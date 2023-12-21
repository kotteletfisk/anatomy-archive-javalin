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

    public void getMuscleByEquipmentPattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<Muscle> muscles = muscleDao.readLikeEquipmentPattern(pattern);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void getMuscleByExercisePattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<Muscle> muscles = muscleDao.getLikeExercisePattern(pattern);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void create(Context context)
    {
        MuscleDTO muscleDTO = context.bodyAsClass(MuscleDTO.class);
        Muscle muscle = new Muscle(muscleDTO);
        MuscleGroup muscleGroup = muscleGroupDao.read(muscleDTO.getMuscleGroupId());
        muscle.setMuscleGroup(muscleGroup);

        muscleDao.create(muscle);
        context.status(201);
        context.json(new MuscleDTO(muscle));
    }

    public void update(Context context)
    {
        int id = Integer.parseInt(context.pathParam("id"));
        MuscleDTO muscleDTO = context.bodyAsClass(MuscleDTO.class);
        Muscle muscle = new Muscle(muscleDTO);
        muscle.setId(id);
        muscleDao.update(muscle);
        context.status(200);
        context.json(new MuscleDTO(muscle));
    }
}
