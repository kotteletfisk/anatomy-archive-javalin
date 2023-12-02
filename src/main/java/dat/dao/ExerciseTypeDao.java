package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.ExerciseType;
import dat.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ExerciseTypeDao implements DAO<ExerciseType>
{
    private static ExerciseTypeDao instance;
    private static EntityManagerFactory emf;
    private ExerciseTypeDao()
    {
        emf = HibernateConfig.getEntityManagerFactory();
    }

    public static ExerciseTypeDao getInstance()
    {
        if (instance == null)
        {
            instance = new ExerciseTypeDao();
        }
        return instance;
    }

    @Override
    public ExerciseType read(int id)
    {
        return null;
    }

    @Override
    public List<ExerciseType> readAll()
    {
        return null;
    }

    @Override
    public ExerciseType readByName(String exerciseType)
    {
        exerciseType = exerciseType.toUpperCase();
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM ExerciseType e WHERE e.typeName = :exerciseType", ExerciseType.class)
                    .setParameter("exerciseType", exerciseType)
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public ExerciseType update(ExerciseType exerciseType)
    {
        return null;
    }

    @Override
    public ExerciseType create(ExerciseType exerciseType)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(exerciseType);
            em.getTransaction().commit();
            return exerciseType;
        }
    }

    @Override
    public boolean exists(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(e) FROM ExerciseType e WHERE e.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult() == 1;
        }
    }

    @Override
    public ExerciseType delete(int id) throws ApiException
    {
        return null;
    }
}
