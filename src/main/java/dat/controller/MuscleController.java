package dat.controller;

import dat.dao.MuscleDao;
import dat.dao.MuscleGroupDao;
import dat.dto.MuscleDTO;
import dat.entities.Muscle;
import dat.entities.MuscleGroup;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class MuscleController
{
    MuscleDao muscleDao = MuscleDao.getInstance();
    MuscleGroupDao muscleGroupDao = MuscleGroupDao.getInstance();

    public void getAll(Context context) throws ApiException
    {
        List<Muscle> muscles = muscleDao.readAll();
        if (muscles.isEmpty()) throw new ApiException(500, "Problem with database");
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void getLikeName(Context context) throws ApiException
    {
        String name = context.pathParam("name");
        List<Muscle> muscles = muscleDao.getLikeName(name);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void getMuscleByNamePattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Muscle> muscles = muscleDao.getLikeNamePattern(pattern);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void getMuscleByEquipmentPattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Muscle> muscles = muscleDao.readLikeEquipmentPattern(pattern);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void getMuscleByExercisePattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Muscle> muscles = muscleDao.getLikeExercisePattern(pattern);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void create(Context context) throws ApiException
    {
        MuscleDTO muscleDTO = context.bodyAsClass(MuscleDTO.class);
        Muscle muscle = new Muscle(muscleDTO);
        MuscleGroup muscleGroup = muscleGroupDao.read(muscleDTO.getMuscleGroupId());
        muscle.setMuscleGroup(muscleGroup);

        muscleDao.create(muscle);
        context.status(201);
        context.json(new MuscleDTO(muscle));
    }

    public void update(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!muscleDao.exists(id)) throw new ApiException(404, "Muscle not found with id " + id);
        MuscleDTO muscleDTO = context.bodyAsClass(MuscleDTO.class);
        Muscle muscle = new Muscle(muscleDTO);

        if (!muscleGroupDao.exists(muscleDTO.getMuscleGroupId()))
            throw new ApiException(404, "Muscle group not found with id " + muscleDTO.getMuscleGroupId());

        MuscleGroup muscleGroup = muscleGroupDao.read(muscleDTO.getMuscleGroupId());
        muscle.setId(id);
        muscle.setMuscleGroup(muscleGroup);
        muscleDao.update(muscle);
        context.status(200);
        context.json(new MuscleDTO(muscle));
    }

    public void getById(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!muscleDao.exists(id)) throw new ApiException(404, "Muscle not found with id " + id);
        Muscle muscle = muscleDao.read(id);
        context.status(200);
        context.json(new MuscleDTO(muscle));
    }
}
