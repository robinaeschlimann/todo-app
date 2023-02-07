package ch.hftm.todo.events;

import ch.hftm.todo.model.IData;
import ch.hftm.todo.model.PersonData;

public record PersonChangedEvent<T extends IData>(T personData, EChangeType type) implements IEvent
{
}
