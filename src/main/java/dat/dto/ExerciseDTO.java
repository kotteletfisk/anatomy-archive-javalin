package dat.dto;

import dat.entities.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO
{
    private int id;
    private String name;
    private String description;
    private String mediaPath;
    private int intensity;

    public ExerciseDTO(String name, String description, String mediaPath, int intensity)
    {
        this.name = name;
        this.description = description;
        this.mediaPath = mediaPath;
        this.intensity = intensity;
    }

    public ExerciseDTO(Exercise exercise)
    {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.description = exercise.getDescription();
        this.mediaPath = exercise.getMediaPath();
        this.intensity = exercise.getIntensity();
    }

    public static List<ExerciseDTO> toExerciseDTOList(List<Exercise> exercises)
    {
        return exercises.stream().map(ExerciseDTO::new).collect(Collectors.toList());
    }
}
