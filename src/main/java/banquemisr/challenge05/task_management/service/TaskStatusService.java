package banquemisr.challenge05.task_management.service;

import banquemisr.challenge05.task_management.dto.TaskStatusDto;

import java.util.List;

public interface TaskStatusService {
    TaskStatusDto getTaskStatusByID(Long status_id);
    List<TaskStatusDto> getAllTaskStatuses();
}
