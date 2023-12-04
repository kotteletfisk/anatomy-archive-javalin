package dat.dto;

import dat.entities.Equipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EquipmentDTO
{
    private int id;
    private String name;
    private String description;

    public EquipmentDTO(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public EquipmentDTO(Equipment equipment)
    {
        if (equipment.getId() != 0) this.id = equipment.getId();
        this.name = equipment.getName();
        this.description = equipment.getDescription();
    }

    public static List<EquipmentDTO> toEquipmentDTOList(List<Equipment> equipment)
    {
        return equipment.stream()
                .map(EquipmentDTO::new)
                .collect(Collectors.toList());
    }
}
