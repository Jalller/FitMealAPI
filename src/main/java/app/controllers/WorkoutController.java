package app.controllers;

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

    // Fetch and save a random workout
    @PostMapping("/fetch-random")
    public ResponseEntity<Workout> fetchAndSaveRandomWorkout() {
        Workout workout = workoutService.fetchAndSaveRandomWorkout();
        if (workout != null) {
            return ResponseEntity.ok(workout);
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }
//    // Fetch and save multiple workouts
//    @PostMapping("/fetch-multiple")
//    public ResponseEntity<String> fetchAndSaveMultipleWorkouts() {
//        workoutService.fetchAndSaveMultipleWorkouts();
//        return ResponseEntity.ok("fetched and saved multiple workouts");
//    }
}
