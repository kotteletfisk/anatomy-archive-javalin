package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class ExerciseTypeWrapper
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_type_id")
    private int id;
    private ExerciseType exerciseType;

    @ManyToMany
    private Set<Exercise> exercises = new HashSet<>();

    public ExerciseTypeWrapper(ExerciseType exerciseType)
    {
        this.exerciseType = exerciseType;
    }

    public enum ExerciseType
    {
        CALISTHENIC,
        WEIGHTLIFTING,
        CARDIO,
        STRETCHING,
        ISOMETRIC,
        DYNAMIC;
    }
}
