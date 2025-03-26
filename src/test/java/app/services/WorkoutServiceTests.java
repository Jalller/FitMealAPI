package app.services;

import app.daos.WorkoutDAO;
import app.entities.Workout;
import app.dtos.WorkoutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WorkoutServiceTests {

    @InjectMocks
    private WorkoutService workoutService;

    @Mock
    private WorkoutDAO workoutRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAndSaveMultipleWorkouts() {
        workoutService.fetchAndSaveMultipleWorkouts();
        verify(workoutRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testFetchAndSaveRandomWorkout() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Random Workout");
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        // Act
        Workout savedWorkout = workoutService.fetchAndSaveRandomWorkout();

        // Assert
        verify(workoutRepository, times(1)).save(any(Workout.class));
        assertNotNull(savedWorkout);
        assertEquals("Random Workout", savedWorkout.getName());
    }

    @Test
    public void testUpdateWorkoutById() {
        // Arrange
        WorkoutDTO workoutDTO = new WorkoutDTO("15", "updated workout", "category", "description");
        Workout existingWorkout = new Workout("15", "Old Workout", "Old Category", "Old description");
        when(workoutRepository.findById(15L)).thenReturn(java.util.Optional.of(existingWorkout));
        when(workoutRepository.save(any(Workout.class))).thenReturn(existingWorkout);

        // Act
        Workout updatedWorkout = workoutService.updateWorkout(15L, workoutDTO);

        // Assert
        verify(workoutRepository, times(1)).save(any(Workout.class));
        assertEquals("updated workout", updatedWorkout.getName());
        assertEquals("category", updatedWorkout.getCategory());
        assertEquals("description", updatedWorkout.getDescription());
    }

//    @Test
//    public void testDeleteWorkoutById() {
//        // Arrange
//        doNothing().when(workoutRepository).deleteById(25L);
//
//        // Act
//        workoutService.deleteWorkoutById(25L);
//
//        // Assert
//        verify(workoutRepository, times(1)).deleteById(25L);
//    }

    @Test
    public void testUpdateFirstAvailableWorkout() {
        // Arrange
        Workout workout = new Workout("1", "First Workout", "Category A", "Description A");
        when(workoutRepository.findAll()).thenReturn(java.util.List.of(workout));
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        // Act
        workoutService.updateFirstAvailableWorkout();

        // Assert
        verify(workoutRepository, times(1)).save(any(Workout.class));
        assertEquals("updated workout name", workout.getName());
        assertEquals("updated category", workout.getCategory());
        assertEquals("updated workout description...", workout.getDescription());
    }

    @Test
    public void testDeleteFirstAvailableWorkout() {
        // Arrange
        Workout workout = new Workout("1", "First Workout", "Category A", "Description A");
        when(workoutRepository.findAll()).thenReturn(java.util.List.of(workout));
        doNothing().when(workoutRepository).deleteById(workout.getId());

        // Act
        workoutService.deleteFirstAvailableWorkout();

        // Assert
        verify(workoutRepository, times(1)).deleteById(workout.getId());
    }

    @Test
    public void testUpdateLastWorkout() {
        // Arrange
        Workout workout = new Workout("1", "Last Workout", "Category Z", "Description Z");
        when(workoutRepository.findAll()).thenReturn(java.util.List.of(workout));
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        // Act
        workoutService.updateLastAvailableWorkout();

        // Assert
        verify(workoutRepository, times(1)).save(any(Workout.class));
        assertEquals("updated last workout name", workout.getName());
        assertEquals("updated last category", workout.getCategory());
        assertEquals("updated last workout description...", workout.getDescription());
    }

    @Test
    public void testDeleteLastWorkout() {
        // Arrange
        Workout workout = new Workout("1", "Last Workout", "Category Z", "Description Z");
        when(workoutRepository.findAll()).thenReturn(java.util.List.of(workout));
        doNothing().when(workoutRepository).deleteById(workout.getId());

        // Act
        workoutService.deleteLastAvailableWorkout();

        // Assert
        verify(workoutRepository, times(1)).deleteById(workout.getId());
    }
}
