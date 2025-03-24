//package controllers;
//
//import app.controllers.MealController;
//import app.entities.Meal;
//import app.services.MealService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(MealController.class)
//class MealControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MealService mealService;
//
//    @Test
//    void shouldGetAllMeals() throws Exception {
//        when(mealService.getAllMeals()).thenReturn(List.of(new Meal("1", "Test Meal", "Category", "Area", "Instructions", "image.jpg")));
//
//        mockMvc.perform(get("/meals"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("Test Meal"));
//
//        verify(mealService, times(1)).getAllMeals();
//    }
//}
