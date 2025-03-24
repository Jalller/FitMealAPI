package app.services;

import app.dtos.MealDTO;
import app.entities.Meal;
import app.daos.MealDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class MealService {
    private static final String RANDOM_MEAL_API = "https://www.themealdb.com/api/json/v1/1/random.php";
    private static final String CATEGORY_MEAL_API = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";

    private final MealDAO mealDAO;

    public MealService(MealDAO mealDAO) {
        this.mealDAO = mealDAO;
    }

    // Fetch & save one random meal
    public Meal fetchAndSaveRandomMeal() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(RANDOM_MEAL_API))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            if (rootNode.has("meals") && rootNode.get("meals").isArray()) {
                JsonNode mealNode = rootNode.get("meals").get(0);
                Meal meal = new Meal(
                        mealNode.get("idMeal").asText(),
                        mealNode.get("strMeal").asText(),
                        mealNode.get("strCategory").asText(),
                        mealNode.get("strArea").asText(),
                        mealNode.get("strInstructions").asText(),
                        mealNode.get("strMealThumb").asText()
                );

                return mealDAO.save(meal); // Save meal to DB
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch & save multiple meals at once
    public void fetchAndSaveMultipleMeals() {
        String[] categories = {"Chicken", "Beef", "Vegetarian", "Seafood"};

        for (String category : categories) {
            String apiUrl = CATEGORY_MEAL_API + category;
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiUrl))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.body());

                if (rootNode.has("meals") && rootNode.get("meals").isArray()) {
                    for (JsonNode mealNode : rootNode.get("meals")) {
                        Meal meal = new Meal(
                                mealNode.get("idMeal").asText(),
                                mealNode.get("strMeal").asText(),
                                category, "", "", mealNode.get("strMealThumb").asText()
                        );
                        mealDAO.save(meal); // Save meal to DB
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fetched and saved multiple meals from categories");
    }

    // Get all meals
    public List<Meal> getAllMeals() {
        return mealDAO.findAll();
    }
    public Meal updateMeal(Long id, MealDTO mealDTO) {
        return mealDAO.findById(id).map(meal -> {
            meal.setName(mealDTO.getStrMeal());
            meal.setCategory(mealDTO.getStrCategory());
            meal.setArea(mealDTO.getStrArea());
            meal.setInstructions(mealDTO.getStrInstructions());
            meal.setImageUrl(mealDTO.getStrMealThumb());
            return mealDAO.save(meal);
        }).orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));
    }

}
