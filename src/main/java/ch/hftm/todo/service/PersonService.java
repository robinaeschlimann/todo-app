package ch.hftm.todo.service;

import ch.hftm.todo.caches.PersonCache;
import ch.hftm.todo.caches.TodoCache;
import ch.hftm.todo.model.PersonData;
import ch.hftm.todo.stores.PersonStore;

import java.util.List;

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
        return null;
    }

    @Override
    public void save(PersonData data) {
        PersonStore.getInstance().savePerson( data );
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public int getNextFreeId()
    {
        return PersonCache.getInstance().getNextFreeId();
    }
}
