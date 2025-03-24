//package services;
//
//import app.daos.WorkoutDAO;
//import app.dtos.WorkoutDTO;
//import app.entities.Workout;
//import app.services.WorkoutService;
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
//class WorkoutServiceTest {
//
//    @Mock
//    private WorkoutDAO workoutDAO;
//
//    @InjectMocks
//    private WorkoutService workoutService;
//
//    private Workout sampleWorkout;
//
//    @BeforeEach
//    void setUp() {
//        sampleWorkout = new Workout("1", "Test Workout", "Category", "Description");
//    }
//
//    @Test
//    void shouldGetAllWorkouts() {
//        when(workoutDAO.findAll()).thenReturn(List.of(sampleWorkout));
//        List<Workout> workouts = workoutService.getAllWorkouts();
//        assertFalse(workouts.isEmpty());
//        assertEquals(1, workouts.size());
//        verify(workoutDAO, times(1)).findAll();
//    }
//
//    @Test
//    void shouldUpdateWorkout() {
//        WorkoutDTO updatedWorkoutDTO = new WorkoutDTO("1", "Updated Workout", "Updated Category", "Updated Description");
//        when(workoutDAO.findById(1L)).thenReturn(Optional.of(sampleWorkout));
//        when(workoutDAO.save(any(Workout.class))).thenReturn(sampleWorkout);
//
//        Workout updatedWorkout = workoutService.updateWorkout(1L, updatedWorkoutDTO);
//        assertEquals("Updated Workout", updatedWorkout.getName());
//        verify(workoutDAO, times(1)).save(any(Workout.class));
//    }
//
//    @Test
//    void shouldDeleteWorkout() {
//        when(workoutDAO.existsById(1L)).thenReturn(true);
//        doNothing().when(workoutDAO).deleteById(1L);
//
//        workoutService.deleteWorkout(1L);
//        verify(workoutDAO, times(1)).deleteById(1L);
//    }
//}
