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
    private ExerciseTypeEnum exerciseTypeEnum;

    @ManyToMany
    private Set<Exercise> exercises = new HashSet<>();

    public ExerciseType(ExerciseTypeEnum exerciseTypeEnum)
    {
        this.exerciseTypeEnum = exerciseTypeEnum;
    }

    public enum ExerciseTypeEnum
    {
        CALISTHENIC,
        WEIGHTLIFTING,
        CARDIO,
        STRETCHING,
        ISOMETRIC,
        DYNAMIC;
    }
}
