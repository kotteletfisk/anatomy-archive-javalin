package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseDaoTest
{

   /* @BeforeAll
    static void setUp()
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory(true);

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
*/

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
    void createExercise()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);

        DAO<Exercise> dao = ExerciseDao.getInstance(true);

        dao.create(exercise);
    }

    @Test
    void createExerciseWithTypes()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);
        exercise.addExerciseType(Exercise.ExerciseType.CALISTHENIC);
        exercise.addExerciseType(Exercise.ExerciseType.DYNAMIC);

        Exercise exercise2 = new Exercise("Bar-curl", "Curl a bar in front of you", null, 5);
        exercise2.addExerciseType(Exercise.ExerciseType.WEIGHTLIFTING);
        exercise2.addExerciseType(Exercise.ExerciseType.DYNAMIC);

        DAO<Exercise> dao = ExerciseDao.getInstance(true);

        dao.create(exercise);
        dao.create(exercise2);
    }
}