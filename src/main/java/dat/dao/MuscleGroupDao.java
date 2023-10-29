package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.MuscleGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MuscleGroupDao implements DAO<MuscleGroup>
{
    private static MuscleGroupDao instance;

    private final EntityManagerFactory emf;

    private MuscleGroupDao()
    {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    public static MuscleGroupDao getInstance()
    {
        if (instance == null)
        {
            instance = new MuscleGroupDao();
        }
        return instance;
    }

    @Override
    public MuscleGroup read(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(MuscleGroup.class, id);
        }
    }

    @Override
    public List<MuscleGroup> readAll()
    {
        return null;
    }

    @Override
    public MuscleGroup readByName(String name)
    {
        return null;
    }

    @Override
    public MuscleGroup update(MuscleGroup muscleGroup)
    {
        return null;
    }

    @Override
    public MuscleGroup create(MuscleGroup muscleGroup)
    {
        return null;
    }
}
