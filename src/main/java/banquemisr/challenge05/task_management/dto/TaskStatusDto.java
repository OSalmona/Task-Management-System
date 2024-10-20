package banquemisr.challenge05.task_management.dto;

import lombok.Data;

@Data
public class TaskStatusDto {
    private Long id;
    private String name;

    public TaskStatusDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
