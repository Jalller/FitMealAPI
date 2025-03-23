package app.services;

import app.entities.Workout;
import app.daos.WorkoutDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WorkoutService {
    private static final String API_URL = "https://wger.de/api/v2/exerciseinfo/?language=2";

    private final WorkoutDAO workoutDAO;

    public WorkoutService(WorkoutDAO workoutDAO) {
        this.workoutDAO = workoutDAO;
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

            // Ensure API response contains workouts
            if (!rootNode.has("results") || rootNode.get("results").isEmpty()) {
                System.out.println("No workout data found.");
                return null;
            }

            // Pick a random workout
            Random random = new Random();
            int randomIndex = random.nextInt(rootNode.get("results").size());
            JsonNode workoutNode = rootNode.get("results").get(randomIndex);

            String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                    ? workoutNode.get("category").get("name").asText()
                    : "Unknown Category";

            // Extract name & description from `translations`
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

            // Save to database
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

            // Ensure API response contains workouts
            if (!rootNode.has("results") || rootNode.get("results").isEmpty()) {
                System.out.println("No workout data found.");
                return;
            }

            List<Workout> workouts = new ArrayList<>();
            for (JsonNode workoutNode : rootNode.get("results")) {
                String category = workoutNode.has("category") && workoutNode.get("category").has("name")
                        ? workoutNode.get("category").get("name").asText()
                        : "Unknown Category";

                // Extract name & description from `translations`
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

                // Create and add workout
                workouts.add(new Workout(
                        workoutNode.get("id").asText(),
                        name,
                        category,
                        description
                ));
            }

            // Save all workouts to the database
            workoutDAO.saveAll(workouts);
            System.out.println("\n===== Multiple Workouts Saved to Database =====");
            System.out.println(workouts.size() + "workouts saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
