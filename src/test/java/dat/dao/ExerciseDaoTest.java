package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Equipment;
import dat.entities.Exercise;
import dat.entities.Muscle;
import dat.entities.MuscleGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseDaoTest
{
    static MuscleGroup mg1 = new MuscleGroup("Chest", null, "Chest muscles");
    static MuscleGroup mg2 = new MuscleGroup("Back", null, "Back muscles");
    static MuscleGroup mg3 = new MuscleGroup("Legs", null, "Leg muscles");
    static MuscleGroup mg4 = new MuscleGroup("Arms", null, "Arm muscles");
    static MuscleGroup mg5 = new MuscleGroup("Shoulders", null, "Shoulder muscles");
    static MuscleGroup mg6 = new MuscleGroup("Abdomen", null, "Abdominal muscles");

    @BeforeAll
    static void setUp()
    {
        HibernateConfig.setTest(true);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(mg1);
            em.persist(mg2);
            em.persist(mg3);
            em.persist(mg4);
            em.persist(mg5);
            em.persist(mg6);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void read()
    {

        DAO<Exercise> dao = ExerciseDao.getInstance();
        dao.create(new Exercise("Pull-up", "Pull yourself up on a bar", null, 5));
        Exercise exercise = dao.read(1);

        assertEquals("Pull-up", exercise.getName());
    }

    @Test
    void readAll()
    {
        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(new Exercise("Pull-up", "Pull yourself up on a bar", null, 5));
        dao.create(new Exercise("Bar-curl", "Curl a bar in front of you", null, 5));

        assertEquals(2, dao.readAll().size());
    }

    @Test
    void update()
    {
        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(new Exercise("Pull-up", "Pull yourself up on a bar", null, 5));
        Exercise exercise = dao.read(1);
        exercise.setDescription("Pull-up with a twist");
        dao.update(exercise);

        assertEquals("Pull-up with a twist", dao.read(1).getDescription());
    }

    @Test
    void createExercise()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);

        DAO<Exercise> dao = ExerciseDao.getInstance();

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

        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(exercise);
        dao.create(exercise2);
    }

    @Test
    void createExerciseWithMuscles()
    {
        Exercise exercise = new Exercise("Squat", "Squat down below the knees", null, 5);

        exercise.addMuscle(new Muscle("Quadriceps", null, "Front of the thigh", mg3));
        exercise.addMuscle(new Muscle("Hamstrings", null, "Back of the thigh", mg3));

        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(exercise);
    }

    @Test
    void createExerciseWithEquipment()
    {
        Exercise exercise = new Exercise("Deadlift", "The dealift exercise", null, 5);

        exercise.addEquipment(new Equipment("Barbell", null, "Barbell"));
        exercise.addEquipment(new Equipment("Weight plates", null, "Weight plates"));

        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(exercise);
    }
}