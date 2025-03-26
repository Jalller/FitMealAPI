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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();
    }

    @Test
    void testFetchMeals() throws Exception {
        mockMvc.perform(get("/api/meals"))
                .andExpect(status().isOk());

        verify(mealService, times(1)).fetchAndSaveMultipleMeals();
        verify(mealService, times(1)).getAllMeals();
    }

    @Test
    void testAddMeal() throws Exception {
        Meal meal = new Meal();
        meal.setName("New Meal");

        when(mealService.fetchAndSaveRandomMeal()).thenReturn(meal);

        mockMvc.perform(post("/api/meals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Meal"));

        verify(mealService, times(1)).fetchAndSaveRandomMeal();
    }

//    @Test
//    void testUpdateMealById() throws Exception {
//        MealDTO mealDTO = new MealDTO("10", "Updated Meal", "Category", "Area", "Instructions", "image_url", null);
//        when(mealService.updateMealById(10L, mealDTO)).thenReturn(true);
//
//        mockMvc.perform(put("/api/meals/update/{id}", 10L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"idMeal\": \"10\", \"strMeal\": \"Updated Meal\", \"strCategory\": \"Category\", \"strArea\": \"Area\", \"strInstructions\": \"Instructions\", \"strMealThumb\": \"image_url\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Meal updated successfully by ID!"));
//
//        verify(mealService, times(1)).updateMealById(eq(10L), any(MealDTO.class));
//    }
//
//    @Test
//    void testDeleteMealById() throws Exception {
//        when(mealService.deleteMealById(20L)).thenReturn(true);
//
//        mockMvc.perform(delete("/api/meals/delete/{id}", 20L))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Meal deleted successfully by ID!"));
//
//        verify(mealService, times(1)).deleteMealById(20L);
//    }
}
