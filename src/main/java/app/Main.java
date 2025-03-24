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
            System.out.println("===== fetching & saving multiple meals =====");
            mealService.fetchAndSaveMultipleMeals();
            System.out.println("saved multiple meals");

            System.out.println("\n===== fetching & saving random meal =====");
            Meal meal = mealService.fetchAndSaveRandomMeal();
            if (meal != null) {
                System.out.println("saved random meal: " + meal.getName());
            } else {
                System.out.println("meal save failed");
            }

            System.out.println("\n===== fetching & saving workout =====");
            Workout workout = workoutService.fetchAndSaveRandomWorkout();
            if (workout != null) {
                System.out.println("saved workout: " + workout.getName());
            } else {
                System.out.println("workout save failed");
            }

            System.out.println("\n===== fetching & saving multiple workouts =====");
            workoutService.fetchAndSaveMultipleWorkouts();
            System.out.println("saved multiple workouts");

            //System.out.println("all meal ids: " + mealService.getAllMeals().stream().map(Meal::getId).toList());
            //System.out.println("all workout ids: " + workoutService.getAllWorkouts().stream().map(Workout::getId).toList());

            // update first available meal
            System.out.println("\n===== updating first available meal =====");
            mealService.updateFirstAvailableMeal();

            // update meal by specific id
            System.out.println("\n===== updating meal by id =====");
            mealService.updateMealById(10L, new MealDTO(
                    "10",
                    "updated meal name by id",
                    "updated category",
                    "updated area",
                    "updated instructions...",
                    "https://example.com/updated-image.jpg",
                    null
            ));

            // update last available meal
            System.out.println("\n===== updating last available meal =====");
            mealService.updateLastAvailableMeal();

            // update first available workout
            System.out.println("\n===== updating first available workout =====");
            workoutService.updateFirstAvailableWorkout();

            // update workout by specific id (e.g., id = 15)
            System.out.println("\n===== updating workout by id =====");
            workoutService.updateWorkoutById(15L, new WorkoutDTO(
                    "15",
                    "updated workout name by id",
                    "updated category",
                    "updated workout description..."
            ));

            // update last available workout
            System.out.println("\n===== updating last available workout =====");
            workoutService.updateLastAvailableWorkout();

            // delete first available meal
            System.out.println("\n===== deleting first available meal =====");
            mealService.deleteFirstAvailableMeal();

            // delete meal by specific id (e.g., id = 20)
            System.out.println("\n===== deleting meal by id =====");
            mealService.deleteMealById(20L);

            // delete last available meal
            System.out.println("\n===== deleting last available meal =====");
            mealService.deleteLastAvailableMeal();

            // delete first available workout
            System.out.println("\n===== deleting first available workout =====");
            workoutService.deleteFirstAvailableWorkout();

            // delete workout by specific id
            System.out.println("\n===== deleting workout by id =====");
            workoutService.deleteWorkoutById(25L);

            // delete last available workout
            System.out.println("\n===== deleting last available workout =====");
            workoutService.deleteLastAvailableWorkout();
        };
    }
//    optional<Meal> mealToUpdate = mealService.getAllMeals().stream()
//            .sorted((m1, m2) -> m2.getId().compareTo(m1.getId())) // sort by id descending (latest first)
//            .findFirst();
//    optional<Workout> workoutToUpdate = workoutService.getAllWorkouts().stream()
//            .sorted((w1, w2) -> w2.getId().compareTo(w1.getId())) // sort by id descending (latest first)
//            .findFirst();

    // updates +1 - deletes the next in row fix
}
