//package app.controllers;
//
//import app.dtos.MealDTO;
//import app.entities.Meal;
//import app.services.MealService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/meals")
//public class MealController {
//
//    private final MealService mealService;
//
//    public MealController(MealService mealService) {
//        this.mealService = mealService;
//    }
//
//    // get all meals from the database
//    @GetMapping
//    public List<Meal> getAllMeals() {
//        return mealService.getAllMeals();
//    }
//
//    // fetch and save one random meal
//    @PostMapping("/fetch-random")
//    public ResponseEntity<Meal> fetchAndSaveRandomMeal() {
//        Meal meal = mealService.fetchAndSaveRandomMeal();
//        if (meal != null) {
//            return ResponseEntity.ok(meal);
//        } else {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//
//    // fetch and save multiple meals from different categories
//    @PostMapping("/fetch-multiple")
//    public ResponseEntity<String> fetchAndSaveMultipleMeals() {
//        mealService.fetchAndSaveMultipleMeals();
//        return ResponseEntity.ok("fetched and saved multiple meals");
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Meal> updateMeal(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
//        Meal updatedMeal = mealService.updateMeal(id, mealDTO);
//        return ResponseEntity.ok(updatedMeal);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteMeal(@PathVariable Long id) {
//        mealService.deleteMeal(id);
//        return ResponseEntity.ok("Meal deleted successfully!");
//    }
//
//    // update the first available meal
//    @PutMapping("/update-first")
//    public ResponseEntity<String> updateFirstAvailableMeal() {
//        mealService.updateFirstAvailableMeal();
//        return ResponseEntity.ok("First available meal updated successfully!");
//    }
//
//    // update meal by ID
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateMealById(@PathVariable Long id, @RequestBody MealDTO mealDTO) {
//        mealService.updateMealById(id, mealDTO);
//        return ResponseEntity.ok("Meal updated successfully by ID!");
//    }
//
//    // delete the first available meal
//    @DeleteMapping("/delete-first")
//    public ResponseEntity<String> deleteFirstAvailableMeal() {
//        mealService.deleteFirstAvailableMeal();
//        return ResponseEntity.ok("First available meal deleted successfully!");
//    }
//
//    // delete meal by ID
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteMealById(@PathVariable Long id) {
//        mealService.deleteMealById(id);
//        return ResponseEntity.ok("Meal deleted successfully by ID!");
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
//        Optional<Meal> meal = mealService.getMealById(id);
//        return meal.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//
//}

package app.controllers;

import app.entities.Meal;
import app.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }
    @GetMapping
    @Transactional  // keep session open during JSON serialization
    public List<Meal> fetchMeals() {
        mealService.fetchAndSaveMultipleMeals();
        return mealService.getAllMeals();
    }

//    // Endpoint to fetch all meals
//    @GetMapping
//    public List<Meal> fetchMeals() {
//        mealService.fetchAndSaveMultipleMeals();
//        return mealService.getAllMeals();
//    }


    // Endpoint to add a random meal
    @PostMapping
    public Meal addMeal() {
        return mealService.fetchAndSaveRandomMeal();
    }

//    // Endpoint to update meal by id
//    @PutMapping("/{id}")
//    public Meal updateMeal(@PathVariable Long id, @RequestBody Meal meal) {
//        return mealService.updateMealById(id, meal);
//    }

//    // Endpoint to delete meal by id
//    @DeleteMapping("/{id}")
//    public void deleteMeal(@PathVariable Long id) {
//        mealService.deleteMealById(id);
//    }
}
