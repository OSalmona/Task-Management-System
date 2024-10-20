package banquemisr.challenge05.task_management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long status_id;
    private Long priority_id;
    private LocalDate dueDate;
    private LocalDate createdOn;
    private LocalDate updatedOn;
    private Long user_id;


    public TaskDto(Long id, String title, String description, Long status_id, Long priority_id, LocalDate dueDate, LocalDate createdOn, LocalDate updatedOn , Long user_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status_id = status_id;
        this.priority_id = priority_id;
        this.dueDate = dueDate;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.user_id = user_id;
    }
}
