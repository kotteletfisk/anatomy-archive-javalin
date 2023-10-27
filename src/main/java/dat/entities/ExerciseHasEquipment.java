package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class ExerciseHasEquipment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_has_equipment_id")
    private int id;

    @ManyToOne
    private Exercise exercise;

    @ManyToOne
    private Equipment equipment;
}
