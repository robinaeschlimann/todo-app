package ch.hftm.todo.service;

import ch.hftm.todo.caches.TodoCache;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.exception.DeleteException;
import ch.hftm.todo.stores.TodoStore;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TodoService implements IDataService<TodoData>
{
    private static final TodoService INSTANCE = new TodoService();

    public static TodoService getInstance()
    {
        return INSTANCE;
    }

    private TodoService(){}

    @Override
    public List<TodoData> getAll()
    {
        return TodoCache.getInstance().getTodos();
    }

    @Override
    public TodoData get(int id )
    {
        return TodoCache.getInstance().getTodo( id );
    }

    @Override
    public void save( TodoData todo )
    {
        TodoStore.getInstance().saveTodo( todo );
    }

    public int getNextFreeId()
    {
        return TodoCache.getInstance().getNextFreeId();
    }

    @Override
    public void delete(int todoId ) throws DeleteException
    {
        if( !TodoStore.getInstance().deleteTodo( todoId ) )
        {
            throw new DeleteException( "Fehler beim Löschen des ToDo-Eintrags" );
        }

    }
}
