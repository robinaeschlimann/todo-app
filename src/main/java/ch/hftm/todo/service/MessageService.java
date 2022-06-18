package ch.hftm.todo.service;

import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.events.IListener;

import java.util.*;
import java.util.stream.Collectors;

public class MessageService
{
    private static final MessageService INSTANCE = new MessageService();

    /**
     * Map<Event, Map<Listener, Rang>>
     *
     *  Der tiefste Rang wird bei einem publish eines Events zuerst aufgerufen.
     */
    private Map<Class<? extends IEvent>, Map<IListener, Integer>> events;

    public static MessageService getInstance()
    {
        return INSTANCE;
    }

    private MessageService()
    {
        events = new HashMap<>();
    }

    public void registerListener( Class<? extends IEvent> event, IListener listener, int rank )
    {
        if( !events.containsKey( event ) )
        {
            events.put( event, new HashMap<>() );
        }

        events.get( event ).put( listener, rank );
    }

    public void publishMessage( IEvent event )
    {
        Map<IListener, Integer> listeners = events.get( event.getClass() );

        if( listeners != null )
        {
            // Sortieren der Listeners nach Rang aufsteigend.
            Map<IListener, Integer> sortedListeners = listeners.entrySet().stream()
                    .sorted( Map.Entry.comparingByValue() )
                    .collect(Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new ) );

            for( IListener listener : sortedListeners.keySet() )
            {
                listener.onMessage( event );
            }
        }
    }
}
