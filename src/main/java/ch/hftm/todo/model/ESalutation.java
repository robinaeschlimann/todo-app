package ch.hftm.todo.model;

import java.util.Arrays;

public enum ESalutation
{
    MR( 1, "Herr" ),
    MRS( 2, "Frau" );

    private int id;
    private String text;

    ESalutation( int id, String text )
    {
        this.id = id;
        this.text = text;
    }

    public int getId()
    {
        return id;
    }

    public String getText()
    {
        return text;
    }

    public static ESalutation getById( int id )
    {
        return Arrays.stream( values() )
                .filter( salutation -> salutation.getId() == id )
                .findFirst()
                .orElse( null );
    }
}
