package dat.controller;

import dat.dao.MuscleGroupDao;
import dat.dto.MuscleGroupDTO;
import dat.entities.MuscleGroup;
import io.javalin.http.Context;

import java.util.List;

public class MuscleGroupController
{
    MuscleGroupDao muscleGroupDao = MuscleGroupDao.getInstance();

    public void getByNamePattern(Context context)
    {
        String pattern = context.queryParam("pattern");
        List<MuscleGroup> muscleGroups = muscleGroupDao.readLikeNamePattern(pattern);
        context.status(200);
        context.json(MuscleGroupDTO.toMuscleGroupDTOList(muscleGroups));
    }
}
