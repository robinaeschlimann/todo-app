package ch.hftm.todo.model;

import ch.hftm.todo.service.PersonService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoData
{
    private int id;
    private String name;
    private String description;
    private String deadline;
    private int person;
    private int group;
    private boolean done;

    @JsonIgnore
    public PersonData getPersonData() {
        return PersonService.getInstance().get(person);
    }
}
