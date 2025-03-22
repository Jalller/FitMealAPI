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
import java.util.Random;

@Service
public class WorkoutService {
    private static final String API_URL = "https://wger.de/api/v2/exerciseinfo/?language=2";
    private final WorkoutDAO workoutDAO;
    private final ObjectMapper objectMapper;

    public WorkoutService(WorkoutDAO workoutDAO) {
        this.workoutDAO = workoutDAO;
        this.objectMapper = new ObjectMapper();
    }

    public Workout fetchAndSaveRandomWorkout() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode rootNode = objectMapper.readTree(response.body());

            if (!rootNode.has("results") || rootNode.get("results").isEmpty()) {
                System.out.println("No workout data found in API response.");
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

            workout = workoutDAO.save(workout); // Save workout to DB

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
}
