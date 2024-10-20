package banquemisr.challenge05.task_management.service;

import banquemisr.challenge05.task_management.dto.TaskPriorityDto;
import banquemisr.challenge05.task_management.dto.TaskStatusDto;

import java.util.List;

public interface TaskPriorityService {
    TaskPriorityDto getTaskPriorityByID(Long priority_id);
    List<TaskPriorityDto> getAllTaskProrities();
}
