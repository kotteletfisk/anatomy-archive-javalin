package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Equipment;
import dat.entities.Exercise;
import dat.entities.ExerciseType;
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
            em.getTransaction().commit();
        }
    }

    public void addMuscleToExercise(int exerciseId, int[] muscleIdInts)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Exercise exercise = em.find(Exercise.class, exerciseId);
            for (int muscleId : muscleIdInts)
            {
                Muscle muscle = em.find(Muscle.class, muscleId);
                exercise.addMuscle(muscle);
            }
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

    public void addTypeToExercise(int exerciseId, int typeId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Exercise exercise = em.find(Exercise.class, exerciseId);
            ExerciseType exerciseType = em.find(ExerciseType.class, typeId);
            exercise.addExerciseType(exerciseType);
            em.getTransaction().commit();
        }
    }

    public List<Exercise> getExercisesByType(int typeId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e JOIN e.exerciseTypes t WHERE t.id = :typeId", Exercise.class)
                    .setParameter("typeId", typeId)
                    .getResultList();
        }
    }

    public List<ExerciseType> getTypesByExercise(int exerciseId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT t FROM Exercise e JOIN e.exerciseTypes t WHERE e.id = :exerciseId", ExerciseType.class)
                    .setParameter("exerciseId", exerciseId)
                    .getResultStream()
                    .collect(java.util.stream.Collectors.toList());
        }
    }

    public void addEquipmentToExercise(int exerciseId, int equipmentId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Exercise exercise = em.find(Exercise.class, exerciseId);
            Equipment equipment = em.find(Equipment.class, equipmentId);
            exercise.addEquipment(equipment);
            em.getTransaction().commit();
        }
    }

    public List<Equipment> getEquipmentByExercise(int exerciseId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Equipment e JOIN e.exerciseHasEquipmentRelation ehe WHERE ehe.exercise.id = :exerciseId", Equipment.class)
                    .setParameter("exerciseId", exerciseId)
                    .getResultList();
        }
    }

    public List<Exercise> getByMusclePattern(String musclePattern)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e JOIN e.exerciseHasMusclesRelation m WHERE m.muscle.name ILIKE :musclePattern", Exercise.class)
                    .setParameter("musclePattern", "%" + musclePattern + "%")
                    .getResultList();
        }
    }

    public List<Exercise> getByEquipmentPattern(String equipmentPattern)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e JOIN e.exerciseHasEquipmentRelation ehe WHERE ehe.equipment.name ILIKE :equipmentPattern", Exercise.class)
                    .setParameter("equipmentPattern", "%" + equipmentPattern + "%")
                    .getResultList();
        }
    }

    public List<Exercise> getByNamePattern(String namePattern)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Exercise e WHERE e.name ILIKE :namePattern", Exercise.class)
                    .setParameter("namePattern", "%" + namePattern + "%")
                    .getResultList();
        }
    }
}
