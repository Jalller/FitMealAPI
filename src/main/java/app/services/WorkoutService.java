package app.services;

import app.dtos.WorkoutDTO;
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
    private static final String API_URL = "https://wger.de/api/v2/exerciseinfo/?language=2"; // ✅ Correct endpoint

    public WorkoutDTO fetchRandomWorkout() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            // Check if API returned results
            if (!rootNode.has("results") || rootNode.get("results").isEmpty()) {
                System.out.println("No workout data found in API response.");
                return null;
            }

            // Pick a random workout from the results
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

            // Create DTO
            WorkoutDTO workout = new WorkoutDTO(
                    workoutNode.get("id").asText(),
                    name,
                    category,
                    description
            );

            // Print only the necessary workout details
            System.out.println("Name: " + workout.getName());
            System.out.println("Category: " + workout.getCategory());
            System.out.println("Description: " + workout.getDescription());
            System.out.println("Workout Fetch Successful!");

            return workout;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
