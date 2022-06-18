package ch.hftm.todo.controller.eventhandlers;

import ch.hftm.todo.events.EChangeType;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.TodoService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DeleteEventHandler implements EventHandler<ActionEvent>
{
    private int todoId;

    public DeleteEventHandler(int todoId )
    {
        this.todoId = todoId;
    }

    @Override
    public void handle(ActionEvent event)
    {
        TodoData todoData = TodoService.getInstance().getTodo( todoId );

        TodoService.getInstance().deleteTodo( todoId );

        MessageService.getInstance().publishMessage( new TodoChangedEvent( todoData, EChangeType.DELETE ) );
    }
}
