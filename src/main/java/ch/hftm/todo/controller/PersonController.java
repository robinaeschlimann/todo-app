package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.comparators.PersonComparator;
import ch.hftm.todo.comparators.TodoComparator;
import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.events.IListener;
import ch.hftm.todo.events.PersonChangedEvent;
import ch.hftm.todo.model.*;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PersonController implements Initializable, IListener
{
    @FXML
    TableView<Person> personTable;

    @FXML
    TableColumn<Person, String> salutationColumn;

    @FXML
    TableColumn<Person, String> firstnameColumn;

    @FXML
    TableColumn<Person, String> lastnameColumn;

    @FXML
    TableColumn<Person, String> emailColumn;

    @FXML
    TableColumn<Person, String> userEditColumn;

    @FXML
    TableColumn<Person, String> userDeleteColumn;
    @FXML
    TableColumn<Person, String> editColumn;

    @FXML
    TableColumn<Person, String> deleteColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        MessageService.getInstance().registerListener( PersonChangedEvent.class, this, 1 );

        showPersons();
    }

    private void showPersons()
    {
        salutationColumn.setCellValueFactory( cellValue -> cellValue.getValue().getSalutation() );
        firstnameColumn.setCellValueFactory( cellValue -> cellValue.getValue().getFirstname() );
        lastnameColumn.setCellValueFactory( cellValue -> cellValue.getValue().getLastname() );
        emailColumn.setCellValueFactory( cellValue -> cellValue.getValue().getEmail() );
        editColumn.setCellValueFactory( new PropertyValueFactory<>("editButton") );
        deleteColumn.setCellValueFactory( new PropertyValueFactory<>( "deleteButton" ) );

        List<PersonData> personDatas = PersonService.getInstance().getAll();

        List<Person> persons = personDatas.stream()
                .sorted( new PersonComparator() )
                .map( Person::new )
                .toList();

        ObservableList<Person> observableList = FXCollections.observableList( persons );

        personTable.setItems(observableList);
    }

    public void createPerson( ActionEvent actionEvent) throws IOException
    {
        openPersonFormStage( "Person erfassen", null );
    }

    public static void openPersonFormStage( String title, PersonData personData ) throws IOException
    {
        // Stage mit UserData muss vor dem laden des fxml aufgerufen werden, da die Daten im initialize
        // ausgelesen werden müssen
        Stage stage = TodoApp.getPersonFormStage();
        stage.setTitle( title );
        stage.setUserData( personData );

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource(ControllerUtil.RESOURCE_PERSON_FORM));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void onMessage(IEvent event)
    {
        if( event instanceof PersonChangedEvent )
        {
            showPersons();
        }
    }
}
