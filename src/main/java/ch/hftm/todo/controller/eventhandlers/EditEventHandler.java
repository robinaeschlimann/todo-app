package ch.hftm.todo.controller.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EditEventHandler implements EventHandler<ActionEvent>
{
    private int todoId;

    public EditEventHandler(int todoId )
    {
        this.todoId = todoId;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println(todoId);

    }
}
