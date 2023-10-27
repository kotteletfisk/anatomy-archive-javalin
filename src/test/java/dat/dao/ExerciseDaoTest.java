package dat.dao;

import dat.entities.Exercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseDaoTest
{

    @Test
    void read()
    {
    }

    @Test
    void readAll()
    {
    }

    @Test
    void update()
    {
    }

    @Test
    void create()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5,
                Exercise.ExerciseType.CALISTHENIC, true);

        DAO<Exercise> dao = ExerciseDao.getInstance(true);

        dao.create(exercise);
    }
}