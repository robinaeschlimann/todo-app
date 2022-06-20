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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Object userData = TodoApp.getTodoFormStage().getUserData();

        if( userData instanceof TodoData )
        {
            TodoData todoData = (TodoData) userData;

            todoField.setText( todoData.getName() );
            deadlineField.getEditor().setText( todoData.getDeadline() );
            personField.setText( todoData.getPerson() );
            descriptionField.setText( todoData.getDescription() );
        }
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
        if( isInputValid() )
        {
            EChangeType changeType = EChangeType.CREATE;

            Object userData = TodoApp.getTodoFormStage().getUserData();
            TodoData todoData = null;

            if ( userData instanceof TodoData ) // Editieren eines ToDos
            {
                changeType = EChangeType.UPDATE;
                todoData = (TodoData) userData;
            }
            else // Erstellen eines ToDos
            {
                todoData = new TodoData();
                todoData.setId(TodoService.getInstance().getNextFreeId());
                todoData.setDone(false);
            }

            todoData.setName(todoField.getText());
            todoData.setDeadline(deadlineField.getEditor().getText());
            todoData.setPerson(personField.getText());
            todoData.setDescription(descriptionField.getText());
            todoData.setGroup(1);

            TodoService.getInstance().saveTodo(todoData);

            MessageService.getInstance().publishMessage(new TodoChangedEvent(todoData, changeType));

            resetAndExit();
        }
    }

    public boolean isInputValid()
    {
        boolean isValid = false;

        String errorMessage = "";

        String todoFieldText = todoField.getText();

        if( todoFieldText == null || todoFieldText.isEmpty() )
        {
            errorMessage += "Bezeichnung ist ein Pflichtfeld\n";
        }

        String deadline = deadlineField.getEditor().getText();

        if( deadline == null || deadline.isEmpty() )
        {
            errorMessage += "Deadline ist ein Pflichtfeld\n";
        }
        else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat( "dd.MM.yyyy" );

            try
            {
                dateFormat.parse( deadline );
            } catch ( ParseException e )
            {
                errorMessage += "Das Datum muss im Format dd.MM.yyyy angegeben werden\n";
            }
        }

        String personFieldText = personField.getText();

        if( personFieldText == null || personFieldText.isEmpty() )
        {
            errorMessage += "Zuständige Person ist ein Pflichtfeld\n";
        }

        if( errorMessage.isEmpty() )
        {
            isValid = true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Felder Valdierung");
            alert.setHeaderText("Bitte korrigiere die ungültigen Felder.");
            alert.setContentText(errorMessage);
            alert.showAndWait();

        }

        return isValid;
    }

}
