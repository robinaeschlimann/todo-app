package ch.hftm.todo.controller;

import ch.hftm.todo.model.ETodoGroup;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerUtil
{
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
}
