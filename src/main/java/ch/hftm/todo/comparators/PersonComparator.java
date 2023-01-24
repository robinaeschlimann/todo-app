package ch.hftm.todo.comparators;

import ch.hftm.todo.model.PersonData;

import java.util.Comparator;

public class PersonComparator implements Comparator<PersonData>
{
    @Override
    public int compare( PersonData o1, PersonData o2 )
    {
        return o1.getFirstname().compareTo( o2.getFirstname() );
    }
}
