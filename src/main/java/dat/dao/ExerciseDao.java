package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Exercise;
import dat.entities.Muscle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javassist.NotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

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
        return null;
    }

    @Override
    public List<Exercise> readAll()
    {
        return null;
    }

    @Override
    public Exercise readByName(String name)
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
