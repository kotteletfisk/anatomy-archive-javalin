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
    static MuscleGroup mg1 = new MuscleGroup("Chest", null, "Chest muscles");
    static MuscleGroup mg2 = new MuscleGroup("Back", null, "Back muscles");
    static MuscleGroup mg3 = new MuscleGroup("Legs", null, "Leg muscles");
    static MuscleGroup mg4 = new MuscleGroup("Arms", null, "Arm muscles");
    static MuscleGroup mg5 = new MuscleGroup("Shoulders", null, "Shoulder muscles");
    static MuscleGroup mg6 = new MuscleGroup("Abdomen", null, "Abdominal muscles");

    @BeforeEach
    void setUp()
    {
        // TODO: Fix Setup, truncate tables, reset sequences
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
            Exercise exercise = dao.read(6);
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
    void createExerciseWithTypes()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);
        ExerciseType cali = new ExerciseType("CALISTHENIC");
        ExerciseType dyna = new ExerciseType("DYNAMIC");
        DAO<ExerciseType> exerciseTypeDao = ExerciseTypeDao.getInstance();
        try
        {
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
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void createExerciseWithMuscles()
    {
        Exercise exercise = new Exercise("Squat", "Squat down below the knees", null, 5);

        Muscle muscle = new Muscle("Glutes", null, "Buttocks");
        Muscle muscle1 = new Muscle("Hamstrings", null, "Back of the thigh");

        DAO<Muscle> muscleDao = MuscleDao.getInstance();
        try
        {
            muscleDao.create(muscle);
            muscleDao.create(muscle1);

            exercise.addMuscle(muscle);
            exercise.addMuscle(muscle1);

            DAO<Exercise> dao = ExerciseDao.getInstance();

            dao.create(exercise);
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void createExerciseWithEquipment()
    {
        Exercise exercise = new Exercise("Deadlift", "The dealift exercise", null, 5);
        Equipment equipment = new Equipment("Barbell", null, "Barbell description");
        Equipment equipment2 = new Equipment("Weight plates", null, "Weight plates description");

        DAO<Equipment> equipmentDao = EquipmentDao.getInstance();
        try
        {
            equipmentDao.create(equipment);
            equipmentDao.create(equipment2);

            exercise.addEquipment(equipment);
            exercise.addEquipment(equipment2);

            DAO<Exercise> dao = ExerciseDao.getInstance();

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