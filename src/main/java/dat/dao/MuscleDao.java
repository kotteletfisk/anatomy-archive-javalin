package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Muscle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MuscleDao implements DAO<Muscle>
{
    private static MuscleDao instance;
    private EntityManagerFactory emf;
    private MuscleDao()
    {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    public static MuscleDao getInstance()
    {
        if (instance == null)
        {
            instance = new MuscleDao();
        }
        return instance;
    }

    @Override
    public Muscle read(int id)
    {
        return null;
    }

    @Override
    public List<Muscle> readAll()
    {
        return null;
    }

    @Override
    public Muscle readByName(String name)
    {
        return null;
    }

    @Override
    public Muscle update(Muscle muscle)
    {
        return null;
    }

    @Override
    public Muscle create(Muscle muscle)
    {

    }
}
