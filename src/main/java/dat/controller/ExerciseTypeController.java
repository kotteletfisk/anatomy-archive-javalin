package dat.controller;

import dat.dao.ExerciseTypeDao;
import dat.dto.ExerciseTypeDTO;
import dat.entities.ExerciseType;
import io.javalin.http.Context;

import java.util.List;

public class ExerciseTypeController
{
    ExerciseTypeDao exerciseTypeDao = ExerciseTypeDao.getInstance();

    public void getByTypePattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<ExerciseType> exerciseTypes = exerciseTypeDao.readLikeNamePattern(pattern);
        context.status(200);
        context.json(ExerciseTypeDTO.toExerciseTypeDTOList(exerciseTypes));
    }
}
