package dat.entities;

import java.util.HashSet;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class MuscleGroup
{
    @Id
    @Column(name = "muscle_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "muscle_group_name", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = true)
    private String mediaPath;

    @OneToMany(mappedBy = "muscleGroup")
    Set<Muscle> muscles = new HashSet<>();
}
