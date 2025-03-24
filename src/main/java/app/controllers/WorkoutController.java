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

    // ✅ Fetch & save a random workout
    @PostMapping("/fetch-random")
    public ResponseEntity<String> fetchAndSaveRandomWorkout() {
        workoutService.fetchAndSaveRandomWorkout();
        return ResponseEntity.ok("Fetched and saved a random workout.");
    }

    // ✅ Fetch & save multiple workouts
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

}
