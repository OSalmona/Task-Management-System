package banquemisr.challenge05.task_management.mapper;

import banquemisr.challenge05.task_management.dto.TaskStatusDto;
import banquemisr.challenge05.task_management.entity.TaskStatus;

public class TaskStatusMapper {
    public static TaskStatusDto mapToTaskStatusDto(TaskStatus taskStatus){
        return new TaskStatusDto(
                taskStatus.getId(),
                taskStatus.getName()
        );
    }
    public static TaskStatus mapToTaskStatus(TaskStatusDto taskStatusDto){

        return new TaskStatus(
                taskStatusDto.getId(),
                taskStatusDto.getName()
        );
    }
}