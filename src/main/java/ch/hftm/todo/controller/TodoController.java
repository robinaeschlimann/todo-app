package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.comparators.TodoComparator;
import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.events.IListener;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.Todo;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.model.ETodoGroup;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.TodoService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class TodoController implements Initializable, IListener
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

    @FXML
    ComboBox<ETodoGroup> groupFilterCombobox;

    protected void showToDos()
    {
        nameColumn.setCellValueFactory( cellData -> cellData.getValue().getNameProperty() );
        descriptionColumn.setCellValueFactory( cellData -> cellData.getValue().getDescriptionProperty() );
        deadlineColumn.setCellValueFactory( cellData -> cellData.getValue().getDeadlineProperty() );
        personColumn.setCellValueFactory( cellData -> cellData.getValue().getPersonProperty() );
        doneColumn.setCellValueFactory( new PropertyValueFactory<>( "doneCheckBox" ) );
        editColumn.setCellValueFactory( new PropertyValueFactory<>("editButton") );
        deleteColumn.setCellValueFactory( new PropertyValueFactory<>( "deleteButton" ) );

        List<TodoData> todoJsons = TodoService.getInstance().getTodos();

        List<Todo> todos = todoJsons.stream()
                .sorted( new TodoComparator() )
                .map(todoJson -> new Todo(todoJson.getId(), todoJson.getName(), todoJson.getDescription(),
                        todoJson.getDeadline(), todoJson.getPerson(), todoJson.isDone()))
                .collect(Collectors.toList());

        ObservableList<Todo> observableList = FXCollections.observableList( todos );

        todoList.setItems(observableList);

    }

    protected void loadFilter() {
        ControllerUtil.loadGroups( groupFilterCombobox, ETodoGroup.ALL );
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void showInfo(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("ToDo-App");
        alert.setContentText("Autor: Robin Aeschlimann\nVersion 1.0");

        alert.showAndWait();
    }

    public void createTodo(ActionEvent actionEvent) throws IOException {
        openTodoFormStage( "Todo erfassen", null );
    }

    public static void openTodoFormStage( String title, TodoData todoData ) throws IOException
    {
        // Stage mit UserData muss vor dem laden des fxml aufgerufen werden, da die Daten im initialize
        // ausgelesen werden müssen
        Stage stage = TodoApp.getTodoFormStage();
        stage.setTitle( title );
        stage.setUserData( todoData );

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource("view/todo-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MessageService.getInstance().registerListener( TodoChangedEvent.class, this, 1 );

        showToDos();
        loadFilter();
    }

    @Override
    public void onMessage( IEvent event )
    {
        if( event instanceof TodoChangedEvent )
        {
            showToDos();
        }
    }
}