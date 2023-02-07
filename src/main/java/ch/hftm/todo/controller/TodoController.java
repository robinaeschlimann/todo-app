package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.comparators.TodoComparator;
import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.events.IListener;
import ch.hftm.todo.events.TodoChangedEvent;
import ch.hftm.todo.model.EPermission;
import ch.hftm.todo.model.Todo;
import ch.hftm.todo.model.TodoData;
import ch.hftm.todo.model.ETodoGroup;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.TodoService;
import ch.hftm.todo.service.UserService;
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

    @FXML
    Button showPersonsButton;

    protected void showToDos( ETodoGroup group )
    {
        nameColumn.setCellValueFactory( cellData -> cellData.getValue().getNameProperty() );
        descriptionColumn.setCellValueFactory( cellData -> cellData.getValue().getDescriptionProperty() );
        deadlineColumn.setCellValueFactory( cellData -> cellData.getValue().getDeadlineProperty() );
        personColumn.setCellValueFactory( cellData -> cellData.getValue().getPersonProperty() );
        doneColumn.setCellValueFactory( new PropertyValueFactory<>( "doneCheckBox" ) );
        editColumn.setCellValueFactory( new PropertyValueFactory<>("editButton") );
        deleteColumn.setCellValueFactory( new PropertyValueFactory<>( "deleteButton" ) );

        List<TodoData> todoDatas = TodoService.getInstance().getAll();

        List<Todo> todos = todoDatas.stream()
                .filter( todoData -> group == ETodoGroup.ALL || todoData.getGroup() == group.getId() )
                .sorted( new TodoComparator() )
                .map( Todo::new )
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
        ControllerUtil.showView( "Todo erfassen", ControllerUtil.RESOURCE_TODO_FORM, TodoApp.getTodoFormStage(),
                null );
    }

    public void showPersons( ActionEvent actionEvent ) throws IOException
    {
        Stage stage = TodoApp.getTodoFormStage();
        stage.setTitle( "Personen" );

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource("view/person-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);

        stage.setScene(scene);
        stage.show();
    }

    public void onGroupFilterChanged( ActionEvent event )
    {
        showToDos( groupFilterCombobox.getValue() );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MessageService.getInstance().registerListener( TodoChangedEvent.class, this, 1 );

        showToDos( ETodoGroup.ALL );
        loadFilter();

        var user = UserService.getInstance().getLoggedInUser();

        var permission = EPermission.getById( user.getPermission() );

        // disable person-view for non-admins
        if( permission != EPermission.ADMIN )
        {
            showPersonsButton.setVisible(false);
        }
    }

    @Override
    public void onMessage( IEvent event )
    {
        if( event instanceof TodoChangedEvent )
        {
            showToDos( groupFilterCombobox.getValue() );
        }
    }
}