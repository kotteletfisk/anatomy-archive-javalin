package dat.entities;

import dat.dto.MuscleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Muscle
{
    @Id
    @Column(name = "muscle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "muscle_name", columnDefinition = "TEXT", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = true)
    private String mediaPath;

    public Muscle(String name, String mediaPath, String description)
    {
        this.name = name;
        this.description = description;
        this.mediaPath = mediaPath;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private MuscleGroup muscleGroup;

    @OneToMany(mappedBy = "muscle", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<ExerciseHasMuscles> exerciseHasMusclesRelation = new HashSet<>();

    public Muscle(MuscleDTO muscleDTO)
    {
        if (muscleDTO.getId() != 0) this.id = muscleDTO.getId();
        this.name = muscleDTO.getName();
        this.description = muscleDTO.getDescription();
        this.mediaPath = muscleDTO.getMediaPath();
    }
}
