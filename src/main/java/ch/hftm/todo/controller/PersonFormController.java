package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.events.EChangeType;
import ch.hftm.todo.events.PersonChangedEvent;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.EPermission;
import ch.hftm.todo.model.ESalutation;
import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.PersonService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonFormController implements Initializable
{
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<ESalutation> salutationCombobox;

    @FXML
    private ComboBox<EPermission> permissionCombobox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSalutationCombobox();

        initializePermissionCombobox();

        Object userData = TodoApp.getPersonFormStage().getUserData();

        if( userData instanceof PersonData personData )
        {
            firstnameField.setText( personData.getFirstname() );
            lastnameField.setText( personData.getLastname() );
            emailField.setText( personData.getEmail() );
            passwordField.setText( personData.getPassword() );
            salutationCombobox.setValue( ESalutation.getById( personData.getSalutation() ) );
            permissionCombobox.setValue( EPermission.getById( personData.getPermission() ) );
        }
    }

    private void initializePermissionCombobox() {
        permissionCombobox.setItems( FXCollections.observableArrayList( EPermission.values() ) );
        permissionCombobox.setConverter(new StringConverter<EPermission>()
        {
            @Override
            public String toString(EPermission ePermission) {
                return ePermission.getText();
            }

            @Override
            public EPermission fromString(String s) {
                return null;
            }
        });

        permissionCombobox.setValue(EPermission.USER);
    }

    private void initializeSalutationCombobox() {
        salutationCombobox.setItems(FXCollections.observableArrayList( ESalutation.values() ));
        salutationCombobox.setConverter(new StringConverter<ESalutation>()
        {
            @Override
            public String toString(ESalutation eSalutation) {
                return eSalutation.getText();
            }

            @Override
            public ESalutation fromString(String s) {
                return null;
            }
        });
        salutationCombobox.setValue( ESalutation.MR );
    }

    public void savePerson( ActionEvent event )
    {
        var salutation = salutationCombobox.getValue().getId();
        var firstname = firstnameField.getText();
        var lastname = lastnameField.getText();
        var email = emailField.getText();
        var password = passwordField.getText();
        var permission = permissionCombobox.getValue().getId();

        if( isInputValid() )
        {
            EChangeType type = EChangeType.CREATE;

            Object userData = TodoApp.getPersonFormStage().getUserData();
            PersonData personData = null;

            if( userData instanceof PersonData )
            {
                type = EChangeType.UPDATE;
                personData = (PersonData) userData;
            }
            else
            {
                personData = new PersonData();
                personData.setId( PersonService.getInstance().getNextFreeId() );
            }

            personData.setSalutation(salutation);
            personData.setFirstname(firstname);
            personData.setLastname(lastname);
            personData.setEmail(email);
            personData.setPassword(password);
            personData.setPermission(permission);

            PersonService.getInstance().save(personData);

            MessageService.getInstance().publishMessage(new PersonChangedEvent<>(personData, type));

            resetAndExit();
        }
    }

    public void resetAndExit()
    {
        salutationCombobox.setValue(ESalutation.MR);
        firstnameField.setText(null);
        lastnameField.setText(null);
        emailField.setText(null);
        passwordField.setText(null);
        permissionCombobox.setValue(EPermission.USER);

        TodoApp.getPersonFormStage().close();
    }

    public boolean isInputValid()
    {
        boolean isValid = false;

        String errorMessage = "";

        String firstname = firstnameField.getText();

        if( firstname == null || firstname.isEmpty() )
        {
            errorMessage += "Vorname ist ein Pflichtfeld\n";
        }

        String lastname = lastnameField.getText();

        if( lastname == null || lastname.isEmpty() )
        {
            errorMessage += "Nachname ist ein Pflichtfeld\n";
        }

        String email = emailField.getText();

        if( email == null || email.isEmpty() )
        {
            errorMessage += "Email ist ein Pflichtfeld\n";
        }
        else
        {
            // Pattern for email
            Pattern pattern = Pattern.compile( "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+" +
                    "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$" );
            Matcher matcher = pattern.matcher( email );

            if( !matcher.matches() )
            {
                errorMessage += "Das Format des Emails stimmt nicht\n";
            }
            else if( doesEmailExist( email ) )
            {
                errorMessage += "Diese Email existiert bereits\n";
            }
        }

        String password = passwordField.getText();

        if( password == null || password.isEmpty() )
        {
            errorMessage += "Passwort ist ein Pflichtfeld\n";
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

    private boolean doesEmailExist(String email)
    {
        return PersonService.getInstance().getPerson( email ) != null;
    }
}
