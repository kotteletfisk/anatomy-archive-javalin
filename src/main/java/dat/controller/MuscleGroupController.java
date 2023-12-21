package dat.controller;

import dat.dao.MuscleGroupDao;
import dat.dto.MuscleGroupDTO;
import dat.entities.MuscleGroup;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class MuscleGroupController
{
    MuscleGroupDao muscleGroupDao = MuscleGroupDao.getInstance();

    public void getByNamePattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<MuscleGroup> muscleGroups = muscleGroupDao.readLikeNamePattern(pattern);
        context.status(200);
        context.json(MuscleGroupDTO.toMuscleGroupDTOList(muscleGroups));
    }

    public void getById(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!muscleGroupDao.exists(id)) throw new ApiException(404, "Muscle group not found with id " + id);
        MuscleGroup muscleGroup = muscleGroupDao.read(id);
        context.status(200);
        context.json(new MuscleGroupDTO(muscleGroup));
    }

    public void create(Context context)
    {
        MuscleGroupDTO muscleGroupDTO = context.bodyAsClass(MuscleGroupDTO.class);
        MuscleGroup muscleGroup = new MuscleGroup(muscleGroupDTO);
        muscleGroupDao.create(muscleGroup);
        context.status(201);
        context.json(new MuscleGroupDTO(muscleGroup));
    }

    public void update(Context context)
    {
        int id = Integer.parseInt(context.pathParam("id"));
        MuscleGroupDTO muscleGroupDTO = context.bodyAsClass(MuscleGroupDTO.class);
        MuscleGroup muscleGroup = new MuscleGroup(muscleGroupDTO);
        muscleGroup.setId(id);
        muscleGroupDao.update(muscleGroup);
        context.status(200);
        context.json(new MuscleGroupDTO(muscleGroup));
    }
}
