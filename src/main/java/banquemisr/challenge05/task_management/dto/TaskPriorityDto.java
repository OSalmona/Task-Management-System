package banquemisr.challenge05.task_management.dto;

import lombok.Data;


@Data
public class TaskPriorityDto {
    private Long id;
    private String name;

    public TaskPriorityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
