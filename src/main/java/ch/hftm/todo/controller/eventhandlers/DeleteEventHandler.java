package ch.hftm.todo.controller.eventhandlers;

import ch.hftm.todo.events.EChangeType;
import ch.hftm.todo.events.IEvent;
import ch.hftm.todo.model.IData;
import ch.hftm.todo.service.IDataService;
import ch.hftm.todo.service.MessageService;
import ch.hftm.todo.service.exception.DeleteException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Slf4j
public record DeleteEventHandler<T extends IData>(int id, IDataService<T> dataService,
                                                  Class<? extends IEvent> event) implements EventHandler<ActionEvent>
{
    @Override
    public void handle(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Löschen");
        alert.setHeaderText("Löschen");
        alert.setContentText("Möchtest du diesen Eintrag wirklich löschen?");

        Optional<ButtonType> result = alert.showAndWait();

        if ( result.isPresent() && result.get() == ButtonType.OK )
        {
            T data = dataService.get(id);

            try
            {
                dataService.delete(id);

                MessageService.getInstance()
                        .publishMessage(this.event.getDeclaredConstructor(IData.class, EChangeType.class)
                                .newInstance(data, EChangeType.DELETE));
            }
            catch ( InvocationTargetException | InstantiationException | IllegalAccessException |
                    NoSuchMethodException e )
            {
                log.error( "Can't find declaredConstructor. Publishing message failed", e );
            }
            catch ( DeleteException e )
            {
                log.error( "Delete failed", e );

                Alert deleteAlert = new Alert( Alert.AlertType.ERROR );
                deleteAlert.setTitle( "Fehler!" );
                deleteAlert.setHeaderText( "Fehler beim Löschen: " );
                deleteAlert.setContentText( e.getMessage() );
                deleteAlert.showAndWait();
            }
        }
    }
}
