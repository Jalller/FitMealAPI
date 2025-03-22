package app.services;

import app.dtos.MealDTO;
import app.entities.Meal;
import app.entities.Ingredient;
import app.daos.MealDAO;
import app.daos.IngredientDAO;
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
    private final MealDAO mealDAO;
    private final IngredientDAO ingredientDAO;

    public MealService(MealDAO mealDAO, IngredientDAO ingredientDAO) {
        this.mealDAO = mealDAO;
        this.ingredientDAO = ingredientDAO;
    }

    public Meal fetchAndSaveRandomMeal() {
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
                MealDTO mealDTO = mapper.treeToValue(mealNode, MealDTO.class);

                Meal meal = new Meal(
                        mealDTO.getIdMeal(),
                        mealDTO.getStrMeal(),
                        mealDTO.getStrCategory(),
                        mealDTO.getStrArea(),
                        mealDTO.getStrInstructions(),
                        mealDTO.getStrMealThumb()
                );

                meal = mealDAO.save(meal); // Save meal to DB

                // Extract and save ingredients
                List<Ingredient> ingredients = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    String ingredientName = mealNode.get("strIngredient" + i).asText();
                    if (ingredientName != null && !ingredientName.isEmpty() && !ingredientName.equals("null")) {
                        Ingredient ingredient = new Ingredient(ingredientName, meal);
                        ingredients.add(ingredient);
                    }
                }
                ingredientDAO.saveAll(ingredients); // Save ingredients to DB

                System.out.println("\n===== Meal Saved to Database =====");
                System.out.println("Meal: " + meal.getName());
                System.out.println("Category: " + meal.getCategory());
                System.out.println("Area: " + meal.getArea());
                System.out.println("Instructions: " + meal.getInstructions());
                System.out.println("Image: " + meal.getImageUrl());
                System.out.println("Ingredients: " + ingredients);

                return meal;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
