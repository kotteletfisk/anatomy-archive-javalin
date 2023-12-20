package dat.controller;

import dat.dao.MuscleDao;
import dat.dto.MuscleDTO;
import dat.entities.Muscle;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class MuscleController
{
    MuscleDao muscleDao = MuscleDao.getInstance();

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
        List<Muscle> muscles = muscleDao.readLikeName(name);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }

    public void getMuscleByNamePattern(Context context) throws ApiException
    {
        String pattern = context.queryParam("pattern");
        List<Muscle> muscles = muscleDao.readLikeNamePattern(pattern);
        List<MuscleDTO> dtos = MuscleDTO.toMuscleDTOList(muscles);
        context.status(200);
        context.json(dtos);
    }
}
