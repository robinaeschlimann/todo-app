package ch.hftm.todo.controller.eventhandlers;

import ch.hftm.todo.controller.TodoController;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.TodoService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class EditEventHandler implements EventHandler<ActionEvent>
{
    private int todoId;

    public EditEventHandler(int todoId )
    {
        this.todoId = todoId;
    }

    @Override
    public void handle(ActionEvent event) {
        TodoData todoData = TodoService.getInstance().getTodo( todoId );

        try
        {
            TodoController.openTodoFormStage( "Todo editieren", todoData );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

    }
}
