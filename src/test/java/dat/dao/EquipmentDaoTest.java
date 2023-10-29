package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Equipment;
import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentDaoTest
{

    static EntityManagerFactory emf;
    @BeforeAll
    static void setUp()
    {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactory();
    }

    @BeforeEach
    void setUpEach()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM exercisehasequipment").executeUpdate();
            em.createQuery("DELETE FROM Equipment").executeUpdate();
            em.createQuery("DELETE FROM Exercise").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE equipment_equipment_id_seq RESTART WITH 1").executeUpdate();
            em.persist(new Equipment("Barbell", null, "Barbell description"));
            em.persist(new Equipment("Dumbbell", null, "Dumbbell description"));
            em.persist(new Equipment("Pull-up bar", null, "Pull-up bar description"));
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
        DAO<Equipment> dao = EquipmentDao.getInstance();
        Equipment equipment = dao.read(1);

        assertEquals("Barbell", equipment.getName());
    }

    @Test
    void readAll()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        assertEquals(3, dao.readAll().size());
    }

    @Test
    void readByName()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        Equipment equipment = dao.readByName("Barbell");

        assertEquals("Barbell", equipment.getName());
    }

    @Test
    void create()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        dao.create(new Equipment("Bench", null, "Bench description"));

        assertEquals(4, dao.readAll().size());
    }

    @Test
    void update()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        Equipment equipment = dao.read(1);
        equipment.setName("Barbell 2");
        dao.update(equipment);

        assertEquals("Barbell 2", dao.read(1).getName());
    }

    @Test
    void dontDuplicateOnPersist()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);
        exercise.addEquipment(new Equipment("Pull-up bar", null, "Pull-up bar description"));

        DAO<Exercise> dao = ExerciseDao.getInstance();
        dao.create(exercise);

        assertEquals(3, EquipmentDao.getInstance().readAll().size());
    }
}