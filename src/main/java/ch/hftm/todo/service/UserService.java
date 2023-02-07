package ch.hftm.todo.service;

import ch.hftm.todo.model.PersonData;

public class UserService
{
    private static final UserService INSTANCE = new UserService();

    private PersonData loggedInUser;

    private UserService(){}

    public static UserService getInstance() {
        return INSTANCE;
    }

    public PersonData getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(PersonData loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
