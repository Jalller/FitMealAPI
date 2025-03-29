package app.daos;

import app.entities.Meal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MealDAOTests {

    @Autowired
    private MealDAO mealDAO;

    @Test
    void testSaveAndFindMeal() {
        Meal meal = new Meal("123", "Test Meal", "Category", "Area", "Instructions", "image-url");
        mealDAO.save(meal);

        List<Meal> meals = mealDAO.findAll();

        assertThat(meals).isNotEmpty();
        assertThat(meals.get(0).getName()).isEqualTo("Test Meal");
    }
}
