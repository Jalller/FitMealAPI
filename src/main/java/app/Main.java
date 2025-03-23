package app;

import app.entities.Meal;
import app.entities.Workout;
import app.services.MealService;
import app.services.WorkoutService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;

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
                System.out.println("Saved random meal");
            } else {
                System.out.println("Meal Save Failed");
            }

            System.out.println("\n===== Fetching & Saving Workout =====");
            Workout workout = workoutService.fetchAndSaveRandomWorkout();
            if (workout != null) {
                System.out.println("Saved workout");
            } else {
                System.out.println("Workout Save Failed");
            }
            System.out.println("===== Fetching & Saving Multiple Meals =====");
            mealService.fetchAndSaveMultipleMeals();
            System.out.println("Saved multiple meals");

//            System.out.println("\n===== Fetching & Saving Multiple Workouts =====");
//            workoutService.fetchAndSaveMultipleWorkouts();
//            System.out.println("Saved multiple workouts");
        };
    }
}
