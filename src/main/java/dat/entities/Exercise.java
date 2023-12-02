package dat.entities;

import dat.dto.ExerciseDTO;
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

    @OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<ExerciseHasMuscles> exerciseHasMusclesRelation = new HashSet<>();

    @OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<ExerciseHasEquipment> exerciseHasEquipmentRelation = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "exercisehastypes",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_type_id"))
    Set<ExerciseType> exerciseTypes = new HashSet<>();

    public Exercise(String name, String description, String mediaPath, int intensity)
    {
        this.name = name;
        this.description = description;
        this.mediaPath = mediaPath;
        this.intensity = intensity;
    }

    public Exercise(ExerciseDTO dto)
    {
        if (dto.getId() != 0) this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.mediaPath = dto.getMediaPath();
        this.intensity = dto.getIntensity();
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

        this.exerciseHasMusclesRelation.add(exerciseHasMuscles);
        muscle.getExerciseHasMuscles().add(exerciseHasMuscles);
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
        this.exerciseHasEquipmentRelation.add(exerciseHasEquipment);
        equipment.getExerciseHasEquipmentRelation().add(exerciseHasEquipment);
        return exerciseHasEquipment;
    }

    public Set<Muscle> getMuscles()
    {
        return exerciseHasMusclesRelation.stream()
                .map(ExerciseHasMuscles::getMuscle)
                .collect(java.util.stream.Collectors.toSet());
    }
}
