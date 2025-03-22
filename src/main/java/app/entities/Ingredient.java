package app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Meal meal;


    public Ingredient(String ingredientName, Meal meal) {
        this.ingredientName = ingredientName;
        this.meal = meal;
    }

}
