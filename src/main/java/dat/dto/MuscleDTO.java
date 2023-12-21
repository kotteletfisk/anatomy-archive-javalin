package dat.dto;

import dat.entities.Muscle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MuscleDTO
{
    private int id;
    private String name;
    private String description;
    private String mediaPath;
    private int muscleGroupId;

    public MuscleDTO(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public MuscleDTO(Muscle muscle)
    {
        if (muscle.getId() != 0) this.id = muscle.getId();
        this.name = muscle.getName();
        this.description = muscle.getDescription();
        this.mediaPath = muscle.getMediaPath();
        this.muscleGroupId = muscle.getMuscleGroup().getId();
    }

    public static List<MuscleDTO> toMuscleDTOList(List<Muscle> muscles)
    {
        return muscles.stream().map(MuscleDTO::new).collect(Collectors.toList());
    }
}
