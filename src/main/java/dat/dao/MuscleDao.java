package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Muscle;
import dat.exception.ApiException;
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

    @Override
    public boolean exists(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(m) FROM Muscle m WHERE m.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult() == 1;
        }
    }

    @Override
    public Muscle delete(int id) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Muscle muscle = em.find(Muscle.class, id);
            em.remove(muscle);
            em.getTransaction().commit();
            return muscle;
        }
    }

    public List<Muscle> getMusclesByExercise(int exerciseId) throws Exception
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m JOIN m.exerciseHasMusclesRelation e WHERE e.exercise.id = :exerciseId", Muscle.class)
                    .setParameter("exerciseId", exerciseId)
                    .getResultList();
        }
    }

    public List<Muscle> getLikeName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m WHERE m.name LIKE :name", Muscle.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }

    public List<Muscle> getLikeNamePattern(String pattern)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m WHERE m.name ILIKE :pattern", Muscle.class)
                    .setParameter("pattern", "%" + pattern + "%")
                    .getResultList();
        }
    }

    public List<Muscle> readLikeEquipmentPattern(String pattern)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m " +
                            "JOIN m.exerciseHasMusclesRelation em " +
                            "JOIN em.exercise e " +
                            "JOIN e.exerciseHasEquipmentRelation ehe WHERE ehe.equipment.name ILIKE :pattern", Muscle.class)
                    .setParameter("pattern", "%" + pattern + "%")
                    .getResultList();
        }
    }

    public List<Muscle> getLikeExercisePattern(String pattern)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Muscle m " +
                            "JOIN m.exerciseHasMusclesRelation em " +
                            "WHERE em.exercise.name ILIKE :pattern", Muscle.class)
                    .setParameter("pattern", "%" + pattern + "%")
                    .getResultList();
        }
    }
}
