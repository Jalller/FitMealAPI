package app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealId; // ID from TheMealDB API
    private String name;
    private String category;
    private String area;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    private String imageUrl;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Prevent infinite recursion
    private List<Ingredient> ingredients;

    // NEW: link meal to user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal(String mealId, String name, String category, String area, String instructions, String imageUrl) {
        this.mealId = mealId;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }

    public Meal(String name, String category, String area, String instructions, String imageUrl, List<Ingredient> ingredients) {
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }
}
