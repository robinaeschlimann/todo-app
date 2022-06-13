package ch.hftm.todo.comparators;

import ch.hftm.todo.model.TodoJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TodoComparator implements Comparator<TodoJson>
{
    @Override
    public int compare(TodoJson todo1, TodoJson todo2)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy" );

        try
        {
            Date dateTodo1 = simpleDateFormat.parse( todo1.getDeadline() );
            Date dateTodo2 = simpleDateFormat.parse( todo2.getDeadline() );

            return dateTodo1.compareTo( dateTodo2 );
        } catch ( ParseException e )
        {
            e.printStackTrace();
        }

        return 0;
    }
}
