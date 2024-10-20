package banquemisr.challenge05.task_management.service.impl;

import banquemisr.challenge05.task_management.dto.TaskStatusDto;
import banquemisr.challenge05.task_management.entity.TaskStatus;
import banquemisr.challenge05.task_management.exception.NotFoundTasksException;
import banquemisr.challenge05.task_management.exception.ResourceNotFoundException;
import banquemisr.challenge05.task_management.mapper.TaskStatusMapper;
import banquemisr.challenge05.task_management.repository.TaskStatusRepository;
import banquemisr.challenge05.task_management.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatusDto getTaskStatusByID(Long status_id) {
        TaskStatus taskStatus = taskStatusRepository.findById(status_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Status is not exist with given id : " + status_id));
        return TaskStatusMapper.mapToTaskStatusDto(taskStatus);
    }

    @Override
    public List<TaskStatusDto> getAllTaskStatuses() {
        List<TaskStatus> taskStatuses  = taskStatusRepository.findAll();

        if(taskStatuses.isEmpty())
            throw new NotFoundTasksException("No Task Statuses founs in database");

        return taskStatuses.stream().map(TaskStatusMapper::mapToTaskStatusDto).collect(Collectors.toList());
    }
}
