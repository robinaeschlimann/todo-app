package ch.hftm.todo;

import ch.hftm.todo.service.TodoService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoApp extends Application {

    private static Stage defaultStage;
    private static Stage todoFormStage;
    private static Stage personStage;
    private static Stage personFormStage;

    @Override
    public void start(Stage stage) throws IOException {
        defaultStage = stage;
        todoFormStage = new Stage();
        personStage = new Stage();
        personFormStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource("view/todo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("ToDo-App!");
        stage.setScene(scene);
        stage.show();

        TodoService.getInstance().getTodos();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getDefaultStage() {
        return defaultStage;
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
}