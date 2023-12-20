package dat.dto;

import dat.entities.MuscleGroup;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MuscleGroupDTO
{
    private int id;
    private String name;
    private String description;
    private String mediaPath;

    public MuscleGroupDTO(MuscleGroup muscleGroup)
    {
        if (muscleGroup.getId() != 0) this.id = muscleGroup.getId();
        this.name = muscleGroup.getName();
        this.description = muscleGroup.getDescription();
        this.mediaPath = muscleGroup.getMediaPath();
    }

    public static List<MuscleGroupDTO> toMuscleGroupDTOList(List<MuscleGroup> muscleGroups)
    {
        return muscleGroups.stream().map(MuscleGroupDTO::new).collect(java.util.stream.Collectors.toList());
    }
}
