package app;

import app.dtos.MealDTO;
import app.dtos.WorkoutDTO;
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
            System.out.println("===== Fetching Random Meal from TheMealDB =====");
            MealDTO meal = mealService.fetchRandomMeal();
            if (meal != null) {
                System.out.println("Meal Fetch Successful!");
            } else {
                System.out.println("Meal Fetch Failed.");
            }

            System.out.println("\n===== Fetching Random Workout from WGER =====");
            WorkoutDTO workout = workoutService.fetchRandomWorkout();
            if (workout != null) {
                System.out.println("Workout Fetch Successful!");
            } else {
                System.out.println("Workout Fetch Failed.");
            }
        };
    }
}
