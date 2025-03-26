package app.services;

import app.daos.MealDAO;
import app.entities.Meal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MealServiceTests {

    @InjectMocks
    private MealService mealService;

    @Mock
    private MealDAO mealRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAndSaveMultipleMeals() {
        mealService.fetchAndSaveMultipleMeals();
        verify(mealRepository, atLeastOnce()).save(any(Meal.class));
    }

    @Test
    public void testFetchAndSaveRandomMeal() {
        Meal meal = new Meal();
        meal.setName("Random Meal");
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        Meal savedMeal = mealService.fetchAndSaveRandomMeal();
        verify(mealRepository, times(1)).save(any(Meal.class));
        assertNotNull(savedMeal);
        assertEquals("Random Meal", savedMeal.getName());
    }

//    @Test
//    public void testUpdateMealById() {
//        Meal existingMeal = new Meal(1L, "Old Meal", "Category", "Area", "Instructions", "image_url", null);
//        MealDTO mealDTO = new MealDTO("1", "Updated Meal", "Updated Category", "Updated Area", "Updated Instructions", "https://updated.com/image.jpg", null);
//
//        when(mealRepository.findById(1L)).thenReturn(Optional.of(existingMeal));
//        when(mealRepository.save(any(Meal.class))).thenReturn(existingMeal);
//
//        boolean updated = mealService.updateMealById(1L, mealDTO);
//        assertTrue(updated);
//        assertEquals("Updated Meal", existingMeal.getName());
//    }

//    @Test
//    public void testDeleteMealById() {
//        Meal meal = new Meal();
//        meal.setId(1L);
//        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
//
//        boolean deleted = mealService.deleteMealById(1L);
//        verify(mealRepository, times(1)).deleteById(1L);
//        assertTrue(deleted);
//    }
}
