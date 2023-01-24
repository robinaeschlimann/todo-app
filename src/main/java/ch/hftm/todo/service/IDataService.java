package ch.hftm.todo.service;

import java.util.List;

public interface IDataService<T>
{
    List<T> getAll();

    T get( int id );

    void save( T data );

    boolean delete( int id );
}
