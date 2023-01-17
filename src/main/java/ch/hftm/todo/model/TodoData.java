package ch.hftm.todo.model;

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
    private String person;
    private int group;
    private boolean done;
}
