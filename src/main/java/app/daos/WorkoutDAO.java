package app.daos;

import app.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutDAO extends JpaRepository<Workout, Long> {}
