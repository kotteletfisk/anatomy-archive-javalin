package dat.controller;

import dat.dao.DAO;
import dat.dao.ExerciseDao;
import dat.dao.MuscleDao;
import dat.dto.ExerciseDTO;
import dat.dto.MuscleDTO;
import dat.entities.Exercise;
import dat.entities.Muscle;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

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

    public void getMuscle(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");

        List<Muscle> muscles = muscleDAO.getMusclesByExercise(exerciseId);
        context.status(200);
        context.json(MuscleDTO.toMuscleDTOList(muscles));
    }
}
