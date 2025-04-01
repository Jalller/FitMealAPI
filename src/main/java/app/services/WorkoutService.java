package app.services;

import app.daos.WorkoutDAO;
import app.dtos.WorkoutDTO;
import app.entities.Workout;
import app.exceptions.ResourceNotFoundException;
import app.exceptions.UnauthorizedException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class WorkoutService {
    private static final String API_URL = "https://wger.de/api/v2/exerciseinfo/?language=2";
    private final WorkoutDAO workoutDAO;

    public WorkoutService(WorkoutDAO workoutDAO) {
        this.workoutDAO = workoutDAO;
    }

    public List<Workout> getAllWorkouts() {
        return workoutDAO.findAll();
    }

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
                throw new ResourceNotFoundException("No workout data found in API.");
            }

            Random random = new Random();
            int randomIndex = random.nextInt(rootNode.get("results").size());
            JsonNode workoutNode = rootNode.get("results").get(randomIndex);

            String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                    ? workoutNode.get("category").get("name").asText()
                    : "Unknown Category";

            String name = "Unnamed Workout";
            String description = "No description available.";

            if (workoutNode.has("translations") && workoutNode.get("translations").isArray()) {
                for (JsonNode translation : workoutNode.get("translations")) {
                    if (translation.has("language") && translation.get("language").asInt() == 2) {
                        name = translation.path("name").asText(name);
                        description = translation.path("description").asText(description);
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

            return workoutDAO.save(workout);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch/save random workout: " + e.getMessage());
        }
    }

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
                throw new ResourceNotFoundException("No workout data found for multiple fetch.");
            }

            List<Workout> workouts = new ArrayList<>();

            for (JsonNode workoutNode : rootNode.get("results")) {
                String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                        ? workoutNode.get("category").get("name").asText()
                        : "Unknown Category";

                String name = "Unnamed Workout";
                String description = "No description available.";

                if (workoutNode.has("translations") && workoutNode.get("translations").isArray()) {
                    for (JsonNode translation : workoutNode.get("translations")) {
                        if (translation.has("language") && translation.get("language").asInt() == 2) {
                            name = translation.path("name").asText(name);
                            description = translation.path("description").asText(description);
                            break;
                        }
                    }
                }

                workouts.add(new Workout(workoutNode.get("id").asText(), name, category, description));
            }

            workoutDAO.saveAll(workouts);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch/save multiple workouts: " + e.getMessage());
        }
    }

    public Workout updateWorkout(Long id, WorkoutDTO dto) {
        return workoutDAO.findById(id).map(w -> {
            w.setName(dto.getName());
            w.setCategory(dto.getCategory());
            w.setDescription(dto.getDescription());
            return workoutDAO.save(w);
        }).orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + id));
    }

    public boolean updateWorkoutById(Long id, WorkoutDTO dto) {
        return workoutDAO.findById(id).map(w -> {
            w.setName(dto.getName());
            w.setCategory(dto.getCategory());
            w.setDescription(dto.getDescription());
            workoutDAO.save(w);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + id));
    }

    public void deleteWorkout(Long id) {
        if (workoutDAO.existsById(id)) {
            workoutDAO.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Workout not found with ID: " + id);
        }
    }

    public boolean deleteWorkoutById(Long id) {
        return workoutDAO.findById(id).map(w -> {
            workoutDAO.deleteById(id);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + id));
    }

    public void updateFirstAvailableWorkout() {
        workoutDAO.findAll().stream().findFirst().map(w -> {
            w.setName("updated workout name");
            w.setCategory("updated category");
            w.setDescription("updated workout description...");
            return workoutDAO.save(w);
        }).orElseThrow(() -> new ResourceNotFoundException("No workouts available to update."));
    }

    public void deleteFirstAvailableWorkout() {
        workoutDAO.findAll().stream().findFirst().map(w -> {
            workoutDAO.deleteById(w.getId());
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("No workouts available to delete."));
    }

    public Optional<Workout> getLastWorkout() {
        return workoutDAO.findAll().stream()
                .sorted((w1, w2) -> w2.getId().compareTo(w1.getId()))
                .findFirst();
    }

    public void updateLastAvailableWorkout() {
        getLastWorkout().map(w -> {
            w.setName("updated last workout name");
            w.setCategory("updated last category");
            w.setDescription("updated last workout description...");
            return workoutDAO.save(w);
        }).orElseThrow(() -> new ResourceNotFoundException("No last workout available to update."));
    }

    public void deleteLastAvailableWorkout() {
        getLastWorkout().map(w -> {
            workoutDAO.deleteById(w.getId());
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("No workouts found to delete."));
    }

    public void restrictedAdminAction(boolean isAdmin) {
        if (!isAdmin) throw new UnauthorizedException("Admin privileges required.");
    }
}
