package ch.hftm.todo.service;

import ch.hftm.todo.caches.TodoCache;
import ch.hftm.todo.model.TodoJson;
import ch.hftm.todo.stores.TodoStore;

import java.util.List;

public class TodoService
{
    private static final TodoService INSTANCE = new TodoService();

    public static TodoService getInstance()
    {
        return INSTANCE;
    }

    private TodoService(){}

    public List<TodoJson> getTodos()
    {
        return TodoCache.getInstance().getTodos();
    }

    public TodoJson getTodo( int id )
    {
        return TodoCache.getInstance().getTodo( id );
    }

    public void saveTodo( TodoJson todo )
    {
        TodoStore.getInstance().saveTodo( todo );
        //TodoCache.getInstance().updateCache( todo );
    }
}
