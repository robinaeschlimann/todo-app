package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.model.Todo;
import ch.hftm.todo.service.PersonService;
import ch.hftm.todo.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(ActionEvent event) throws IOException
    {
        String email = emailField.getText();
        String password = passwordField.getText();

        var personData = PersonService.getInstance().getPerson( emailField.getText() );

        if( personData == null )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login fehlgeschlagen");
            alert.setHeaderText("Login fehlgeschlagen");
            alert.setContentText("Die Login-Daten sind falsch");
            alert.showAndWait();

            return;
        }

        UserService.getInstance().setLoggedInUser( personData );

        Stage stage = TodoApp.getTodoStage();
        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource("view/todo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("ToDo-App!");
        stage.setScene(scene);
        stage.show();

        TodoApp.getLoginStage().close();
    }
}
