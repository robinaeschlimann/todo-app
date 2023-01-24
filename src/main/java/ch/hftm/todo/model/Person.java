package ch.hftm.todo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person
{
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty salutation;
    private StringProperty email;

    public Person( PersonData data )
    {
        ESalutation salutation = ESalutation.getById( data.getSalutation() );

        this.firstname = new SimpleStringProperty( data.getFirstname() );
        this.lastname = new SimpleStringProperty( data.getLastname() );
        this.salutation = new SimpleStringProperty( salutation != null ? salutation.getText() : ESalutation.MR.getText() );
        this.email = new SimpleStringProperty( data.getEmail() );
    }

    public StringProperty getSalutation()
    {
        return salutation;
    }

    public StringProperty getFirstname()
    {
        return firstname;
    }

    public StringProperty getLastname()
    {
        return lastname;
    }

    public StringProperty getEmail()
    {
        return email;
    }
}
