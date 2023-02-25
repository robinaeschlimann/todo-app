package ch.hftm.todo.controller.eventhandlers;

import ch.hftm.todo.controller.ControllerUtil;
import ch.hftm.todo.model.IData;
import ch.hftm.todo.service.IDataService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public record EditEventHandler<T extends IData>(int dataId, String title, String resource, Stage stage,
                                                IDataService<T> service)
        implements EventHandler<ActionEvent>
{
    @Override
    public void handle(ActionEvent event) {
        T data = service.get(dataId);

        try
        {
            ControllerUtil.showView( title, resource, stage, data );
        }
        catch ( IOException e )
        {
            log.error( "Error while opening form", e );
        }

    }
}
