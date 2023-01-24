package ch.hftm.todo.model;

import ch.hftm.todo.controller.eventhandlers.DeleteEventHandler;
import ch.hftm.todo.controller.eventhandlers.EditEventHandler;
import ch.hftm.todo.controller.listeners.DoneChangedListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Todo
{
    private StringProperty name;
    private StringProperty description;
    private StringProperty deadline;
    private StringProperty person;
    private CheckBox doneCheckBox;
    private Button editButton;
    private Button deleteButton;

    public Todo(TodoData todoData) {
        int id = todoData.getId();
        this.name = new SimpleStringProperty( todoData.getName() );
        this.description = new SimpleStringProperty( todoData.getDescription() );
        this.deadline = new SimpleStringProperty( todoData.getDeadline() );
        PersonData person = todoData.getPersonData();
        this.person = new SimpleStringProperty(person.toString());

        doneCheckBox = new CheckBox();
        doneCheckBox.setSelected( todoData.isDone() );

        doneCheckBox.selectedProperty().addListener( new DoneChangedListener( id ) );

        editButton = new Button( "Bearbeiten" );
        editButton.setOnAction( new EditEventHandler( id ));

        deleteButton = new Button( "Löschen" );
        deleteButton.setOnAction( new DeleteEventHandler( id ));
    }

    public StringProperty getNameProperty()
    {
        return name;
    }

    public StringProperty getDescriptionProperty()
    {
        return description;
    }

    public StringProperty getDeadlineProperty()
    {
        return deadline;
    }

    public StringProperty getPersonProperty() {
        return person;
    }

    public CheckBox getDoneCheckBox() {
        return doneCheckBox;
    }

    public void setDoneCheckBox(CheckBox doneCheckBox) {
        this.doneCheckBox = doneCheckBox;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
