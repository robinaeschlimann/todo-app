package ch.hftm.todo.model;

public class TodoJson
{
    private int id;
    private String name;
    private String description;
    private String deadline;
    private String person;
    private int group;
    private boolean done;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getPerson() {
        return person;
    }

    public int getGroup() {
        return group;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
