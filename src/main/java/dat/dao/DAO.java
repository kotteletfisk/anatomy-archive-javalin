package dat.dao;

import java.util.List;

public interface DAO<T>
{
    public T read(int id);

    public List<T> readAll();

    public T readByName(String name);

    public T update(T t);

    public T create(T t);
}
