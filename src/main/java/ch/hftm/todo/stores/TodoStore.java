package ch.hftm.todo.stores;

import ch.hftm.todo.model.TodoData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TodoStore
{
    private static TodoStore INSTANCE = new TodoStore();

    public static TodoStore getInstance() {
        return INSTANCE;
    }

    private TodoStore()
    {}

    /**
     * Loads all todos from the todos-folder and deserializes all files
     *
     * @return List of all todos in the todos-folder
     */
    public List<TodoData> getTodos()
    {
        List<TodoData> todoJsons = new ArrayList<>();

        File dir = new File("data/todos/");

        if( dir.isDirectory() && dir.listFiles() != null )
        {
            for( File file : dir.listFiles() )
            {
                try
                {
                    if ( file.isFile() )
                    {
                        String todoJson = Files.readString( Path.of( file.getPath() ) );
                        TodoData todo = new ObjectMapper().readValue( todoJson, TodoData.class );

                        todoJsons.add( todo );
                    }
                }
                catch ( IOException e )
                {
                    log.error( "Can't get todos from JSON", e );
                }
            }
        }

        return todoJsons;
    }

    public void saveTodo( TodoData todo )
    {
        try
        {
            File todoFile = new File( "data/todos/todo" + todo.getId() + ".json" );

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue( todoFile, todo );
        }
        catch ( IOException e )
        {
            log.error( "Error while saving todo", e );
        }
    }

    public boolean deleteTodo( int todoId )
    {
        File todoFile = new File( "data/todos/todo" + todoId + ".json" );

        if( todoFile.exists() )
        {
            return todoFile.delete();
        }

        return false;
    }
}
