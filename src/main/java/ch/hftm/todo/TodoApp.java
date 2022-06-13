package ch.hftm.todo;

import ch.hftm.todo.service.TodoService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoApp extends Application {

    private static Stage defaultStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.defaultStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource("view/todo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Hello!");
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
}