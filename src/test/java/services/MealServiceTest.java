//package services;
//
//import app.dtos.MealDTO;
//import app.entities.Meal;
//import app.daos.MealDAO;
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
//    private MealDAO mealRepository;
//
//    @InjectMocks
//    private MealService mealService;
//
//    private Meal meal;
//    private MealDTO mealDTO;
//
//    @BeforeEach
//    void setUp() {
//        meal = new Meal(1L, "Test Meal", "Category", "Area", "Instructions", "image.jpg", null);
//        mealDTO = new MealDTO("1", "Updated Meal", "Updated Category", "Updated Area", "Updated Instructions", "updated-image.jpg", null);
//    }
//
//    @Test
//    void testFetchAndSaveRandomMeal() {
//        when(mealRepository.save(any(Meal.class))).thenReturn(meal);
//        Meal savedMeal = mealService.fetchAndSaveRandomMeal();
//        assertNotNull(savedMeal);
//        verify(mealRepository, times(1)).save(any(Meal.class));
//    }
//
//    @Test
//    void testUpdateMealById() {
//        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
//        when(mealRepository.save(any(Meal.class))).thenReturn(meal);
//
//        boolean updated = mealService.updateMealById(1L, mealDTO);
//        assertTrue(updated);
//        verify(mealRepository, times(1)).save(any(Meal.class));
//    }
//
//    @Test
//    void testDeleteMealById() {
//        when(mealRepository.existsById(1L)).thenReturn(true);
//        doNothing().when(mealRepository).deleteById(1L);
//
//        boolean deleted = mealService.deleteMealById(1L);
//        assertTrue(deleted);
//        verify(mealRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testGetAllMeals() {
//        when(mealRepository.findAll()).thenReturn(List.of(meal));
//        List<Meal> meals = mealService.getAllMeals();
//        assertFalse(meals.isEmpty());
//        assertEquals(1, meals.size());
//    }
//
////    @Test
//    void testGetMealById() {
//        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
//
//        Optional<Meal> foundMeal = mealService.getMealById(1L);
//
//        assertTrue(foundMeal.isPresent());
//        assertEquals("Test Meal", foundMeal.get().getName());
//        verify(mealRepository, times(1)).findById(1L);
//    }
//}
