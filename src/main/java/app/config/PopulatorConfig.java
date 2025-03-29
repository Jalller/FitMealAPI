package app.config;

import app.dtos.MealDTO;
import app.dtos.WorkoutDTO;
import app.entities.Meal;
import app.entities.Workout;
import app.services.MealService;
import app.services.WorkoutService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PopulatorConfig {

    @Bean
    public CommandLineRunner run(MealService mealService, WorkoutService workoutService) {
        return args -> {
            if (false) { // change to true to enable logic below
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
            }
        };
    }
}

//Fetch data from external APIs and store it locally
//Perform basic setup needed before the app is used
//false = clean startup
//true = populate with test/demo data on boot