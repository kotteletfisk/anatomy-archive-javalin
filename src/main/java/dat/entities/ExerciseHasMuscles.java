package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ExerciseHasMuscles
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_has_muscles_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.EAGER)
    private Muscle muscle;
}
