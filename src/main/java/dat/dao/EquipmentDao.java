package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Equipment;
import dat.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class EquipmentDao implements DAO<Equipment>
{
    private static EquipmentDao instance;

    private EntityManagerFactory emf;

    private EquipmentDao()
    {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    public static EquipmentDao getInstance()
    {
        if (instance == null)
        {
            instance = new EquipmentDao();
        }
        return instance;
    }

    @Override
    public Equipment read(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Equipment.class, id);
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public List<Equipment> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Equipment e", Equipment.class)
                    .getResultList();
        }
    }

    @Override
    public Equipment readByName(String name)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT e FROM Equipment e WHERE e.name = :name", Equipment.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public Equipment update(Equipment equipment)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Equipment updatedEquipment = em.merge(equipment);
            em.getTransaction().commit();
            return updatedEquipment;
        }
    }

    @Override
    public Equipment create(Equipment equipment)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(equipment);
            em.getTransaction().commit();
            return equipment;
        }
    }

    @Override
    public boolean exists(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(e) FROM Equipment e WHERE e.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult() == 1;
        }
    }

    @Override
    public Equipment delete(int id) throws ApiException
    {
        return null;
    }
}
