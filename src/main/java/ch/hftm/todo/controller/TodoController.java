package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.comparators.TodoComparator;
import ch.hftm.todo.model.Todo;
import ch.hftm.todo.model.TodoJson;
import ch.hftm.todo.service.TodoService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TodoController implements Initializable
{

    @FXML
    TableView<Todo> todoList;

    @FXML
    TableColumn<Todo, String> nameColumn;

    @FXML
    TableColumn<Todo, String> descriptionColumn;

    @FXML
    TableColumn<Todo, String> deadlineColumn;

    @FXML
    TableColumn<Todo, String> personColumn;

    @FXML
    TableColumn<Todo, String> doneColumn;

    @FXML
    TableColumn<Todo, String> editColumn;

    @FXML
    TableColumn<Todo, String> deleteColumn;

    protected void showToDos()
    {
        nameColumn.setCellValueFactory( cellData -> cellData.getValue().getNameProperty() );
        descriptionColumn.setCellValueFactory( cellData -> cellData.getValue().getDescriptionProperty() );
        deadlineColumn.setCellValueFactory( cellData -> cellData.getValue().getDeadlineProperty() );
        personColumn.setCellValueFactory( cellData -> cellData.getValue().getPersonProperty() );
        doneColumn.setCellValueFactory( new PropertyValueFactory<>( "doneCheckBox" ) );
        editColumn.setCellValueFactory( new PropertyValueFactory<>("editButton") );
        deleteColumn.setCellValueFactory( new PropertyValueFactory<>( "deleteButton" ) );

        List<TodoJson> todoJsons = TodoService.getInstance().getTodos();

        List<Todo> todos = todoJsons.stream()
                .sorted( new TodoComparator() )
                .map(todoJson -> new Todo(todoJson.getId(), todoJson.getName(), todoJson.getDescription(),
                        todoJson.getDeadline(), todoJson.getPerson(), todoJson.isDone()))
                .collect(Collectors.toList());

        ObservableList<Todo> observableList = FXCollections.observableList( todos );

        todoList.setItems(observableList);

    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void myInfo(ActionEvent actionEvent)
    {
        Label label = new Label("Version: 1.0");

        // create a popup
        Popup popup = new Popup();

        // set background
        label.setStyle(" -fx-background-color: white;");

        // add the label
        popup.getContent().add(label);

        // set size of label
        label.setMinWidth(80);
        label.setMinHeight(50);

        if ( !popup.isShowing() )
        {
            popup.show(TodoApp.getDefaultStage() );
        }
    }

    public void createTodo(ActionEvent actionEvent) {
        showToDos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showToDos();
    }

    public void test(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getTarget());
    }
}