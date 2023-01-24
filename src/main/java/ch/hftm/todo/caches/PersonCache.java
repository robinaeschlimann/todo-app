package ch.hftm.todo.caches;

import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.events.IListener;
import ch.hftm.todo.events.PersonChangedEvent;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.stores.PersonStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonCache implements IListener
{
    private static final PersonCache INSTANCE = new PersonCache();

    private Map<Integer, PersonData> personCache;

    public static PersonCache getInstance() {
        return INSTANCE;
    }

    private PersonCache()
    {
        // Muss als aller erstes aufgerufen werden. Deshalb wird dieser Listener mit der tiefst möglichen Zahl registriert.
        MessageService.getInstance().registerListener( PersonChangedEvent.class, this, -Integer.MAX_VALUE+1 );
    }

    public List<PersonData> getPersons()
    {
        if( personCache == null )
        {
            loadPersons();
        }

        return new ArrayList<>( personCache.values() );
    }

    private void loadPersons()
    {
        personCache = PersonStore.getInstance().getPersons().stream()
                .collect( Collectors.toMap( PersonData::getId, Function.identity() ) );
    }

    public int getNextFreeId()
    {
        int nextFreeId = 1;

        if( !personCache.isEmpty() )
        {
            List<Integer> keys = personCache.keySet().stream()
                    .sorted()
                    .toList();

            nextFreeId = keys.get( keys.size() - 1 ) + 1;
        }

        return nextFreeId;
    }

    @Override
    public void onMessage(IEvent event) {
        if( event instanceof PersonChangedEvent changedEvent )
        {
            var data = changedEvent.getPersonData();

            switch ( changedEvent.getType() )
            {
                case CREATE, UPDATE -> personCache.put(data.getId(), data);
                case DELETE -> personCache.remove( data.getId() );
            }
        }
    }
}
