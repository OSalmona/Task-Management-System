package banquemisr.challenge05.task_management.mapper;

import banquemisr.challenge05.task_management.dto.TaskDto;
import banquemisr.challenge05.task_management.entity.Task;
import banquemisr.challenge05.task_management.entity.TaskPriority;
import banquemisr.challenge05.task_management.entity.TaskStatus;
import banquemisr.challenge05.task_management.user.User;

public class TaskMapper {
    public static TaskDto mapToTaskDto(Task task){
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getId(),
                task.getPriority().getId(),
                task.getDueDate(),
                task.getCreatedOn(),
                task.getUpdatedOn(),
                task.getUser().getId()
        );
    }
    public static Task mapToTask(TaskDto taskDto , TaskStatus taskStatus , TaskPriority taskPriority , User user){

        return new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskStatus,
                taskPriority,
                taskDto.getDueDate(),
                taskDto.getCreatedOn(),
                taskDto.getUpdatedOn(),
                user
        );
    }
}