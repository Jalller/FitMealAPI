package app.controllers;

import app.dtos.WorkoutDTO;
import app.entities.Workout;
import app.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    // fetch & save a random workout
    @PostMapping("/fetch-random")
    public ResponseEntity<String> fetchAndSaveRandomWorkout() {
        workoutService.fetchAndSaveRandomWorkout();
        return ResponseEntity.ok("Fetched and saved a random workout.");
    }

    // fetch & save multiple workouts
    @PostMapping("/fetch-multiple")
    public ResponseEntity<String> fetchAndSaveMultipleWorkouts() {
        workoutService.fetchAndSaveMultipleWorkouts();
        return ResponseEntity.ok("Fetched and saved multiple workouts.");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody WorkoutDTO workoutDTO) {
        Workout updatedWorkout = workoutService.updateWorkout(id, workoutDTO);
        return ResponseEntity.ok(updatedWorkout);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.ok("Workout deleted successfully!");
    }

    // update the first available workout
    @PutMapping("/update-first")
    public ResponseEntity<String> updateFirstAvailableWorkout() {
        workoutService.updateFirstAvailableWorkout();
        return ResponseEntity.ok("First available workout updated successfully!");
    }

    // update workout by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWorkoutById(@PathVariable Long id, @RequestBody WorkoutDTO workoutDTO) {
        workoutService.updateWorkoutById(id, workoutDTO);
        return ResponseEntity.ok("Workout updated successfully by ID!");
    }

    // delete the first available workout
    @DeleteMapping("/delete-first")
    public ResponseEntity<String> deleteFirstAvailableWorkout() {
        workoutService.deleteFirstAvailableWorkout();
        return ResponseEntity.ok("First available workout deleted successfully!");
    }

    // delete workout by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkoutById(@PathVariable Long id) {
        workoutService.deleteWorkoutById(id);
        return ResponseEntity.ok("Workout deleted successfully by ID!");
    }




}
