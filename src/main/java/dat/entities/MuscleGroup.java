package dat.entities;

import java.util.HashSet;
import java.util.List;

import dat.dto.MuscleGroupDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class MuscleGroup
{
    @Id
    @Column(name = "muscle_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "muscle_group_name", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = true)
    private String mediaPath;

    public MuscleGroup(String name, String mediaPath, String description)
    {
        this.name = name;
        this.description = description;
        this.mediaPath = mediaPath;
    }

    public MuscleGroup(MuscleGroupDTO muscleGroupDTO)
    {
        if (muscleGroupDTO.getId() != 0) this.id = muscleGroupDTO.getId();
        this.name = muscleGroupDTO.getName();
        this.description = muscleGroupDTO.getDescription();
        this.mediaPath = muscleGroupDTO.getMediaPath();
    }

    public void addMuscle(Muscle muscle)
    {
        if (muscle == null)
        {
            throw new NullPointerException("Added muscle is null!");
        }
        this.muscles.add(muscle);
    }

    @OneToMany(mappedBy = "muscleGroup", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    Set<Muscle> muscles = new HashSet<>();
}
