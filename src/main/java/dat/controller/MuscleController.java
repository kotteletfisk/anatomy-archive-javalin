package dat.controller;

import dat.dao.MuscleDao;
import dat.dao.MuscleGroupDao;
import dat.dto.MuscleDTO;
import dat.entities.Muscle;
import dat.entities.MuscleGroup;
import dat.exception.ApiException;
import io.javalin.http.Context;
import io.javalin.validation.ValidationError;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        MuscleDTO muscleDTO = validate(context);
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
        MuscleDTO muscleDTO = validate(context);
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

    private MuscleDTO validate(Context context) throws ApiException
    {
        return context.bodyValidator(MuscleDTO.class)
                .check(muscleDTO -> muscleDTO.getName() != null && !muscleDTO.getName().isEmpty(), "Name cannot be empty")
                .check(muscleDTO -> muscleDTO.getName().matches("[a-zA-Z ]+"), "Name can only contain letters and spaces")
                .check(muscleDTO -> muscleDTO.getDescription() != null && !muscleDTO.getDescription().isEmpty(), "Description cannot be empty")
                .check(muscleDTO -> muscleDao.readByName(muscleDTO.getName()) == null, "Muscle name already exists")
                .get();
                 // TODO: Solution for this
                /*.getOrThrow(error -> {
                    Collection<? extends List<ValidationError<Object>>> errors = error.values();
                    System.out.println(errors);
                    Iterator<? extends List<ValidationError<Object>>> iterator = errors.iterator();
                    String msg = "";
                    while (iterator.hasNext())
                    {
                        List<ValidationError<Object>> next = iterator.next();
                        System.out.println(next);
                        msg += next.get(0).getMessage();
                        if (iterator.hasNext()) msg += ", ";
                    }
                    return new ApiException(400, msg);
                });*/

    }
}
