package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;



public class ExerciseDao implements DAO<Exercise>
{
    private EntityManagerFactory emf;
    private static ExerciseDao instance;

    @Getter(AccessLevel.PUBLIC)
    private static boolean isTest;

    private ExerciseDao(boolean isTest)
    {
        emf = HibernateConfig.getEntityManagerFactory(isTest);
    }

    public static ExerciseDao getInstance(boolean isTest)
    {
        if (instance == null)
        {
            instance = new ExerciseDao(isTest);
        }
        return instance;
    }


    @Override
    public Exercise read(int id)
    {
        return null;
    }

    @Override
    public List<Exercise> readAll()
    {
        return null;
    }

    @Override
    public Exercise update(Exercise exercise)
    {
        return null;
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
