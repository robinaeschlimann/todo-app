package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.events.EChangeType;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.TodoService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TodoFormController implements Initializable
{
    @FXML
    TextField todoField;

    @FXML
    DatePicker deadlineField;

    @FXML
    TextField personField;

    @FXML
    TextArea descriptionField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void resetAndExit()
    {
        todoField.setText(null);
        deadlineField.getEditor().setText(null);
        personField.setText(null);
        descriptionField.setText(null);

        TodoApp.getTodoFormStage().close();
    }

    public void saveTodo()
    {
        TodoData todoData = new TodoData();
        todoData.setId( TodoService.getInstance().getNextFreeId() );
        todoData.setName( todoField.getText() );
        todoData.setDeadline( deadlineField.getEditor().getText() );
        todoData.setPerson( personField.getText() );
        todoData.setDescription( descriptionField.getText() );
        todoData.setDone( false );
        todoData.setGroup( 1 );

        TodoService.getInstance().saveTodo( todoData );

        MessageService.getInstance().publishMessage( new TodoChangedEvent( todoData, EChangeType.CREATE ) );

        resetAndExit();
    }
}
