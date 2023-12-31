package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.MuscleGroup;
import dat.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MuscleGroupDaoTest
{

    @BeforeAll
    static void setUp()
    {
        HibernateConfig.setTest(true);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(new MuscleGroup("Chest", null, "Chest muscles"));
            em.persist(new MuscleGroup("Back", null, "Back muscles"));
            em.persist(new MuscleGroup("Legs", null, "Leg muscles"));
            em.persist(new MuscleGroup("Arms", null, "Arm muscles"));
            em.persist(new MuscleGroup("Shoulders", null, "Shoulder muscles"));
            em.persist(new MuscleGroup("Abdomen", null, "Abdominal muscles"));
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
        DAO<MuscleGroup> dao = MuscleGroupDao.getInstance();
        MuscleGroup muscleGroup = null;
        try
        {
            muscleGroup = dao.read(1);
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }

        assertEquals("Chest", muscleGroup.getName());
    }

    @Test
    void readAll()
    {
        DAO<MuscleGroup> dao = MuscleGroupDao.getInstance();
        try
        {
            assertEquals(6, dao.readAll().size());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    void readByName()
    {
        DAO<MuscleGroup> dao = MuscleGroupDao.getInstance();
        MuscleGroup muscleGroup = null;
        try
        {
            muscleGroup = dao.readByName("Chest");
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }

        assertEquals("Chest", muscleGroup.getName());
    }

    @Test
    void update()
    {
        DAO<MuscleGroup> dao = MuscleGroupDao.getInstance();
        MuscleGroup muscleGroup = null;
        try
        {
            muscleGroup = dao.read(1);
            muscleGroup.setDescription("New description");
            dao.update(muscleGroup);
            assertEquals("New description", dao.read(1).getDescription());
        } catch (ApiException e)
        {
            fail(e.getMessage());
        }
    }
}