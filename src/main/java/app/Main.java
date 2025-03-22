package app;

import app.dtos.MealDTO;
import app.services.MealService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "app.entities")
public class Main {

    private final MealService mealService;

    public Main(MealService mealService) {
        this.mealService = mealService;
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
                System.out.println("✅ Meal Fetch Successful!");
            } else {
                System.out.println("❌ Meal Fetch Failed.");
            }
        };
    }
}
