package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "app.entities")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

//package app;
//
//import app.entities.Meal;
//import app.entities.Workout;
//import app.services.MealService;
//import app.services.WorkoutService;
//import app.dtos.MealDTO;
//import app.dtos.WorkoutDTO;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//
//@SpringBootApplication
//@EntityScan(basePackages = "app.entities")
//public class Main {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//
//    @Bean
//    public CommandLineRunner run(MealService mealService, WorkoutService workoutService) {
//        return args -> {
//
//            if (false) { // change to true to re-enable logic below
//                System.out.println("===== fetching & saving multiple meals =====");
//                mealService.fetchAndSaveMultipleMeals();
//                System.out.println("saved multiple meals");
//
//                System.out.println("\n===== fetching & saving random meal =====");
//                Meal meal = mealService.fetchAndSaveRandomMeal();
//                if (meal != null) {
//                    System.out.println("saved random meal: " + meal.getName());
//                } else {
//                    System.out.println("meal save failed");
//                }
//
//                System.out.println("\n===== fetching & saving workout =====");
//                Workout workout = workoutService.fetchAndSaveRandomWorkout();
//                if (workout != null) {
//                    System.out.println("saved workout: " + workout.getName());
//                } else {
//                    System.out.println("workout save failed");
//                }
//
//                System.out.println("\n===== fetching & saving multiple workouts =====");
//                workoutService.fetchAndSaveMultipleWorkouts();
//                System.out.println("saved multiple workouts");
//
//                System.out.println("\n===== updating first available meal =====");
//                mealService.updateFirstAvailableMeal();
//
//                System.out.println("\n===== updating meal by id =====");
//                mealService.updateMealById(10L, new MealDTO(
//                        "10", "updated meal name by id", "updated category", "updated area",
//                        "updated instructions...", "https://example.com/updated-image.jpg", null));
//
//                System.out.println("\n===== updating last available meal =====");
//                mealService.updateLastAvailableMeal();
//
//                System.out.println("\n===== updating first available workout =====");
//                workoutService.updateFirstAvailableWorkout();
//
//                System.out.println("\n===== updating workout by id =====");
//                workoutService.updateWorkoutById(15L, new WorkoutDTO(
//                        "15", "updated workout name by id", "updated category", "updated workout description..."));
//
//                System.out.println("\n===== updating last available workout =====");
//                workoutService.updateLastAvailableWorkout();
//
//                System.out.println("\n===== deleting first available meal =====");
//                mealService.deleteFirstAvailableMeal();
//
//                System.out.println("\n===== deleting meal by id =====");
//                mealService.deleteMealById(20L);
//
//                System.out.println("\n===== deleting last available meal =====");
//                mealService.deleteLastAvailableMeal();
//
//                System.out.println("\n===== deleting first available workout =====");
//                workoutService.deleteFirstAvailableWorkout();
//
//                System.out.println("\n===== deleting workout by id =====");
//                workoutService.deleteWorkoutById(25L);
//
//                System.out.println("\n===== deleting last available workout =====");
//                workoutService.deleteLastAvailableWorkout();
//            }
//
//        };
//    }
//}
