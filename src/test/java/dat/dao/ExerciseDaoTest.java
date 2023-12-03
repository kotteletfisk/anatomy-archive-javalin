package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.*;
import dat.exception.ApiException;
import dat.util.Populate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseDaoTest
{
    @BeforeEach
    void setUp()
    {
        HibernateConfig.setTest(true);
        Populate.main(null);
    }

    @Test
    void read()
    {

        DAO<Exercise> dao = ExerciseDao.getInstance();
        try
        {
            dao.create(new Exercise("Pull-up", "Pull yourself up on a bar", null, 5));
            Exercise exercise = dao.read(7);
            assertEquals("Pull-up", exercise.getName());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }

    }

    @Test
    void readAll()
    {
        DAO<Exercise> dao = ExerciseDao.getInstance();

        try
        {
            dao.create(new Exercise("Pull-up", "Pull yourself up on a bar", null, 5));
            dao.create(new Exercise("Bar-curl", "Curl a bar in front of you", null, 5));
            assertEquals(8, dao.readAll().size());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void update()
    {
        DAO<Exercise> dao = ExerciseDao.getInstance();

        try
        {
            dao.create(new Exercise("Pull-up", "Pull yourself up on a bar", null, 5));
            Exercise exercise = dao.read(6);
            exercise.setDescription("Pull-up with a twist");
            dao.update(exercise);

            assertEquals("Pull-up with a twist", dao.read(6).getDescription());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void createExercise()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);

        DAO<Exercise> dao = ExerciseDao.getInstance();

        try
        {
            dao.create(exercise);
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void addMuscleToExercise()
    {
        ExerciseDao exerciseDao = ExerciseDao.getInstance();

        exerciseDao.addMuscleToExercise(1,1);
        List<Exercise> exercises = exerciseDao.getExercisesByMuscle(1);
        assertEquals(1, exercises.size());
    }

    @Test
    void addTypeToExercise()
    {
        ExerciseDao exerciseDao = ExerciseDao.getInstance();

        exerciseDao.addTypeToExercise(1,1);
        List<Exercise> exercises = exerciseDao.getExercisesByType(1);
        assertEquals(1, exercises.size());
    }
}