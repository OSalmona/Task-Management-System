package banquemisr.challenge05.task_management.service.impl;

import banquemisr.challenge05.task_management.dto.TaskPriorityDto;
import banquemisr.challenge05.task_management.entity.TaskPriority;
import banquemisr.challenge05.task_management.exception.NotFoundTasksException;
import banquemisr.challenge05.task_management.exception.ResourceNotFoundException;
import banquemisr.challenge05.task_management.mapper.TaskPriorityMapper;
import banquemisr.challenge05.task_management.repository.TaskPriorityRepository;
import banquemisr.challenge05.task_management.service.TaskPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskPriorityServiceImpl implements TaskPriorityService {
    @Autowired
    private TaskPriorityRepository taskPriorityRepository;

    @Override
    public TaskPriorityDto getTaskPriorityByID(Long priority_id) {
        TaskPriority taskPriority = taskPriorityRepository.findById(priority_id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Priority is not exist with given id : " + priority_id));
        return TaskPriorityMapper.mapToTaskPriorityDto(taskPriority);
    }

    @Override
    public List<TaskPriorityDto> getAllTaskProrities() {
        List<TaskPriority> taskPriorities  = taskPriorityRepository.findAll();

        if(taskPriorities.isEmpty())
            throw new NotFoundTasksException("No Task Statuses founs in database");

        return taskPriorities.stream().map(TaskPriorityMapper::mapToTaskPriorityDto).collect(Collectors.toList());

    }
}
