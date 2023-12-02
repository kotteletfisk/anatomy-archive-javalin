package dat.util;

import dat.config.HibernateConfig;
import dat.dao.DAO;
import dat.dao.EquipmentDao;
import dat.dao.ExerciseDao;
import dat.dao.MuscleGroupDao;
import dat.entities.Equipment;
import dat.entities.Exercise;
import dat.entities.MuscleGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate
{
    public static void main(String[] args)
    {
        // Populate the database with data
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        DAO<MuscleGroup> muscleGroupDAO = MuscleGroupDao.getInstance();
        DAO<Exercise> exerciseDAO = ExerciseDao.getInstance();
        DAO<Equipment> equipmentDAO = EquipmentDao.getInstance();

        MuscleGroup mg1 = new MuscleGroup("Chest", null, "Chest muscles");
        MuscleGroup mg2 = new MuscleGroup("Back", null, "Back muscles");
        MuscleGroup mg3 = new MuscleGroup("Legs", null, "Leg muscles");
        MuscleGroup mg4 = new MuscleGroup("Arms", null, "Arm muscles");
        MuscleGroup mg5 = new MuscleGroup("Shoulders", null, "Shoulder muscles");
        MuscleGroup mg6 = new MuscleGroup("Abdomen", null, "Abdominal muscles");

        Exercise exercise = new Exercise("Squat", "Squat down below the knees", null, 5);
        Exercise exercise2 = new Exercise("Deadlift", "The dealift exercise", null, 5);
        Exercise exercise3 = new Exercise("Bench press", "The bench press exercise", null, 5);
        Exercise exercise4 = new Exercise("Overhead press", "The overhead press exercise", null, 5);
        Exercise exercise5 = new Exercise("Barbell row", "The barbell row exercise", null, 5);
        Exercise exercise6 = new Exercise("Pull up", "The pull up exercise", null, 5);

        Equipment equipment = new Equipment("Barbell", null, "Barbell description");
        Equipment equipment2 = new Equipment("Dumbells", null, "Dumbells description");
        Equipment equipment3 = new Equipment("Pull-up bar", null, "Pull-up bar description");

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM exercisehasequipment").executeUpdate();
            em.createQuery("DELETE FROM Equipment").executeUpdate();
            em.createQuery("DELETE FROM Exercise").executeUpdate();
            em.createQuery("DELETE FROM MuscleGroup").executeUpdate();
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

            em.persist(exercise);
            em.persist(exercise2);
            em.persist(exercise3);
            em.persist(exercise4);
            em.persist(exercise5);
            em.persist(exercise6);

            em.persist(equipment);
            em.persist(equipment2);
            em.persist(equipment3);

            em.getTransaction().commit();
        }
    }

}