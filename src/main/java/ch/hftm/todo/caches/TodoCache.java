package ch.hftm.todo.caches;

import ch.hftm.todo.model.TodoJson;
import ch.hftm.todo.stores.TodoStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TodoCache
{
    private static final TodoCache INSTANCE = new TodoCache();

    private Map<Integer, TodoJson> todoCache;

    public static TodoCache getInstance() {
        return INSTANCE;
    }

    private TodoCache()
    {
    }

    public List<TodoJson> getTodos()
    {
        if( todoCache == null )
        {
            loadTodos();
        }

        return new ArrayList<>( todoCache.values() );
    }

    public TodoJson getTodo( int id )
    {
        if( todoCache == null )
        {
            loadTodos();
        }

        return todoCache.get( id );
    }

    private void loadTodos()
    {
        todoCache = TodoStore.getInstance().getTodos().stream()
                .collect( Collectors.toMap( TodoJson::getId, Function.identity() ) );
    }

    public void updateCache( TodoJson todo )
    {
        if( todoCache != null )
        {
            todoCache.put( todo.getId(), todo );
        }
    }
}
