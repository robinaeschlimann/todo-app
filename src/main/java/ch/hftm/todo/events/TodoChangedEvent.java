package ch.hftm.todo.events;

import ch.hftm.todo.model.IData;
import ch.hftm.todo.model.TodoData;

public record TodoChangedEvent<T extends IData>(T todoData, EChangeType type) implements IEvent
{
}
