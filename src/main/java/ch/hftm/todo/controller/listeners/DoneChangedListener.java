package ch.hftm.todo.controller.listeners;

import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.TodoService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DoneChangedListener implements ChangeListener<Boolean>
{
    private int todoId;

    public DoneChangedListener(int todoId )
    {
        this.todoId = todoId;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue)
    {
        System.out.println( "TodoId " + todoId + " set to done=" + newValue );

        TodoData todo = TodoService.getInstance().getTodo( todoId );
        todo.setDone( newValue );

        TodoService.getInstance().saveTodo( todo );
    }
}
