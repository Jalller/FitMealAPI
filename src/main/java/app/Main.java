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


            System.out.println("\n===== Updating an Existing Meal =====");
            Optional<Meal> mealToUpdate = mealService.getAllMeals().stream().findFirst();
            if (mealToUpdate.isPresent()) {
                MealDTO updatedMealDTO = new MealDTO(
                        mealToUpdate.get().getMealId(),
                        "Updated Meal Name",
                        "Updated Category",
                        "Updated Area",
                        "Updated Instructions...",
                        "https://example.com/updated-image.jpg",
                        null
                );
                Meal updatedMeal = mealService.updateMeal(mealToUpdate.get().getId(), updatedMealDTO);
                System.out.println("Meal Updated: " + updatedMeal.getName());
            } else {
                System.out.println("No Meals Found to Update");
            }


            //Oldest entry first available
            System.out.println("\n===== Updating an Existing Workout =====");
            Optional<Workout> workoutToUpdate = workoutService.getAllWorkouts().stream().findFirst();
            if (workoutToUpdate.isPresent()) {
                WorkoutDTO updatedWorkoutDTO = new WorkoutDTO(
                        workoutToUpdate.get().getWorkoutId(),
                        "Updated Workout Name",
                        "Updated Category",
                        "Updated workout description..."
                );
                Workout updatedWorkout = workoutService.updateWorkout(workoutToUpdate.get().getId(), updatedWorkoutDTO);
                System.out.println("Workout Updated: " + updatedWorkout.getName());
            } else {
                System.out.println("No Workouts Found to Update");
            }
        };
//        workoutService.updateWorkout(10L, new WorkoutDTO("Updated Name", "Updated Category", "Updated Description")); //Update specific

    }

}
