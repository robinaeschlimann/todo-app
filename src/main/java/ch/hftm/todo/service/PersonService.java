package ch.hftm.todo.service;

import ch.hftm.todo.caches.PersonCache;
import ch.hftm.todo.model.PersonData;

import java.util.List;

public class PersonService
{
    private static final PersonService INSTANCE = new PersonService();

    public static PersonService getInstance()
    {
        return INSTANCE;
    }

    private PersonService(){}

    public List<PersonData> getPersons()
    {
        return PersonCache.getInstance().getPersons();
    }
}
