package app.services;

import app.daos.MealDAO;
import app.dtos.MealDTO;
import app.entities.Meal;
import app.exceptions.ResourceNotFoundException;
import app.exceptions.UnauthorizedException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private static final String RANDOM_MEAL_API = "https://www.themealdb.com/api/json/v1/1/random.php";
    private static final String CATEGORY_MEAL_API = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";

    private final MealDAO mealDAO;

    public MealService(MealDAO mealDAO) {
        this.mealDAO = mealDAO;
    }

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

                return mealDAO.save(meal);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch/save random meal: " + e.getMessage());
        }
        throw new ResourceNotFoundException("No meal returned from API.");
    }

    public void fetchAndSaveMultipleMeals() {
        String[] categories = {"Chicken", "Beef", "Vegetarian", "Seafood"};

        for (String category : categories) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(CATEGORY_MEAL_API + category))
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
                        mealDAO.save(meal);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error fetching category: " + category + " – " + e.getMessage());
            }
        }
    }

    public List<Meal> getAllMeals() {
        return mealDAO.findAll();
    }

    public Meal updateMeal(Long id, MealDTO mealDTO) {
        return mealDAO.findById(id)
                .map(meal -> {
                    meal.setName(mealDTO.getStrMeal());
                    meal.setCategory(mealDTO.getStrCategory());
                    meal.setArea(mealDTO.getStrArea());
                    meal.setInstructions(mealDTO.getStrInstructions());
                    meal.setImageUrl(mealDTO.getStrMealThumb());
                    return mealDAO.save(meal);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));
    }

    public void deleteMeal(Long id) {
        if (mealDAO.existsById(id)) {
            mealDAO.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Meal not found with ID: " + id);
        }
    }

    public void updateFirstAvailableMeal() {
        mealDAO.findAll().stream().findFirst()
                .map(meal -> {
                    meal.setName("updated meal name");
                    meal.setCategory("updated category");
                    meal.setArea("updated area");
                    meal.setInstructions("updated instructions...");
                    meal.setImageUrl("https://example.com/updated-image.jpg");
                    return mealDAO.save(meal);
                })
                .orElseThrow(() -> new ResourceNotFoundException("No meals available to update."));
    }

    public boolean updateMealById(Long id, MealDTO mealDTO) {
        return mealDAO.findById(id).map(meal -> {
            meal.setName(mealDTO.getStrMeal());
            meal.setCategory(mealDTO.getStrCategory());
            meal.setArea(mealDTO.getStrArea());
            meal.setInstructions(mealDTO.getStrInstructions());
            meal.setImageUrl(mealDTO.getStrMealThumb());
            mealDAO.save(meal);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));
    }

    public void deleteFirstAvailableMeal() {
        mealDAO.findAll().stream().findFirst()
                .map(meal -> {
                    mealDAO.deleteById(meal.getId());
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException("No meals available to delete."));
    }

    public boolean deleteMealById(Long id) {
        return mealDAO.findById(id).map(meal -> {
            mealDAO.deleteById(id);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Meal not found with ID: " + id));
    }

    public Optional<Meal> getLastMeal() {
        return mealDAO.findAll().stream()
                .sorted((m1, m2) -> m2.getId().compareTo(m1.getId()))
                .findFirst();
    }

    public void updateLastAvailableMeal() {
        getLastMeal().map(meal -> {
            meal.setName("updated last meal name");
            meal.setCategory("updated last category");
            meal.setArea("updated last area");
            meal.setInstructions("updated last instructions...");
            meal.setImageUrl("https://example.com/updated-last-image.jpg");
            return mealDAO.save(meal);
        }).orElseThrow(() -> new ResourceNotFoundException("No last meal available to update."));
    }

    public void deleteLastAvailableMeal() {
        getLastMeal().map(meal -> {
            mealDAO.deleteById(meal.getId());
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("No meals found to delete!"));
    }

    // 🔐 BONUS: If needed later — add role/permission check like this:
    public void restrictedAdminAction(boolean isAdmin) {
        if (!isAdmin) {
            throw new UnauthorizedException("Only admins are allowed to perform this action.");
        }
        // Continue with logic...
    }
}
