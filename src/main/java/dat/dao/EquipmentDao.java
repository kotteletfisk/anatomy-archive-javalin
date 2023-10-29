package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.Equipment;
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
        return null;
    }

    @Override
    public List<Equipment> readAll()
    {
        return null;
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
        return null;
    }

    @Override
    public Equipment create(Equipment equipment)
    {
        return null;
    }
}
