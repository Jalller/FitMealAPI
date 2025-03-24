package app.services;

import app.dtos.WorkoutDTO;
import app.entities.Workout;
import app.daos.WorkoutDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WorkoutService {
    private static final String API_URL = "https://wger.de/api/v2/exerciseinfo/?language=2";
    private final WorkoutDAO workoutDAO;

    public WorkoutService(WorkoutDAO workoutDAO) {
        this.workoutDAO = workoutDAO;
    }

    // Fetch all workouts
    public List<Workout> getAllWorkouts() {
        return workoutDAO.findAll();
    }

    // Fetch and save a random workout
    public Workout fetchAndSaveRandomWorkout() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            if (!rootNode.has("results") || rootNode.get("results").isEmpty()) {
                System.out.println("No workout data found.");
                return null;
            }

            Random random = new Random();
            int randomIndex = random.nextInt(rootNode.get("results").size());
            JsonNode workoutNode = rootNode.get("results").get(randomIndex);

            String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                    ? workoutNode.get("category").get("name").asText()
                    : "Unknown Category";

            String name = "Unknown Workout";
            String description = "No description available.";

            if (workoutNode.has("translations") && workoutNode.get("translations").isArray()) {
                for (JsonNode translation : workoutNode.get("translations")) {
                    if (translation.has("language") && translation.get("language").asInt() == 2) { // English
                        name = translation.has("name") ? translation.get("name").asText() : name;
                        description = translation.has("description") ? translation.get("description").asText() : description;
                        break;
                    }
                }
            }

            Workout workout = new Workout(
                    workoutNode.get("id").asText(),
                    name,
                    category,
                    description
            );
            workout = workoutDAO.save(workout);

            System.out.println("\n===== Workout Saved to Database =====");
            System.out.println("Name: " + workout.getName());
            System.out.println("Category: " + workout.getCategory());
            System.out.println("Description: " + workout.getDescription());

            return workout;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch & save multiple workouts
    public void fetchAndSaveMultipleWorkouts() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            if (!rootNode.has("results") || rootNode.get("results").isEmpty()) {
                System.out.println("No workout data found.");
                return;
            }

            List<Workout> workouts = rootNode.get("results").findValuesAsText("id").stream().map(id -> {
                JsonNode workoutNode = rootNode.get("results").get(0);
                String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                        ? workoutNode.get("category").get("name").asText()
                        : "Unknown Category";

                String name = "Unknown Workout";
                String description = "No description available.";

                if (workoutNode.has("translations") && workoutNode.get("translations").isArray()) {
                    for (JsonNode translation : workoutNode.get("translations")) {
                        if (translation.has("language") && translation.get("language").asInt() == 2) { // English
                            name = translation.has("name") ? translation.get("name").asText() : name;
                            description = translation.has("description") ? translation.get("description").asText() : description;
                            break;
                        }
                    }
                }

                return new Workout(id, name, category, description);
            }).toList();

            workoutDAO.saveAll(workouts);
            System.out.println("\n===== Multiple Workouts Saved to Database =====");
            System.out.println(workouts.size() + " workouts saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update workout find by ID update fields save updated to db
    public Workout updateWorkout(Long id, WorkoutDTO workoutDTO) {
        return workoutDAO.findById(id).map(workout -> {
            workout.setName(workoutDTO.getName());
            workout.setCategory(workoutDTO.getCategory());
            workout.setDescription(workoutDTO.getDescription());
            return workoutDAO.save(workout);
        }).orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
    }
    //delete workout by ID
    public void deleteWorkout(Long id) {
        if (workoutDAO.existsById(id)) {
            workoutDAO.deleteById(id);
            System.out.println("Workout deleted: ID " + id);
        } else {
            throw new RuntimeException("Workout not found with ID: " + id);
        }
    }
    // update the first available workout
    public void updateFirstAvailableWorkout() {
        workoutDAO.findAll().stream().findFirst().ifPresent(workout -> {
            workout.setName("Updated Workout Name");
            workout.setCategory("Updated Category");
            workout.setDescription("Updated workout description...");
            workoutDAO.save(workout);
            System.out.println("Workout updated: " + workout.getName());
        });
    }

    // update workout by ID
    public void updateWorkoutById(Long id, WorkoutDTO workoutDTO) {
        workoutDAO.findById(id).ifPresent(workout -> {
            workout.setName(workoutDTO.getName());
            workout.setCategory(workoutDTO.getCategory());
            workout.setDescription(workoutDTO.getDescription());
            workoutDAO.save(workout);
            System.out.println("Workout updated by ID: " + workout.getName());
        });
    }

    // delete the first available workout
    public void deleteFirstAvailableWorkout() {
        workoutDAO.findAll().stream().findFirst().ifPresent(workout -> {
            workoutDAO.deleteById(workout.getId());
            System.out.println("Workout deleted: " + workout.getName());
        });
    }

    // delete workout by ID
    public void deleteWorkoutById(Long id) {
        workoutDAO.findById(id).ifPresent(workout -> {
            workoutDAO.deleteById(id);
            System.out.println("Workout deleted by ID: " + workout.getName());
        });
    }



}
