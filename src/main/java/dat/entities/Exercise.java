package dat.entities;

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
public class Exercise
{
    @Id
    @Column(name = "exercise_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "exercise_name", nullable = false)
    private String name;
    @Column(name = "exercise_description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "exercise_mediapath", nullable = true)
    private String mediaPath;
    @Column(name = "exercise_intensity", nullable = false)
    private int intensity;

    @OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    Set<ExerciseHasMuscles> exerciseHasMuscles = new HashSet<>();

    @OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    Set<ExerciseHasEquipment> exerciseHasEquipment = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<ExerciseType> exerciseTypes = new HashSet<>();

    public Exercise(String name, String description, String mediaPath, int intensity)
    {
        this.name = name;
        this.description = description;
        this.mediaPath = mediaPath;
        this.intensity = intensity;
    }

    public void addExerciseType(ExerciseType exerciseType)
    {
        if (exerciseType == null)
        {
            throw new NullPointerException("Added exercise type is null!");
        }
        this.exerciseTypes.add(exerciseType);
        exerciseType.getExercises().add(this);
    }

    public ExerciseHasMuscles addMuscle(Muscle muscle)
    {
        if (muscle == null)
        {
            throw new NullPointerException("Added muscle is null!");
        }
        ExerciseHasMuscles exerciseHasMuscles = new ExerciseHasMuscles();
        exerciseHasMuscles.setExercise(this);
        exerciseHasMuscles.setMuscle(muscle);
        this.exerciseHasMuscles.add(exerciseHasMuscles);
        return exerciseHasMuscles;
    }

    public ExerciseHasEquipment addEquipment(Equipment equipment)
    {
        if (equipment == null)
        {
            throw new NullPointerException("Added equipment is null!");
        }
        ExerciseHasEquipment exerciseHasEquipment = new ExerciseHasEquipment();
        exerciseHasEquipment.setExercise(this);
        exerciseHasEquipment.setEquipment(equipment);
        this.exerciseHasEquipment.add(exerciseHasEquipment);
        return exerciseHasEquipment;
    }
}
