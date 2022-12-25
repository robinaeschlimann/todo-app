package ch.hftm.todo.controller;

import ch.hftm.todo.model.PersonData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonController implements Initializable
{
    @FXML
    TableView<PersonData> personTable;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
