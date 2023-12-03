package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Exercise;
import dat.entities.Muscle;
import dat.exception.ApiException;
import dat.util.Populate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MuscleDaoTest
{
    MuscleDao muscleDao = MuscleDao.getInstance();
    ExerciseDao exerciseDao = ExerciseDao.getInstance();


    @BeforeEach
    void setUp()
    {
        HibernateConfig.setTest(false);
        Populate.main(null);
    }
    @Test
    void getMusclesByExercise() // TODO: fix this
    {
        exerciseDao.addMuscleToExercise(2, 1);
        List<Muscle> found = null;
        try
        {
            found = muscleDao.getMusclesByExercise(2);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        assertEquals(1, found.size());
        System.out.println(found);
    }
}