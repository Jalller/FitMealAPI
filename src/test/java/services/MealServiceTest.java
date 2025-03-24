//package services;
//
//import app.daos.MealDAO;
//import app.dtos.MealDTO;
//import app.entities.Meal;
//import app.services.MealService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MealServiceTest {
//
//    @Mock
//    private MealDAO mealDAO;
//
//    @InjectMocks
//    private MealService mealService;
//
//    private Meal sampleMeal;
//
//    @BeforeEach
//    void setUp() {
//        sampleMeal = new Meal("1", "Test Meal", "Category", "Area", "Instructions", "image.jpg");
//    }
//
//    @Test
//    void shouldGetAllMeals() {
//        when(mealDAO.findAll()).thenReturn(List.of(sampleMeal));
//        List<Meal> meals = mealService.getAllMeals();
//        assertFalse(meals.isEmpty());
//        assertEquals(1, meals.size());
//        verify(mealDAO, times(1)).findAll();
//    }
//
//    @Test
//    void shouldUpdateMeal() {
//        MealDTO updatedMealDTO = new MealDTO("1", "Updated Meal", "Updated Category", "Updated Area", "Updated Instructions", "updated.jpg", null);
//        when(mealDAO.findById(1L)).thenReturn(Optional.of(sampleMeal));
//        when(mealDAO.save(any(Meal.class))).thenReturn(sampleMeal);
//
//        Meal updatedMeal = mealService.updateMeal(1L, updatedMealDTO);
//        assertEquals("Updated Meal", updatedMeal.getName());
//        verify(mealDAO, times(1)).save(any(Meal.class));
//    }
//
//    @Test
//    void shouldDeleteMeal() {
//        when(mealDAO.existsById(1L)).thenReturn(true);
//        doNothing().when(mealDAO).deleteById(1L);
//
//        mealService.deleteMeal(1L);
//        verify(mealDAO, times(1)).deleteById(1L);
//    }
//}
