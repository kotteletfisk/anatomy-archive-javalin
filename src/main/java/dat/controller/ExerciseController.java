package dat.controller;

import dat.dao.DAO;
import dat.dao.ExerciseDao;
import dat.dto.ExerciseDTO;
import dat.entities.Exercise;
import io.javalin.http.Context;

import java.util.List;

public class ExerciseController
{
    DAO<Exercise> exerciseDAO = ExerciseDao.getInstance();

    public void getAll(Context context)
    {
        List<Exercise> exercises = exerciseDAO.readAll();
        List<ExerciseDTO> dtos = ExerciseDTO.toExerciseDTOList(exercises);
        context.status(200);
        context.json(dtos);
    }

    public void getById(Context context)
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(id))
        {
            context.status(404);
            context.json("Exercise with id " + id + " not found");
            return;
        }
        Exercise exercise = exerciseDAO.read(id);
        ExerciseDTO dto = new ExerciseDTO(exercise);
        context.status(200);
        context.json(dto);
    }

    public void create(Context context)
    {
        ExerciseDTO dto;
        try
        {
            dto = context.bodyAsClass(ExerciseDTO.class);
        }
        catch (Exception e) // TODO: change to ApiException
        {
            context.status(400);
            context.json(e.getMessage());
            return;
        }

        Exercise exercise = exerciseDAO.create(new Exercise(dto));
        dto = new ExerciseDTO(exercise);
        context.status(201);
        context.json(dto);
    }

    public void update(Context context)
    {
        int id = Integer.parseInt(context.pathParam("id"));
        ExerciseDTO dto;

        if (!exerciseDAO.exists(id))
        {
            context.status(404);
            context.json("Exercise with id " + id + " not found");
            return;
        }
        try
        {
            dto = context.bodyAsClass(ExerciseDTO.class);
        }
        catch (Exception e) // TODO: change to ApiException
        {
            context.status(400);
            context.json(e.getMessage());
            return;
        }
        Exercise exercise = new Exercise(dto);
        exercise.setId(id);
        dto = new ExerciseDTO(exerciseDAO.update(exercise));
        context.status(200);
        context.json(dto);
    }
}
