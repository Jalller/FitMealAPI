//package services;
//
//import app.dtos.WorkoutDTO;
//import app.entities.Workout;
//import app.daos.WorkoutDAO;
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
//    private WorkoutDAO workoutRepository;
//
//    @InjectMocks
//    private WorkoutService workoutService;
//
//    private Workout workout;
//    private WorkoutDTO workoutDTO;
//
//    @BeforeEach
//    void setUp() {
//        workout = new Workout(1L, "Test Workout", "Category", "Description");
//        workoutDTO = new WorkoutDTO("1", "Updated Workout", "Updated Category", "Updated Description");
//    }
//
//    @Test
//    void testFetchAndSaveRandomWorkout() {
//        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
//        Workout savedWorkout = workoutService.fetchAndSaveRandomWorkout();
//        assertNotNull(savedWorkout);
//        verify(workoutRepository, times(1)).save(any(Workout.class));
//    }
//
//    @Test
//    void testUpdateWorkoutById() {
//        when(workoutRepository.findById(1L)).thenReturn(Optional.of(workout));
//        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
//
//        boolean updated = workoutService.updateWorkoutById(1L, workoutDTO);
//        assertTrue(updated);
//        verify(workoutRepository, times(1)).save(any(Workout.class));
//    }
//
//    @Test
//    void testDeleteWorkoutById() {
//        when(workoutRepository.existsById(1L)).thenReturn(true);
//        doNothing().when(workoutRepository).deleteById(1L);
//
//        boolean deleted = workoutService.deleteWorkoutById(1L);
//        assertTrue(deleted);
//        verify(workoutRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testGetAllWorkouts() {
//        when(workoutRepository.findAll()).thenReturn(List.of(workout));
//        List<Workout> workouts = workoutService.getAllWorkouts();
//        assertFalse(workouts.isEmpty());
//        assertEquals(1, workouts.size());
//    }
//}
