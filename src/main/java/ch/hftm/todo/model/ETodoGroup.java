package ch.hftm.todo.model;

public enum ETodoGroup
{
    ALL ( 0, "Alle" ),
    PRIVATE(1, "Privat"),
    BUSINESS(2, "Geschäftlich"),
    SHOPPING_LIST(3, "Einkaufsliste");

    private int id;
    private String title;

    ETodoGroup(int id, String title )
    {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static ETodoGroup getById( int id )
    {
        ETodoGroup todoGroup = null;

        for( ETodoGroup group : values() )
        {
            if( group.getId() == id )
            {
                todoGroup = group;
                break;
            }
        }

        return todoGroup;
    }
}
