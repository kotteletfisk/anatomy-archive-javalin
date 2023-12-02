package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
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
        this.typeName = typeName;
    }
}
