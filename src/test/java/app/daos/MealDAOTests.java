package app.daos;

import app.entities.Meal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

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

    @Test
    void testFindById() {
        Meal meal = new Meal("456", "FindMe", "TestCat", "TestArea", "Instructions", "url");
        Meal saved = mealDAO.save(meal);

        Optional<Meal> found = mealDAO.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("FindMe");
    }

    @Test
    void testDeleteById() {
        Meal meal = new Meal("789", "DeleteMe", "Cat", "Area", "Inst", "url");
        Meal saved = mealDAO.save(meal);
        Long id = saved.getId();

        mealDAO.deleteById(id);

        assertThat(mealDAO.findById(id)).isEmpty();
    }

    @Test
    void testSaveMultipleMeals() {
        Meal m1 = new Meal("1", "Meal 1", "Cat", "Area", "Inst", "url");
        Meal m2 = new Meal("2", "Meal 2", "Cat", "Area", "Inst", "url");

        mealDAO.saveAll(List.of(m1, m2));

        List<Meal> meals = mealDAO.findAll();
        assertThat(meals.size()).isGreaterThanOrEqualTo(2);
    }
}
