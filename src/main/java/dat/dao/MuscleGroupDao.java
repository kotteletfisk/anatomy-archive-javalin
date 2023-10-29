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
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM MuscleGroup m", MuscleGroup.class)
                    .getResultList();
        }
    }

    @Override
    public MuscleGroup readByName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM MuscleGroup m WHERE m.name = :name", MuscleGroup.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    @Override
    public MuscleGroup update(MuscleGroup muscleGroup)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(muscleGroup);
            em.getTransaction().commit();
            return muscleGroup;
        }
    }

    @Override
    public MuscleGroup create(MuscleGroup muscleGroup)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(muscleGroup);
            em.getTransaction().commit();
            return muscleGroup;
        }
    }
}
