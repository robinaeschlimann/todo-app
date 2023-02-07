package ch.hftm.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonData implements IData
{
    private int id;
    private int salutation;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int permission;

    @Override
    public String toString()
    {
        return getFirstname() + " " + getLastname();
    }
}
