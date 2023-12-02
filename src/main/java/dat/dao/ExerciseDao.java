package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Exercise;
import dat.entities.Muscle;
import dat.exception.ApiException;
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

    public List<Exercise> getExercisesByMuscle(int muscleId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e JOIN e.exerciseHasMusclesRelation m WHERE m.id = :muscleId", Exercise.class)
                    .setParameter("muscleId", muscleId)
                    .getResultList();
        }
    }

    public void addMuscleToExercise(int exerciseId, int muscleId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Exercise exercise = em.find(Exercise.class, exerciseId);
            Muscle muscle = em.find(Muscle.class, muscleId);
            exercise.addMuscle(muscle);
            em.merge(exercise);
            em.getTransaction().commit();
        }
    }

    public boolean exists(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(e) FROM Exercise e WHERE e.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult() == 1;
        }
    }

    @Override
    public Exercise delete(int id) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Exercise exercise = em.find(Exercise.class, id);
            em.remove(exercise);
            em.getTransaction().commit();
            return exercise;
        }
    }
}
