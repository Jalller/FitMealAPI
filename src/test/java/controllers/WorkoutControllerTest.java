package controllers;

import app.controllers.WorkoutController;
import app.dtos.WorkoutDTO;
import app.entities.Workout;
import app.services.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WorkoutControllerTest {

    @Mock
    private WorkoutService workoutService;

    @InjectMocks
    private WorkoutController workoutController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(workoutController).build();
    }

    @Test
    void testFetchAndSaveRandomWorkout() throws Exception {
        mockMvc.perform(post("/workouts/fetch-random"))
                .andExpect(status().isOk())
                .andExpect(content().string("Fetched and saved a random workout."));

        verify(workoutService, times(1)).fetchAndSaveRandomWorkout();
    }

    @Test
    void testFetchAndSaveMultipleWorkouts() throws Exception {
        mockMvc.perform(post("/workouts/fetch-multiple"))
                .andExpect(status().isOk())
                .andExpect(content().string("Fetched and saved multiple workouts."));

        verify(workoutService, times(1)).fetchAndSaveMultipleWorkouts();
    }

//    @Test
//    void testUpdateWorkoutById() throws Exception {
//        WorkoutDTO workoutDTO = new WorkoutDTO("15", "Updated Workout", "Category", "Updated Description");
//        when(workoutService.updateWorkoutById(15L, workoutDTO)).thenReturn(true);
//
//        mockMvc.perform(put("/workouts/update/{id}", 15L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\": \"15\", \"name\": \"Updated Workout\", \"category\": \"Category\", \"description\": \"Updated Description\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Workout updated successfully by ID!"));
//
//        verify(workoutService, times(1)).updateWorkoutById(eq(15L), any(WorkoutDTO.class));
//    }

    @Test
    void testDeleteWorkoutById() throws Exception {
        when(workoutService.deleteWorkoutById(25L)).thenReturn(true);

        mockMvc.perform(delete("/workouts/delete/{id}", 25L))
                .andExpect(status().isOk())
                .andExpect(content().string("Workout deleted successfully by ID!"));

        verify(workoutService, times(1)).deleteWorkoutById(25L);
    }
}
