package ch.hftm.todo.comparators;

import ch.hftm.todo.model.TodoData;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

@Slf4j
public class TodoComparator implements Comparator<TodoData>
{
    @Override
    public int compare(TodoData todo1, TodoData todo2)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy" );

        try
        {
            Date dateTodo1 = simpleDateFormat.parse( todo1.getDeadline() );
            Date dateTodo2 = simpleDateFormat.parse( todo2.getDeadline() );

            return dateTodo1.compareTo( dateTodo2 );
        }
        catch ( ParseException e )
        {
            log.error( "Can't parse date", e );
        }

        return 0;
    }
}
