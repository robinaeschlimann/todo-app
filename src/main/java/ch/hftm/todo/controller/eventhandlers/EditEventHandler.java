package ch.hftm.todo.controller.eventhandlers;

import ch.hftm.todo.controller.TodoController;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.TodoService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public record EditEventHandler(int todoId) implements EventHandler<ActionEvent>
{
    @Override
    public void handle(ActionEvent event) {
        TodoData todoData = TodoService.getInstance().get(todoId);

        try
        {
            TodoController.openTodoFormStage("Todo editieren", todoData);
        }
        catch ( IOException e )
        {
            log.error( "Error while opening todo form" );
        }

    }
}
