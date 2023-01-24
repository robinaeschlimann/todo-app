package ch.hftm.todo.stores;

import ch.hftm.todo.model.PersonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonStore
{
    private static final PersonStore INSTANCE = new PersonStore();

    public static PersonStore getInstance()
    {
        return INSTANCE;
    }

    private PersonStore() {}

    /**
     * Loads all Persons from the persons-folder and deserializes all files
     *
     * @return List of all persons in the persons-folder
     */
    public List<PersonData> getPersons()
    {
        List<PersonData> personDatas = new ArrayList<>();

        File dir = new File("data/persons/");

        if( dir.isDirectory() && dir.listFiles() != null )
        {
            for( File file : dir.listFiles() )
            {
                try
                {
                    if ( file.isFile() )
                    {
                        String personJson = Files.readString( Path.of( file.getPath() ) );
                        PersonData personData = new ObjectMapper().readValue( personJson, PersonData.class );

                        personDatas.add( personData );
                    }
                }
                catch ( IOException e )
                {
                    log.error( "Can't load persons", e );
                }
            }
        }

        return personDatas;
    }

    public void savePerson( PersonData data )
    {
        try
        {
            File personFile = new File( "data/persons/person" + data.getId() + ".json" );

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue( personFile, data );
        }
        catch ( IOException e )
        {
            log.error( "Error while saving todo", e );
        }
    }

    public boolean deletePerson( int id )
    {
        File personFile = new File( "data/persons/person" + id + ".json" );

        if( personFile.exists() )
        {
            return personFile.delete();
        }

        return false;
    }
}
