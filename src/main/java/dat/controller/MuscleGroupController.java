package dat.controller;

import dat.dao.MuscleGroupDao;
import dat.dto.MuscleGroupDTO;
import dat.entities.MuscleGroup;
import dat.exception.ApiException;
import io.javalin.http.Context;
import io.javalin.validation.ValidationError;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public void create(Context context) throws ApiException
    {
        MuscleGroupDTO muscleGroupDTO = validate(context);
        MuscleGroup muscleGroup = new MuscleGroup(muscleGroupDTO);
        muscleGroupDao.create(muscleGroup);
        context.status(201);
        context.json(new MuscleGroupDTO(muscleGroup));
    }

    public void update(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!muscleGroupDao.exists(id)) throw new ApiException(404, "Muscle group not found with id " + id);

        MuscleGroupDTO muscleGroupDTO = validate(context);
        MuscleGroup muscleGroup = new MuscleGroup(muscleGroupDTO);
        muscleGroup.setId(id);
        muscleGroupDao.update(muscleGroup);
        context.status(200);
        context.json(new MuscleGroupDTO(muscleGroup));
    }

    // Body validation method
    private MuscleGroupDTO validate(Context context)
    {
        return context.bodyValidator(MuscleGroupDTO.class)
                .check(muscleGroupDTO -> muscleGroupDTO.getName() != null && !muscleGroupDTO.getName().isEmpty(), "Muscle group name cannot be null or empty")
                .check(muscleGroupDTO -> muscleGroupDTO.getName().length() <= 50, "Muscle group name cannot be longer than 50 characters")
                .check(muscleGroupDTO -> muscleGroupDTO.getName().matches("^[a-zA-Z ]+$"), "Muscle group name can only contain alphanumeric characters and spaces")
                .check(muscleGroupDTO -> muscleGroupDTO.getDescription() != null && !muscleGroupDTO.getDescription().isEmpty(), "Muscle group description cannot be null or empty")
                .check(muscleGroupDTO -> muscleGroupDao.readByName(muscleGroupDTO.getName()) == null, "Muscle group name already exists")
                .get();
    }
}
