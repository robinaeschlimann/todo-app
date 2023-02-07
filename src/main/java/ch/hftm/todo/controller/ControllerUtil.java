package ch.hftm.todo.controller;

import ch.hftm.todo.TodoApp;
import ch.hftm.todo.model.ETodoGroup;
import ch.hftm.todo.model.IData;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerUtil
{
    public static final String RESOURCE_TODO_FORM = "view/todo-form.fxml";
    public static final String RESOURCE_PERSON_FORM = "view/person-form.fxml";

    public static void loadGroups(ComboBox<ETodoGroup> groupComboBox, ETodoGroup defaultValue, ETodoGroup... elementsToExclude)
    {
        List<ETodoGroup> todoGroupsToExclude = Arrays.asList( elementsToExclude );

        groupComboBox.setItems(FXCollections.observableArrayList(
                Arrays.stream( ETodoGroup.values() )
                        .filter( group -> !todoGroupsToExclude.contains( group ) )
                        .collect(Collectors.toList())
                        .toArray(new ETodoGroup[0])));

        groupComboBox.setConverter(new StringConverter<>()
        {
            @Override
            public String toString(ETodoGroup todoGroup) {
                return todoGroup.getTitle();
            }

            @Override
            public ETodoGroup fromString(String s) {
                return null;
            }
        });


        groupComboBox.setValue( defaultValue );
    }

    public static void showView( String title, String resource, Stage stage, IData data ) throws IOException
    {
        // Stage mit UserData muss vor dem laden des fxml aufgerufen werden, da die Daten im initialize
        // ausgelesen werden müssen
        stage.setTitle( title );
        stage.setUserData( data );

        FXMLLoader fxmlLoader = new FXMLLoader(TodoApp.class.getResource(resource ));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setScene(scene);
        stage.show();
    }
}
