package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.events.EChangeType;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.model.ETodoGroup;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.PersonService;
import ch.hftm.todo.service.TodoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class TodoFormController implements Initializable
{
    @FXML
    TextField todoField;

    @FXML
    DatePicker deadlineField;

    @FXML
    ComboBox<PersonData> personCombobox;

    @FXML
    TextArea descriptionField;

    @FXML
    ComboBox<ETodoGroup> groupCombobox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ControllerUtil.loadGroups( groupCombobox, ETodoGroup.PRIVATE, ETodoGroup.ALL );

        loadPersons();

        // Load selected user
        Object userData = TodoApp.getTodoFormStage().getUserData();

        if( userData instanceof TodoData todoData )
        {
            todoField.setText( todoData.getName() );
            deadlineField.getEditor().setText( todoData.getDeadline() );
            personCombobox.setValue( todoData.getPersonData() );
            descriptionField.setText( todoData.getDescription() );
            groupCombobox.setValue( ETodoGroup.getById( todoData.getGroup() ) );
        }
    }

    private void loadPersons() {
        List<PersonData> persons = PersonService.getInstance().getAll();

        personCombobox.setItems(FXCollections.observableArrayList(persons));
        personCombobox.setConverter(new StringConverter<>()
        {
            @Override
            public String toString(PersonData personData) {

                return personData != null ? personData.toString() : null;
            }

            @Override
            public PersonData fromString(String s) {
                return null;
            }
        });

        personCombobox.setValue( persons.get(0) );
    }

    private void resetAndExit()
    {
        todoField.setText(null);
        deadlineField.getEditor().setText(null);
        personCombobox.setValue(null);
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
            todoData.setPerson(personCombobox.getValue().getId());
            todoData.setDescription(descriptionField.getText());
            todoData.setGroup(groupCombobox.getValue().getId());

            TodoService.getInstance().save(todoData);

            MessageService.getInstance().publishMessage(new TodoChangedEvent(todoData, changeType));

            resetAndExit();
        }
    }

    private boolean isInputValid()
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
