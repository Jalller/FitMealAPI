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
import java.util.Optional;

@Service
public class MealService {
    private static final String RANDOM_MEAL_API = "https://www.themealdb.com/api/json/v1/1/random.php";
    private static final String CATEGORY_MEAL_API = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";

    private final MealDAO mealDAO;

    public MealService(MealDAO mealDAO) {
        this.mealDAO = mealDAO;
    }

    // fetch & save one random meal
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
            e.printStackTrace();
        }
        return null;
    }

    // fetch & save multiple meals at once
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
                        mealDAO.save(meal);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("fetched and saved multiple meals from categories");
    }

    // get all meals
    public List<Meal> getAllMeals() {
        return mealDAO.findAll();
    }

    // update meal by id
    public Meal updateMeal(Long id, MealDTO mealDTO) {
        return mealDAO.findById(id).map(meal -> {
            meal.setName(mealDTO.getStrMeal());
            meal.setCategory(mealDTO.getStrCategory());
            meal.setArea(mealDTO.getStrArea());
            meal.setInstructions(mealDTO.getStrInstructions());
            meal.setImageUrl(mealDTO.getStrMealThumb());
            return mealDAO.save(meal);
        }).orElseThrow(() -> new RuntimeException("meal not found with id: " + id));
    }

    // delete meal by id
    public void deleteMeal(Long id) {
        if (mealDAO.existsById(id)) {
            mealDAO.deleteById(id);
            System.out.println("meal deleted: id " + id);
        } else {
            throw new RuntimeException("meal not found with id: " + id);
        }
    }

    // update the first available meal
    public void updateFirstAvailableMeal() {
        mealDAO.findAll().stream().findFirst().ifPresent(meal -> {
            meal.setName("updated meal name");
            meal.setCategory("updated category");
            meal.setArea("updated area");
            meal.setInstructions("updated instructions...");
            meal.setImageUrl("https://example.com/updated-image.jpg");
            mealDAO.save(meal);
            System.out.println("meal updated: " + meal.getName());
        });
    }

    // update meal by id
    public void updateMealById(Long id, MealDTO mealDTO) {
        mealDAO.findById(id).ifPresent(meal -> {
            meal.setName(mealDTO.getStrMeal());
            meal.setCategory(mealDTO.getStrCategory());
            meal.setArea(mealDTO.getStrArea());
            meal.setInstructions(mealDTO.getStrInstructions());
            meal.setImageUrl(mealDTO.getStrMealThumb());
            mealDAO.save(meal);
            System.out.println("meal updated by id: " + meal.getName());
        });
    }

    // delete the first available meal
    public void deleteFirstAvailableMeal() {
        mealDAO.findAll().stream().findFirst().ifPresent(meal -> {
            mealDAO.deleteById(meal.getId());
            System.out.println("meal deleted: " + meal.getName());
        });
    }

    // delete meal by id
    public void deleteMealById(Long id) {
        mealDAO.findById(id).ifPresent(meal -> {
            mealDAO.deleteById(id);
            System.out.println("meal deleted by id: " + meal.getName());
        });
    }

    // get the last available meal
    public Optional<Meal> getLastMeal() {
        return mealDAO.findAll().stream()
                .sorted((m1, m2) -> m2.getId().compareTo(m1.getId()))
                .findFirst();
    }

    // update the last available meal
    public void updateLastAvailableMeal() {
        getLastMeal().ifPresent(meal -> {
            meal.setName("updated last meal name");
            meal.setCategory("updated last category");
            meal.setArea("updated last area");
            meal.setInstructions("updated last instructions...");
            meal.setImageUrl("https://example.com/updated-last-image.jpg");
            mealDAO.save(meal);
            System.out.println("meal updated: " + meal.getName());
        });
    }

    // delete the last available meal
    public void deleteLastAvailableMeal() {
        getLastMeal().ifPresentOrElse(
                meal -> {
                    mealDAO.deleteById(meal.getId());
                    System.out.println("meal deleted: " + meal.getName());
                },
                () -> { throw new RuntimeException("no meals found to delete!"); }
        );
    }
}
