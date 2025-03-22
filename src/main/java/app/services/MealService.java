package app.services;

import app.dtos.MealDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {
    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/random.php";

    public MealDTO fetchRandomMeal() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            if (rootNode.has("meals") && rootNode.get("meals").isArray()) {
                JsonNode mealNode = rootNode.get("meals").get(0);

                // Extract ingredients
                List<String> ingredients = new ArrayList<>();
                for (int i = 1; i <= 20; i++) { // TheMealDB has up to 20 ingredients
                    String ingredient = mealNode.get("strIngredient" + i).asText();
                    if (ingredient != null && !ingredient.isEmpty() && !ingredient.equals("null")) {
                        ingredients.add(ingredient);
                    }
                }

                // Map JSON to DTO
                MealDTO meal = mapper.treeToValue(mealNode, MealDTO.class);
                meal.setIngredients(ingredients); // Add extracted ingredients

                // ✅ Use correct getter methods (`getStrMeal()`, `getStrCategory()`, etc.)
                System.out.println("\n===== Random Meal Fetched =====");
                System.out.println("🍽️ Meal: " + meal.getStrMeal());
                System.out.println("📌 Category: " + meal.getStrCategory());
                System.out.println("🌍 Area: " + meal.getStrArea());
                System.out.println("📝 Instructions: " + meal.getStrInstructions());
                System.out.println("🖼️ Image: " + meal.getStrMealThumb());
                System.out.println("🥗 Ingredients: " + ingredients);

                return meal;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
