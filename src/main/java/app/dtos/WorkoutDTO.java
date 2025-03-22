package app.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {
    private String id;
    private String name;
    private String category;
    private String description;
    private List<String> muscles;

}
