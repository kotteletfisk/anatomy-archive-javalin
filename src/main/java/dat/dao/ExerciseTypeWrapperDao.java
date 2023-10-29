package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.ExerciseTypeWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ExerciseTypeWrapperDao implements DAO<ExerciseTypeWrapper>
{
    private static ExerciseTypeWrapperDao instance;
    private static EntityManagerFactory emf;
    private ExerciseTypeWrapperDao()
    {
        emf = HibernateConfig.getEntityManagerFactory();
    }

    public static ExerciseTypeWrapperDao getInstance()
    {
        if (instance == null)
        {
            instance = new ExerciseTypeWrapperDao();
        }
        return instance;
    }

    @Override
    public ExerciseTypeWrapper read(int id)
    {
        return null;
    }

    @Override
    public List<ExerciseTypeWrapper> readAll()
    {
        return null;
    }

    @Override
    public ExerciseTypeWrapper readByName(String exerciseType)
    {
        exerciseType = exerciseType.toUpperCase();
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM ExerciseTypeWrapper e WHERE e.exerciseType = :exerciseType", ExerciseTypeWrapper.class)
                    .setParameter("exerciseType", exerciseType)
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public ExerciseTypeWrapper update(ExerciseTypeWrapper exerciseTypeWrapper)
    {
        return null;
    }

    @Override
    public ExerciseTypeWrapper create(ExerciseTypeWrapper exerciseTypeWrapper)
    {
        return null;
    }
}
