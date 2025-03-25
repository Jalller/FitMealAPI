package controllers;

import app.controllers.MealController;
import app.dtos.MealDTO;
import app.entities.Meal;
import app.services.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    private Meal meal;
    private MealDTO mealDTO;

    @BeforeEach
    void setUp() {
        meal = new Meal(1L, "Test Meal", "Category", "Area", "Instructions", "https://example.com/image.jpg", null);
        mealDTO = new MealDTO("1", "Updated Meal", "Updated Category", "Updated Area", "Updated Instructions", "https://example.com/updated-image.jpg", null);
    }

    @Test
    void testCreateMeal() {
        when(mealService.createMeal(any(MealDTO.class))).thenReturn(meal);

        Meal createdMeal = mealController.createMeal(mealDTO);

        assertNotNull(createdMeal);
        assertEquals("Test Meal", createdMeal.getName());
        verify(mealService, times(1)).createMeal(any(MealDTO.class));
    }

    @Test
    void testGetMealById() {
        when(mealService.getMealById(1L)).thenReturn(Optional.of(meal));

        Optional<Meal> foundMeal = mealController.getMealById(1L);

        assertTrue(foundMeal.isPresent());
        assertEquals("Test Meal", foundMeal.get().getName());
        verify(mealService, times(1)).getMealById(1L);
    }

    @Test
    void testUpdateMeal() {
        when(mealService.updateMealById(1L, mealDTO)).thenReturn(true);

        boolean updated = mealController.updateMeal(1L, mealDTO).hasBody();

        assertTrue(updated);
        verify(mealService, times(1)).updateMealById(1L, mealDTO);
    }

    @Test
    void testDeleteMeal() {
        when(mealService.deleteMealById(1L)).thenReturn(true);

        boolean deleted = mealController.deleteMeal(1L).hasBody();

        assertTrue(deleted);
        verify(mealService, times(1)).deleteMealById(1L);
    }
}
