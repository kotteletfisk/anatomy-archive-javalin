package dat.dao;

import dat.exception.ApiException;

import java.util.List;

public interface DAO<T>
{
    public T read(int id) throws ApiException;

    public List<T> readAll() throws ApiException;

    public T readByName(String name) throws ApiException;

    public T update(T t) throws ApiException;

    public T create(T t) throws ApiException;

    boolean exists(int id) throws ApiException;

    T delete(int id) throws ApiException;
}
