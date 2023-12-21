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

    public void getByMusclePattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<Equipment> equipment = equipmentDao.getLikeMusclePattern(pattern);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipment));
    }

    public void getByExercisePattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<Equipment> equipment = equipmentDao.getLikeExercisePattern(pattern);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipment));
    }
}
