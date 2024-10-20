package banquemisr.challenge05.task_management.mapper;

import banquemisr.challenge05.task_management.dto.TaskPriorityDto;
import banquemisr.challenge05.task_management.entity.TaskPriority;

public class TaskPriorityMapper {
    public static TaskPriorityDto mapToTaskPriorityDto(TaskPriority taskPriority){
        return new TaskPriorityDto(
                taskPriority.getId(),
                taskPriority.getName()
        );
    }
    public static TaskPriority mapToTaskPriority(TaskPriorityDto taskPriorityDto){

        return new TaskPriority(
                taskPriorityDto.getId(),
                taskPriorityDto.getName()
        );
    }
}