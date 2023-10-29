package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
public enum ExerciseType
{
    CALISTHENIC,
    WEIGHTLIFTING,
    CARDIO,
    STRETCHING,
    ISOMETRIC,
    DYNAMIC;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_type_id")
    private int id;

    @ManyToMany
    private Set<Exercise> exercises = new HashSet<>();
}

