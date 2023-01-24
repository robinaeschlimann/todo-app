package ch.hftm.todo.caches;

import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.stores.PersonStore;
import ch.hftm.todo.stores.TodoStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonCache
{
    private static final PersonCache INSTANCE = new PersonCache();

    private Map<Integer, PersonData> personCache;

    public static PersonCache getInstance() {
        return INSTANCE;
    }

    private PersonCache()
    {
        // Muss als aller erstes aufgerufen werden. Deshalb wird dieser Listener mit der tiefst möglichen Zahl registriert.
//        MessageService.getInstance().registerListener( TodoChangedEvent.class, this, -Integer.MAX_VALUE+1 );
    }

    public List<PersonData> getPersons()
    {
        if( personCache == null )
        {
            loadTodos();
        }

        return new ArrayList<>( personCache.values() );
    }

    private void loadTodos()
    {
        personCache = PersonStore.getInstance().getPersons().stream()
                .collect( Collectors.toMap( PersonData::getId, Function.identity() ) );
    }
}
