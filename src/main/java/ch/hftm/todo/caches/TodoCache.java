package ch.hftm.todo.caches;

import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.events.IListener;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.stores.TodoStore;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class TodoCache implements IListener
{
    private static final TodoCache INSTANCE = new TodoCache();

    private Map<Integer, TodoData> todoCache;

    public static TodoCache getInstance() {
        return INSTANCE;
    }

    private TodoCache()
    {
        // Muss als aller erstes aufgerufen werden. Deshalb wird dieser Listener mit der tiefst möglichen Zahl registriert.
        MessageService.getInstance().registerListener( TodoChangedEvent.class, this, -Integer.MAX_VALUE+1 );
    }

    public List<TodoData> getTodos()
    {
        if( todoCache == null )
        {
            loadTodos();
        }

        return new ArrayList<>( todoCache.values() );
    }

    public TodoData getTodo(int id )
    {
        if( todoCache == null )
        {
            loadTodos();
        }

        return todoCache.get( id );
    }

    public int getNextFreeId()
    {
        int nextFreeId = 1;

        if( !todoCache.isEmpty() )
        {
            List<Integer> keys = todoCache.keySet().stream()
                    .sorted()
                    .collect(Collectors.toList());

            nextFreeId = keys.get( keys.size() - 1 ) + 1;
        }

        return nextFreeId;
    }

    private void loadTodos()
    {
        todoCache = TodoStore.getInstance().getTodos().stream()
                .collect( Collectors.toMap( TodoData::getId, Function.identity() ) );
    }

    public void updateCache( TodoData todo )
    {
        if( todoCache != null )
        {
            todoCache.put( todo.getId(), todo );
        }
    }


    @Override
    public void onMessage(IEvent event)
    {
        if( event instanceof TodoChangedEvent todoChangedEvent )
        {
            switch ( todoChangedEvent.getType() )
            {
                case CREATE, UPDATE -> updateCache( todoChangedEvent.getTodoData() );
                case DELETE -> todoCache.remove( todoChangedEvent.getTodoData().getId() );
                default -> log.warn( "EventType {} doesn't exist", todoChangedEvent.getType() );
            }
        }
    }
}
