package dat.entities;

import dat.dto.ExerciseTypeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ExerciseType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_type_id")
    private int id;
    @Column(name = "exercise_type", nullable = false)
    private String typeName;

    @ManyToMany(mappedBy = "exerciseTypes", fetch = FetchType.EAGER)
    private Set<Exercise> exercises = new HashSet<>();

    public ExerciseType(String typeName)
    {
        this.typeName = typeName.toUpperCase();
    }

    public ExerciseType(ExerciseTypeDTO exerciseTypeDTO)
    {
        if (exerciseTypeDTO.getId() != 0) this.id = exerciseTypeDTO.getId();
        this.typeName = exerciseTypeDTO.getTypeName().toUpperCase();
    }
}
