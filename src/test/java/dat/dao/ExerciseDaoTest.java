package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        // TODO: Fix Setup, truncate tables, reset sequences
        HibernateConfig.setTest(true);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery("ALTER SEQUENCE equipment_equipment_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE exercise_exercise_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE muscle_muscle_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE musclegroup_muscle_group_id_seq RESTART WITH 1").executeUpdate();
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
        ExerciseType cali = new ExerciseType("CALISTHENIC");
        ExerciseType dyna = new ExerciseType("DYNAMIC");
        DAO<ExerciseType> exerciseTypeDao = ExerciseTypeWrapperDao.getInstance();
        exerciseTypeDao.create(cali);
        exerciseTypeDao.create(dyna);

        exercise.addExerciseType(cali);
        exercise.addExerciseType(dyna);

        /*Exercise exercise2 = new Exercise("Bar-curl", "Curl a bar in front of you", null, 5);
        exercise2.addExerciseType(new ExerciseType(ExerciseType.ExerciseTypeEnum.WEIGHTLIFTING));
        exercise2.addExerciseType(new ExerciseType(ExerciseType.ExerciseTypeEnum.DYNAMIC));*/

        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(exercise);
        // dao.create(exercise2);
    }

    @Test
    void createExerciseWithMuscles()
    {
        Exercise exercise = new Exercise("Squat", "Squat down below the knees", null, 5);

        Muscle muscle = new Muscle("Glutes", null, "Buttocks", mg3);
        Muscle muscle1 = new Muscle("Hamstrings", null, "Back of the thigh", mg3);

        DAO<Muscle> muscleDao = MuscleDao.getInstance();
        muscleDao.create(muscle);
        muscleDao.create(muscle1);

        exercise.addMuscle(muscle);
        exercise.addMuscle(muscle1);

        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(exercise);
    }

    @Test
    void createExerciseWithEquipment()
    {
        Exercise exercise = new Exercise("Deadlift", "The dealift exercise", null, 5);
        Equipment equipment = new Equipment("Barbell", null, "Barbell description");
        Equipment equipment2 = new Equipment("Weight plates", null, "Weight plates description");

        DAO<Equipment> equipmentDao = EquipmentDao.getInstance();
        equipmentDao.create(equipment);
        equipmentDao.create(equipment2);

        exercise.addEquipment(equipment);
        exercise.addEquipment(equipment2);

        DAO<Exercise> dao = ExerciseDao.getInstance();

        dao.create(exercise);
    }

    @Test
    void addMuscleToExercise()
    {
        Exercise exercise = new Exercise("Deadlift", "The dealift exercise", null, 5);
        Muscle muscle = new Muscle("Glutes", null, "Buttocks", mg3);

        DAO<Muscle> muscleDao = MuscleDao.getInstance();
        ExerciseDao dao = ExerciseDao.getInstance();

        muscleDao.create(muscle);
        dao.create(exercise);
        ExerciseHasMuscles ehm = exercise.addMuscle(muscle);
        dao.update(exercise);


        List<Exercise> exercises = dao.getExercisesByMuscle(1);
        assertEquals(1, exercises.size());
    }
}