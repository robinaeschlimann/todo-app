module ch.hftm.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires org.slf4j;

    opens ch.hftm.todo to javafx.fxml;
    exports ch.hftm.todo;
    exports ch.hftm.todo.controller;
    exports ch.hftm.todo.model;
    opens ch.hftm.todo.controller to javafx.fxml;
    exports ch.hftm.todo.controller.eventhandlers;
    opens ch.hftm.todo.controller.eventhandlers to javafx.fxml;
}