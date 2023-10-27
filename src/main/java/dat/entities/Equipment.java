package dat.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Equipment
{
    @Id
    @Column(name = "equipment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "equipment_name", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = true)
    private String mediaPath;

    @OneToMany(mappedBy = "equipment")
    Set<ExerciseHasEquipment> exerciseHasEquipment = new HashSet<>();
}
