package app.controllers;

import app.dtos.MealDTO;
import app.entities.Meal;
import app.services.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // Get all meals from the database
    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    // Fetch and save one random meal
    @PostMapping("/fetch-random")
    public ResponseEntity<Meal> fetchAndSaveRandomMeal() {
        Meal meal = mealService.fetchAndSaveRandomMeal();
        if (meal != null) {
            return ResponseEntity.ok(meal);
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Fetch and save multiple meals from different categories
    @PostMapping("/fetch-multiple")
    public ResponseEntity<String> fetchAndSaveMultipleMeals() {
        mealService.fetchAndSaveMultipleMeals();
        return ResponseEntity.ok("fetched and saved multiple meals");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
        Meal updatedMeal = mealService.updateMeal(id, mealDTO);
        return ResponseEntity.ok(updatedMeal);
    }

}
