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
public class Muscle
{
    @Id
    @Column(name = "muscle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "muscle_name", columnDefinition = "TEXT", nullable = false)
    private String name;
    @Column(nullable = true)
    private String mediaPath;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private MuscleGroup muscleGroup;

    @OneToMany(mappedBy = "muscle", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    Set<ExerciseHasMuscles> exerciseHasMuscles = new HashSet<>();
}
