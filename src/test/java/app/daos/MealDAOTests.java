package app.daos;

import app.entities.Meal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Only loads JPA, H2, and Spring Data JPA beans
public class MealDAOTests {

    @Autowired
    private MealDAO mealDAO;

    @Test
    @DisplayName("Should save and retrieve meals from DB")
    void testSaveAndFindAllMeals() {
        // Arrange
        Meal meal = new Meal();
        meal.setMealId("12345");
        meal.setName("DAO Test Meal");
        meal.setCategory("Test Category");
        meal.setArea("Test Area");
        meal.setInstructions("Test instructions");
        meal.setImageUrl("https://example.com/image.jpg");

        // Act
        mealDAO.save(meal);
        List<Meal> result = mealDAO.findAll();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("DAO Test Meal");
    }
}
