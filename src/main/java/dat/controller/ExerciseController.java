package dat.controller;

import dat.dao.DAO;
import dat.dao.ExerciseDao;
import dat.dao.MuscleDao;
import dat.dto.EquipmentDTO;
import dat.dto.ExerciseDTO;
import dat.dto.ExerciseTypeDTO;
import dat.dto.MuscleDTO;
import dat.entities.Equipment;
import dat.entities.Exercise;
import dat.entities.ExerciseType;
import dat.entities.Muscle;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;
import java.util.Set;

public class ExerciseController
{
    ExerciseDao exerciseDAO = ExerciseDao.getInstance();
    MuscleDao muscleDAO = MuscleDao.getInstance();

    public void getAll(Context context) throws Exception
    {
        List<Exercise> exercises = exerciseDAO.readAll();
        if (exercises.isEmpty()) throw new ApiException(500, "Problem with database");
        List<ExerciseDTO> dtos = ExerciseDTO.toExerciseDTOList(exercises);
        context.status(200);
        context.json(dtos);
    }

    public void getById(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(id)) throw new ApiException(404, "Exercise with id " + id + " not found");
        Exercise exercise = exerciseDAO.read(id);
        ExerciseDTO dto = new ExerciseDTO(exercise);
        context.status(200);
        context.json(dto);
    }

    public void create(Context context) throws ApiException
    {
        ExerciseDTO dto;
        try
        {
            dto = context.bodyAsClass(ExerciseDTO.class);
        }
        catch (Exception e)
        {
            throw new ApiException(400, e.getMessage());
        }
        Exercise exercise = exerciseDAO.create(new Exercise(dto));
        dto = new ExerciseDTO(exercise);
        context.status(201);
        context.json(dto);
    }

    public void update(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        ExerciseDTO dto;

        if (!exerciseDAO.exists(id)) throw new ApiException(404, "Exercise with id " + id + " not found");

        try
        {
            dto = context.bodyAsClass(ExerciseDTO.class);
        }
        catch (Exception e)
        {
            throw new ApiException(400, e.getMessage());
        }
        Exercise exercise = new Exercise(dto);
        exercise.setId(id);
        dto = new ExerciseDTO(exerciseDAO.update(exercise));
        context.status(200);
        context.json(dto);
    }

    public void delete(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(id)) throw new ApiException(404, "Exercise with id " + id + " not found");
        exerciseDAO.delete(id);
        context.status(204);
    }

    public void addMuscle(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.queryParam("exerciseId"));
        int muscleId = Integer.parseInt(context.queryParam("muscleId"));

        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        if (!muscleDAO.exists(muscleId)) throw new ApiException(404, "Muscle with id " + muscleId + " not found");

        exerciseDAO.addMuscleToExercise(exerciseId, muscleId);
        context.status(201);
    }

    public void getMuscle(Context context) throws Exception
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        List<Muscle> muscles = muscleDAO.getMusclesByExercise(exerciseId);
        context.status(200);
        context.json(MuscleDTO.toMuscleDTOList(muscles));
    }

    public void addType(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.queryParam("exerciseId"));
        int typeId = Integer.parseInt(context.queryParam("typeId"));

        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        if (!exerciseDAO.exists(typeId)) throw new ApiException(404, "Type with id " + typeId + " not found");

        exerciseDAO.addTypeToExercise(exerciseId, typeId);
        context.status(201);
    }

    public void getType(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        List<ExerciseType> exerciseTypes = exerciseDAO.getTypesByExercise(exerciseId);
        context.status(200);
        context.json(ExerciseTypeDTO.toExerciseTypeDTOList(exerciseTypes));
    }

    public void addEquipment(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.queryParam("exerciseId"));
        int equipmentId = Integer.parseInt(context.queryParam("equipmentId"));

        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        if (!exerciseDAO.exists(equipmentId)) throw new ApiException(404, "Equipment with id " + equipmentId + " not found");

        exerciseDAO.addEquipmentToExercise(exerciseId, equipmentId);
        context.status(201);
    }

    public void getEquipment(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        List<Equipment> equipments = exerciseDAO.getEquipmentByExercise(exerciseId);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipments));
    }
}
