package ch.hftm.todo.service;

import ch.hftm.todo.service.exception.DeleteException;

import java.util.List;

public interface IDataService<T>
{
    List<T> getAll();

    T get( int id );

    void save( T data );

    void delete(int id ) throws DeleteException;
}
