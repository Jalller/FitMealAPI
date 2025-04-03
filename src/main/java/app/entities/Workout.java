package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workouts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workoutId; // ID from WGER API
    private String name;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    // NEW: link workout to user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Workout(String workoutId, String name, String category, String description) {
        this.workoutId = workoutId;
        this.name = name;
        this.category = category;
        this.description = description;
    }
}
