package ch.hftm.todo.events;

import ch.hftm.todo.model.TodoData;

public class TodoChangedEvent implements IEvent
{
    private TodoData todoData;
    private EChangeType type;

    public TodoChangedEvent( TodoData todoData, EChangeType type )
    {
        this.todoData = todoData;
        this.type = type;
    }

    public TodoData getTodoData()
    {
        return todoData;
    }

    public EChangeType getType()
    {
        return type;
    }
}
