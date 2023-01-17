package ch.hftm.todo.controller.eventhandlers;

import ch.hftm.todo.events.EChangeType;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.TodoService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public record DeleteEventHandler(int todoId) implements EventHandler<ActionEvent>
{
    @Override
    public void handle(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ToDo löschen");
        alert.setHeaderText("ToDo löschen");
        alert.setContentText("Möchtest du dieses ToDo wirklich löschen?");

        Optional<ButtonType> result = alert.showAndWait();

        if ( result.get() == ButtonType.OK )
        {
            TodoData todoData = TodoService.getInstance().getTodo(todoId);

            TodoService.getInstance().deleteTodo(todoId);

            MessageService.getInstance().publishMessage(new TodoChangedEvent(todoData, EChangeType.DELETE));
        }
    }
}
