package app.daos;


import app.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealDAO extends JpaRepository<Meal, Long> {}
