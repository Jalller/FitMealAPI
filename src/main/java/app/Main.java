package app;

import app.entities.Meal;
import app.entities.Workout;
import app.services.MealService;
import app.services.WorkoutService;
import app.dtos.MealDTO;
import app.dtos.WorkoutDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Optional;

@SpringBootApplication
@EntityScan(basePackages = "app.entities")
public class Main {

    private final MealService mealService;
    private final WorkoutService workoutService;

    public Main(MealService mealService, WorkoutService workoutService) {
        this.mealService = mealService;
        this.workoutService = workoutService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("===== Fetching & Saving Multiple Meals =====");
            mealService.fetchAndSaveMultipleMeals();
            System.out.println("Saved multiple meals");

            System.out.println("\n===== Fetching & Saving Random Meal =====");
            Meal meal = mealService.fetchAndSaveRandomMeal();
            if (meal != null) {
                System.out.println("Saved random meal: " + meal.getName());
            } else {
                System.out.println("Meal Save Failed");
            }

            System.out.println("\n===== Fetching & Saving Workout =====");
            Workout workout = workoutService.fetchAndSaveRandomWorkout();
            if (workout != null) {
                System.out.println("Saved workout: " + workout.getName());
            } else {
                System.out.println("Workout Save Failed");
            }

            System.out.println("\n===== Fetching & Saving Multiple Workouts =====");
            workoutService.fetchAndSaveMultipleWorkouts();
            System.out.println("Saved multiple workouts");

            System.out.println("All Meal IDs: " + mealService.getAllMeals().stream().map(Meal::getId).toList());
            System.out.println("All Workout IDs: " + workoutService.getAllWorkouts().stream().map(Workout::getId).toList());

            // ✅ Update first available meal
            System.out.println("\n===== Updating First Available Meal =====");
            mealService.updateFirstAvailableMeal();

            // ✅ Update meal by specific ID (e.g., ID = 10)
            System.out.println("\n===== Updating Meal by ID =====");
            mealService.updateMealById(10L, new MealDTO(
                    "10",
                    "Updated Meal Name by ID",
                    "Updated Category",
                    "Updated Area",
                    "Updated Instructions...",
                    "https://example.com/updated-image.jpg",
                    null
            ));

            // ✅ Update first available workout
            System.out.println("\n===== Updating First Available Workout =====");
            workoutService.updateFirstAvailableWorkout();

            // ✅ Update workout by specific ID (e.g., ID = 15)
            System.out.println("\n===== Updating Workout by ID =====");
            workoutService.updateWorkoutById(15L, new WorkoutDTO(
                    "15",
                    "Updated Workout Name by ID",
                    "Updated Category",
                    "Updated workout description..."
            ));

            // ✅ Delete first available meal
            System.out.println("\n===== Deleting First Available Meal =====");
            mealService.deleteFirstAvailableMeal();

            // ✅ Delete meal by specific ID (e.g., ID = 20)
            System.out.println("\n===== Deleting Meal by ID =====");
            mealService.deleteMealById(20L);

            // ✅ Delete first available workout
            System.out.println("\n===== Deleting First Available Workout =====");
            workoutService.deleteFirstAvailableWorkout();

            // ✅ Delete workout by specific ID (e.g., ID = 25)
            System.out.println("\n===== Deleting Workout by ID =====");
            workoutService.deleteWorkoutById(25L);
        };
    }
//    Optional<Meal> mealToUpdate = mealService.getAllMeals().stream()
//            .sorted((m1, m2) -> m2.getId().compareTo(m1.getId())) // Sort by ID descending (latest first)
//            .findFirst();
//    Optional<Workout> workoutToUpdate = workoutService.getAllWorkouts().stream()
//            .sorted((w1, w2) -> w2.getId().compareTo(w1.getId())) // Sort by ID descending (latest first)
//            .findFirst();

    //Updates +1 - deletes the next in row FIX
}
