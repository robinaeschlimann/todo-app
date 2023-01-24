package ch.hftm.todo.events;

import ch.hftm.todo.model.PersonData;

public class PersonChangedEvent implements IEvent
{
    private PersonData personData;
    private EChangeType type;

    public PersonChangedEvent( PersonData personData, EChangeType type )
    {
        this.personData = personData;
        this.type = type;
    }

    public PersonData getPersonData()
    {
        return personData;
    }

    public EChangeType getType()
    {
        return type;
    }
}
