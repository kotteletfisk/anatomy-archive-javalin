package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Equipment;
import dat.entities.Exercise;
import dat.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.SneakyThrows;
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
        Equipment equipment = null;
        try
        {
            equipment = dao.read(1);
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }

        assertEquals("Barbell", equipment.getName());
    }


    @Test
    void readAll()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        try
        {
            assertEquals(3, dao.readAll().size());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void readByName()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        Equipment equipment = null;
        try
        {
            equipment = dao.readByName("Barbell");
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }

        assertEquals("Barbell", equipment.getName());
    }

    @Test
    void create()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        try
        {
            dao.create(new Equipment("Bench", null, "Bench description"));
            assertEquals(4, dao.readAll().size());
        }
        catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void update()
    {
        DAO<Equipment> dao = EquipmentDao.getInstance();
        try
        {
            Equipment equipment = dao.read(1);
            equipment.setName("Barbell 2");
            dao.update(equipment);
            assertEquals("Barbell 2", dao.read(1).getName());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void dontDuplicateOnPersist()
    {
        Exercise exercise = new Exercise("Pull-up", "Pull yourself up on a bar", null, 5);
        exercise.addEquipment(new Equipment("Pull-up bar", null, "Pull-up bar description"));

        DAO<Exercise> dao = ExerciseDao.getInstance();
        try
        {
            dao.create(exercise);
            assertEquals(1, dao.readAll().size());
            assertEquals(3, EquipmentDao.getInstance().readAll().size());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }
}