package ch.hftm.todo;

import ch.hftm.todo.service.TodoService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoApp extends Application {

    private static Stage todoStage;
    private static Stage todoFormStage;
    private static Stage personStage;
    private static Stage personFormStage;
    private static Stage loginStage;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        todoStage = new Stage();
        todoFormStage = new Stage();
        personStage = new Stage();
        personFormStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource("view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        TodoService.getInstance().getAll();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getTodoFormStage() {
        return todoFormStage;
    }

    public static Stage getPersonStage()
    {
        return personStage;
    }

    public static Stage getPersonFormStage()
    {
        return personFormStage;
    }

    public static Stage getLoginStage() {
        return loginStage;
    }

    public static Stage getTodoStage() {
        return todoStage;
    }
}