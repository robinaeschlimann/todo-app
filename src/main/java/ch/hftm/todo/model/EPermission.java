package ch.hftm.todo.model;

public enum EPermission
{
    ADMIN ( 1, "Administrator" ),
    USER ( 2, "Benutzer" );

    private final String text;
    private final int id;

    EPermission( int id, String text )
    {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText()
    {
        return text;
    }

    public static EPermission getById( int id )
    {
        for( EPermission permission : values() )
        {
            if( permission.getId() == id )
            {
                return permission;
            }
        }

        return null;
    }
}
