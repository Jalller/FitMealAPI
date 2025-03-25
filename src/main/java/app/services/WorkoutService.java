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

    // fetch all workouts
    public List<Workout> getAllWorkouts() {
        return workoutDAO.findAll();
    }

    // fetch and save a random workout
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
                System.out.println("no workout data found.");
                return null;
            }

            Random random = new Random();
            int randomIndex = random.nextInt(rootNode.get("results").size());
            JsonNode workoutNode = rootNode.get("results").get(randomIndex);

            String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                    ? workoutNode.get("category").get("name").asText()
                    : "unknown category";

            String name = "unknown workout";
            String description = "no description available.";

            if (workoutNode.has("translations") && workoutNode.get("translations").isArray()) {
                for (JsonNode translation : workoutNode.get("translations")) {
                    if (translation.has("language") && translation.get("language").asInt() == 2) { // english
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

            System.out.println("\n===== workout saved to database =====");
            System.out.println("name: " + workout.getName());
            System.out.println("category: " + workout.getCategory());
            System.out.println("description: " + workout.getDescription());

            return workout;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // fetch & save multiple workouts
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
                System.out.println("no workout data found.");
                return;
            }

            List<Workout> workouts = rootNode.get("results").findValuesAsText("id").stream().map(id -> {
                JsonNode workoutNode = rootNode.get("results").get(0);
                String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                        ? workoutNode.get("category").get("name").asText()
                        : "unknown category";

                String name = "unknown workout";
                String description = "no description available.";

                if (workoutNode.has("translations") && workoutNode.get("translations").isArray()) {
                    for (JsonNode translation : workoutNode.get("translations")) {
                        if (translation.has("language") && translation.get("language").asInt() == 2) { // english
                            name = translation.has("name") ? translation.get("name").asText() : name;
                            description = translation.has("description") ? translation.get("description").asText() : description;
                            break;
                        }
                    }
                }

                return new Workout(id, name, category, description);
            }).toList();

            workoutDAO.saveAll(workouts);
            System.out.println("\n===== multiple workouts saved to database =====");
            System.out.println(workouts.size() + " workouts saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update workout by id
    public Workout updateWorkout(Long id, WorkoutDTO workoutDTO) {
        return workoutDAO.findById(id).map(workout -> {
            workout.setName(workoutDTO.getName());
            workout.setCategory(workoutDTO.getCategory());
            workout.setDescription(workoutDTO.getDescription());
            return workoutDAO.save(workout);
        }).orElseThrow(() -> new RuntimeException("workout not found with id: " + id));
    }

    // delete workout by id
    public void deleteWorkout(Long id) {
        if (workoutDAO.existsById(id)) {
            workoutDAO.deleteById(id);
            System.out.println("workout deleted: id " + id);
        } else {
            throw new RuntimeException("workout not found with id: " + id);
        }
    }

    // update first available workout
    public void updateFirstAvailableWorkout() {
        workoutDAO.findAll().stream().findFirst().ifPresent(workout -> {
            workout.setName("updated workout name");
            workout.setCategory("updated category");
            workout.setDescription("updated workout description...");
            workoutDAO.save(workout);
            System.out.println("workout updated: " + workout.getName());
        });
    }

    // update workout by id
    public boolean updateWorkoutById(Long id, WorkoutDTO workoutDTO) {
        workoutDAO.findById(id).ifPresent(workout -> {
            workout.setName(workoutDTO.getName());
            workout.setCategory(workoutDTO.getCategory());
            workout.setDescription(workoutDTO.getDescription());
            workoutDAO.save(workout);
            System.out.println("workout updated by id: " + workout.getName());
        });
        return false;
    }

    // delete first available workout
    public void deleteFirstAvailableWorkout() {
        workoutDAO.findAll().stream().findFirst().ifPresent(workout -> {
            workoutDAO.deleteById(workout.getId());
            System.out.println("workout deleted: " + workout.getName());
        });
    }

    // delete workout by id
    public boolean deleteWorkoutById(Long id) {
        workoutDAO.findById(id).ifPresent(workout -> {
            workoutDAO.deleteById(id);
            System.out.println("workout deleted by id: " + workout.getName());
        });
        return false;
    }

    // get last workout
    public Optional<Workout> getLastWorkout() {
        return workoutDAO.findAll().stream()
                .sorted((w1, w2) -> w2.getId().compareTo(w1.getId())) // sort by id desc
                .findFirst();
    }

    // update last workout
    public void updateLastAvailableWorkout() {
        getLastWorkout().ifPresent(workout -> {
            workout.setName("updated last workout name");
            workout.setCategory("updated last category");
            workout.setDescription("updated last workout description...");
            workoutDAO.save(workout);
            System.out.println("workout updated: " + workout.getName());
        });
    }

    // delete last workout
    public void deleteLastAvailableWorkout() {
        getLastWorkout().ifPresentOrElse(
                workout -> {
                    workoutDAO.deleteById(workout.getId());
                    System.out.println("workout deleted: " + workout.getName());
                },
                () -> { throw new RuntimeException("no workouts found to delete!"); }
        );
    }
}
