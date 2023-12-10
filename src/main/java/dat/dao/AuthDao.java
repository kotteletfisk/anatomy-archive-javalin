package dat.dao;

import dat.config.HibernateConfig;
import dat.entities.User;
import dat.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AuthDao
{
    EntityManagerFactory emf;
    private static AuthDao instance;

    private AuthDao()
    {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }


    public static AuthDao getInstance()
    {
        if (instance == null)
        {
            instance = new AuthDao();
        }
        return instance;
    }

    public User read(int id) throws ApiException
    {
        return null;
    }

    public List<User> readAll() throws ApiException
    {
        return null;
    }

    public User readByName(String name) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public User update(User user) throws ApiException
    {
        return null;
    }

    public User create(User user) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    public boolean exists(String username) throws ApiException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            User user = em.find(User.class, username);
            return user != null;
        }
    }

    public User delete(int id) throws ApiException
    {
        return null;
    }
}
