package ch.hftm.todo.service;

import ch.hftm.todo.caches.PersonCache;
import ch.hftm.todo.model.EPermission;
import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.service.exception.DeleteException;
import ch.hftm.todo.stores.PersonStore;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PersonService implements IDataService<PersonData>
{
    private static final PersonService INSTANCE = new PersonService();

    public static PersonService getInstance()
    {
        return INSTANCE;
    }

    private PersonService(){}

    @Override
    public List<PersonData> getAll() {
        return PersonCache.getInstance().getPersons();
    }

    @Override
    public PersonData get(int id) {
        return PersonCache.getInstance().getPerson( id );
    }

    @Override
    public void save(PersonData data) {
        PersonStore.getInstance().savePerson( data );
    }

    @Override
    public void delete(int id) throws DeleteException {

        if( !hasOtherAdministrators( id ) )
        {
           throw new DeleteException( "Es muss mindestens ein Administrator existieren." );
        }

        if( !PersonStore.getInstance().deletePerson( id ) )
        {
            throw new DeleteException( "Fehler beim löschen einer Person" );
        }
    }

    public int getNextFreeId()
    {
        return PersonCache.getInstance().getNextFreeId();
    }

    private boolean hasOtherAdministrators( int id )
    {
        return getAll().stream()
                .anyMatch( personData -> EPermission.getById( personData.getPermission() ) == EPermission.ADMIN
                        && personData.getId() != id );
    }

    public PersonData getPerson( String email )
    {
        var persons = PersonCache.getInstance().getPersons();

        if( persons != null )
        {
            return persons.stream()
                    .filter( personData -> personData.getEmail().equals( email ) )
                    .findFirst()
                    .orElse( null );
        }

        return null;
    }
}
