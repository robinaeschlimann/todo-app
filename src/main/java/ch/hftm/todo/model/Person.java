package ch.hftm.todo.model;

import ch.hftm.todo.controller.eventhandlers.DeleteEventHandler;
import ch.hftm.todo.events.PersonChangedEvent;
import ch.hftm.todo.service.PersonService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person
{
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty salutation;
    private StringProperty email;
    private Button editButton;
    private Button deleteButton;

    public Person( PersonData data )
    {
        ESalutation salutation = ESalutation.getById( data.getSalutation() );

        this.firstname = new SimpleStringProperty( data.getFirstname() );
        this.lastname = new SimpleStringProperty( data.getLastname() );
        this.salutation = new SimpleStringProperty( salutation != null ? salutation.getText() : ESalutation.MR.getText() );
        this.email = new SimpleStringProperty( data.getEmail() );

        this.editButton = new Button( "Bearbeiten" );
        this.editButton.setOnAction( event -> {

        });

        this.deleteButton = new Button( "Löschen" );
        this.deleteButton.setOnAction(new DeleteEventHandler<>(data.getId(), PersonService.getInstance(), PersonChangedEvent.class) );
    }
}
