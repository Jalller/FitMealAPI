package app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientName;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    @JsonBackReference // Prevent infinite recursion
    private Meal meal;

    public Ingredient(String ingredientName, Meal meal) {
        this.ingredientName = ingredientName;
        this.meal = meal;
    }
}
