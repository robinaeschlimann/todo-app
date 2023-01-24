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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

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

        PersonData personData = PersonData.builder()
                .id(PersonService.getInstance().getNextFreeId())
                .salutation(salutation)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .permission(permission)
                .build();

        PersonService.getInstance().save( personData );

        MessageService.getInstance().publishMessage(new PersonChangedEvent(personData, EChangeType.CREATE));

        resetAndExit();
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
}
