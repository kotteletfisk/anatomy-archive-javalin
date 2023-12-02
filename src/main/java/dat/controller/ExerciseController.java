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
        Exercise exercise = exerciseDAO.read(id);
        ExerciseDTO dto = new ExerciseDTO(exercise);
        context.status(200);
        context.json(dto);
    }
}
