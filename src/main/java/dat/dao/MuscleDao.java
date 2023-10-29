package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Muscle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

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
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Muscle.class, id);
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public List<Muscle> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m", Muscle.class)
                    .getResultList();
        }
    }

    @Override
    public Muscle readByName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m WHERE m.name = :name", Muscle.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public Muscle update(Muscle muscle)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Muscle updatedMuscle = em.merge(muscle);
            em.getTransaction().commit();
            return updatedMuscle;
        }
    }

    @Override
    public Muscle create(Muscle muscle)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(muscle);
            em.getTransaction().commit();
            return muscle;
        }
    }
}
