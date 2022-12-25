package ch.hftm.todo.model;

public class PersonData
{
    private int id;
    private int salutation;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public int getId() {
        return id;
    }

    public int getSalutation() {
        return salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSalutation(int salutation) {
        this.salutation = salutation;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
