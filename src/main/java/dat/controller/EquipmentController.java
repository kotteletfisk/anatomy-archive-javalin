package dat.controller;

import dat.dao.EquipmentDao;
import dat.dto.EquipmentDTO;
import dat.entities.Equipment;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class EquipmentController
{
    EquipmentDao equipmentDao = EquipmentDao.getInstance();

    public void getByEquipmentPattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Equipment> equipment = equipmentDao.readLikeNamePattern(pattern);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipment));
    }

    public void getByMusclePattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Equipment> equipment = equipmentDao.getLikeMusclePattern(pattern);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipment));
    }

    public void getByExercisePattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Equipment> equipment = equipmentDao.getLikeExercisePattern(pattern);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipment));
    }

    public void create(Context context) throws ApiException
    {
        EquipmentDTO equipmentDTO = validate(context);
        Equipment equipment = new Equipment(equipmentDTO);
        equipmentDao.create(equipment);
        context.status(201);
        context.json(new EquipmentDTO(equipment));
    }

    public void update(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        EquipmentDTO equipmentDTO = validate(context);
        Equipment equipment = new Equipment(equipmentDTO);
        equipment.setId(id);
        equipmentDao.update(equipment);
        context.status(200);
        context.json(new EquipmentDTO(equipment));
    }

    public void getById(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!equipmentDao.exists(id)) throw new ApiException(404, "Equipment not found with id " + id);
        Equipment equipment = equipmentDao.read(id);
        context.status(200);
        context.json(new EquipmentDTO(equipment));
    }

    private EquipmentDTO validate(Context context)
    {
        return context.bodyValidator(EquipmentDTO.class)
                .check((equipmentDTO) -> equipmentDTO.getName() != null && !equipmentDTO.getName().isEmpty(), "Equipment name cannot be empty or longer than 50 characters")
                .check((equipmentDTO) -> equipmentDTO.getName().length() <= 50, "Equipment name cannot be empty or longer than 50 characters")
                .check((equipmentDTO -> !equipmentDTO.getDescription().isEmpty()), "Equipment description cannot be empty")
                .check((equipmentDTO) -> equipmentDao.readByName(equipmentDTO.getName()) == null, "Equipment name already exists")
                .get();
    }
}
