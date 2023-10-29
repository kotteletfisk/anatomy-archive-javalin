package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import java.util.List;



public class ExerciseDao implements DAO<Exercise>
{
    private EntityManagerFactory emf;
    private static ExerciseDao instance;

    private ExerciseDao()
    {
        emf = HibernateConfig.getEntityManagerFactory();
    }

    public static ExerciseDao getInstance()
    {
        if (instance == null)
        {
            instance = new ExerciseDao();
        }
        return instance;
    }


    @Override
    public Exercise read(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Exercise.class, id);
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public List<Exercise> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e", Exercise.class)
                    .getResultList();
        }
    }

    @Override
    public Exercise readByName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e WHERE e.name = :name", Exercise.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public Exercise update(Exercise exercise)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(exercise);
            em.getTransaction().commit();
            return exercise;
        }
    }

    @Override
    public Exercise create(Exercise exercise)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(exercise);
            em.getTransaction().commit();
            return exercise;
        }
    }
}
