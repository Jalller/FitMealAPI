package app.daos;

import app.entities.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WorkoutDAOTests {

    @Autowired
    private WorkoutDAO workoutDAO;

    @Test
    void testSaveAndFindWorkout() {
        Workout workout = new Workout("123", "Test Workout", "Category", "Description");
        workoutDAO.save(workout);

        List<Workout> workouts = workoutDAO.findAll();

        assertThat(workouts).isNotEmpty();
        assertThat(workouts.get(0).getName()).isEqualTo("Test Workout");
    }

    @Test
    void testFindById() {
        Workout workout = new Workout("456", "FindMe", "TestCat", "Description");
        Workout saved = workoutDAO.save(workout);

        Optional<Workout> found = workoutDAO.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("FindMe");
    }

    @Test
    void testDeleteById() {
        Workout workout = new Workout("789", "DeleteMe", "Cat", "Description");
        Workout saved = workoutDAO.save(workout);
        Long id = saved.getId();

        workoutDAO.deleteById(id);

        assertThat(workoutDAO.findById(id)).isEmpty();
    }

    @Test
    void testSaveMultipleWorkouts() {
        Workout w1 = new Workout("1", "Workout 1", "Cat", "Desc");
        Workout w2 = new Workout("2", "Workout 2", "Cat", "Desc");

        workoutDAO.saveAll(List.of(w1, w2));

        List<Workout> workouts = workoutDAO.findAll();
        assertThat(workouts.size()).isGreaterThanOrEqualTo(2);
    }
}
