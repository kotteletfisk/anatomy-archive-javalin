package dat.dto;

import dat.entities.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExerciseTypeDTO
{
    private int id;
    private String typeName;

    public ExerciseTypeDTO(String typeName)
    {
        this.typeName = typeName;
    }

    public ExerciseTypeDTO(ExerciseType exerciseType)
    {
        if (exerciseType.getId() != 0) this.id = exerciseType.getId();
        this.typeName = exerciseType.getTypeName();
    }

    public static List<ExerciseTypeDTO> toExerciseTypeDTOList(List<ExerciseType> exerciseTypes)
    {
        return exerciseTypes.stream().map(ExerciseTypeDTO::new).collect(Collectors.toList());
    }
}
