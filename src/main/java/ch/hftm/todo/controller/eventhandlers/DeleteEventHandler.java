package ch.hftm.todo.controller.eventhandlers;

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
    public void handle(ActionEvent event) {


    }
}
