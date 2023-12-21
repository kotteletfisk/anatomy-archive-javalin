package dat.controller;

import dat.dao.ExerciseTypeDao;
import dat.dto.ExerciseTypeDTO;
import dat.entities.ExerciseType;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class ExerciseTypeController
{
    ExerciseTypeDao exerciseTypeDao = ExerciseTypeDao.getInstance();

    public void getByTypePattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<ExerciseType> exerciseTypes = exerciseTypeDao.readLikeNamePattern(pattern);
        context.status(200);
        context.json(ExerciseTypeDTO.toExerciseTypeDTOList(exerciseTypes));
    }

    public void getById(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        ExerciseType exerciseType = exerciseTypeDao.read(id);
        context.status(200);
        context.json(new ExerciseTypeDTO(exerciseType));
    }

    public void create(Context context) throws ApiException
    {
        ExerciseTypeDTO exerciseTypeDTO = validate(context);
        ExerciseType exerciseType = new ExerciseType(exerciseTypeDTO);
        exerciseTypeDao.create(exerciseType);
        context.status(201);
        context.json(new ExerciseTypeDTO(exerciseType));
    }

    public void update(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!exerciseTypeDao.exists(id)) throw new ApiException(404, "Exercise type not found with id " + id);

        ExerciseTypeDTO exerciseTypeDTO = validate(context);
        ExerciseType exerciseType = new ExerciseType(exerciseTypeDTO);
        exerciseType.setId(id);
        exerciseType = exerciseTypeDao.update(exerciseType);
        context.status(200);
        context.json(new ExerciseTypeDTO(exerciseType));
    }

    private ExerciseTypeDTO validate(Context context)
    {
        return context.bodyValidator(ExerciseTypeDTO.class)
                .check((exerciseTypeDTO) -> exerciseTypeDTO.getTypeName() != null && !exerciseTypeDTO.getTypeName().isEmpty(), "Type name cannot be empty or longer than 50 characters")
                .check((exerciseTypeDTO) -> exerciseTypeDTO.getTypeName().length() <= 50, "Type name cannot be empty or longer than 50 characters")
                .check((exerciseTypeDTO) -> exerciseTypeDao.getByName(exerciseTypeDTO.getTypeName()) == null, "Type name already exists")
                .get();
    }
}
