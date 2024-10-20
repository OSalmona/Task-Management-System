package banquemisr.challenge05.task_management.service.impl;

import banquemisr.challenge05.task_management.dto.TaskDto;
import banquemisr.challenge05.task_management.entity.*;
import banquemisr.challenge05.task_management.exception.NotFoundTasksException;
import banquemisr.challenge05.task_management.exception.ResourceNotFoundException;
import banquemisr.challenge05.task_management.exception.UnAuthorizedUserException;
import banquemisr.challenge05.task_management.mapper.TaskMapper;
import banquemisr.challenge05.task_management.mapper.TaskPriorityMapper;
import banquemisr.challenge05.task_management.mapper.TaskStatusMapper;
import banquemisr.challenge05.task_management.repository.TaskRepository;
import banquemisr.challenge05.task_management.service.TaskPriorityService;
import banquemisr.challenge05.task_management.service.TaskService;
import banquemisr.challenge05.task_management.service.TaskStatusService;
import banquemisr.challenge05.task_management.user.Role;
import banquemisr.challenge05.task_management.user.User;
import banquemisr.challenge05.task_management.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final TaskRepository taskRepository;

    private final TaskStatusService taskStatusService;
    private final TaskPriorityService taskPriorityService;
    private final UserService userService;
    @Override
    public List<TaskDto> getAllTasksForAdmin() {
        LOGGER.log(Level.INFO, "getAllTasksForAdmin method started.");
        List<Task> tasks  = taskRepository.findAll();

        if(tasks.isEmpty())
            throw new NotFoundTasksException("Not tasks found in data base");
        LOGGER.log(Level.INFO, "getAllTasksForAdmin method finished.");
        return tasks.stream().map(TaskMapper::mapToTaskDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllTasksForUser() {
        User currUser = userService.getCurrentLoginUser();
        LOGGER.log(Level.INFO, "getAllTasksForUser method started. user id : " + currUser.getId());
        if(currUser.getTasks().isEmpty())
            throw new NotFoundTasksException("Not found tasks");
        LOGGER.log(Level.INFO, "getAllTasksForUser method finished. user id : " + currUser.getId());
        return currUser.getTasks().stream().map(TaskMapper::mapToTaskDto).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(Long id) {
        LOGGER.log(Level.INFO, "getTaskById method started. task id : " + id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task is not exist with given id : " + id));

        User currUser = userService.getCurrentLoginUser();

        if(currUser.getRole() == Role.USER && !task.getUser().equals(currUser))
            throw new UnAuthorizedUserException("Un Authorized user to access task : " + id);

        LOGGER.log(Level.INFO, "getTaskById method finished. task id : " + id);
        return TaskMapper.mapToTaskDto(task);
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        User currUser = userService.getCurrentLoginUser();
        LOGGER.log(Level.INFO, "createTask method started. user id : " + currUser.getId());

        TaskStatus taskStatus = TaskStatusMapper.mapToTaskStatus(taskStatusService.getTaskStatusByID(taskDto.getStatus_id()));
        TaskPriority taskPriority = TaskPriorityMapper.mapToTaskPriority(taskPriorityService.getTaskPriorityByID(taskDto.getPriority_id()));
        Task task = TaskMapper.mapToTask(taskDto , taskStatus ,taskPriority,currUser);
        task.setCreatedOn(LocalDate.now());
        task = taskRepository.save(task);
        LOGGER.log(Level.INFO, "createTask method finished. user id : %d ,task id : %d".formatted(currUser.getId(), task.getId()));
        return TaskMapper.mapToTaskDto(task);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDetails) {
        TaskDto task = getTaskById(id);
        User currUser = userService.getCurrentLoginUser();
        LOGGER.log(Level.INFO, "updateTask method started. user id : %d ,task id : %d".formatted(currUser.getId(), id));

        if(currUser.getRole() == Role.USER && !task.getUser_id().equals(currUser.getId()))
            throw new UnAuthorizedUserException("Un Authorized user to access task : " + id);

        task.setTitle(taskDetails.getTitle() != null ? taskDetails.getTitle() : task.getTitle());
        task.setDescription(taskDetails.getDescription() != null ? taskDetails.getDescription() : task.getDescription());
        task.setDueDate(taskDetails.getDueDate() != null ? taskDetails.getDueDate() : task.getDueDate());
        task.setStatus_id(taskDetails.getStatus_id() != null ? taskDetails.getStatus_id() : task.getStatus_id());
        task.setPriority_id(taskDetails.getPriority_id() != null ? taskDetails.getPriority_id() : task.getPriority_id());
        task.setUpdatedOn(LocalDate.now());

        TaskStatus status = TaskStatusMapper.mapToTaskStatus(taskStatusService.getTaskStatusByID(task.getStatus_id()));
        TaskPriority priority = TaskPriorityMapper.mapToTaskPriority(taskPriorityService.getTaskPriorityByID(task.getPriority_id()));
        Task updatedTask = TaskMapper.mapToTask(task ,status,priority,currUser);
        updatedTask =  taskRepository.save(updatedTask);

        LOGGER.log(Level.INFO, "updateTask method finished. user id : %d ,task id : %d".formatted(currUser.getId(), id));

        return TaskMapper.mapToTaskDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        LOGGER.log(Level.INFO, "deleteTask method started. task id : %d".formatted( id));
        TaskDto task = getTaskById(id);
        taskRepository.deleteById(task.getId());
        LOGGER.log(Level.INFO, "deleteTask method finished. task id : %d".formatted( id));
    }
    @Override
    public List<TaskDto> searchTasks(TaskDto criteria) {
        LOGGER.log(Level.INFO, "searchTasks method started");

        TaskStatus status =
                criteria.getStatus_id() != null ? TaskStatusMapper.mapToTaskStatus(taskStatusService.getTaskStatusByID(criteria.getStatus_id())) : null;
        TaskPriority priority =
                criteria.getPriority_id() != null ? TaskPriorityMapper.mapToTaskPriority(taskPriorityService.getTaskPriorityByID(criteria.getPriority_id())) : null;
        User user = criteria.getUser_id() != null ? userService.getUserByID(criteria.getUser_id()) : null;

        User currUser = userService.getCurrentLoginUser();
        if(currUser.getRole() == Role.USER && user == null)
            throw new ResourceNotFoundException(" user id is null");

        if(currUser.getRole() == Role.USER && !criteria.getUser_id().equals(user.getId()))
            throw new UnAuthorizedUserException("Un Authorized user to access task : " + criteria.getUser_id());

        Task taskCriteria = TaskMapper.mapToTask(criteria ,status,priority,user);
        TaskSpecification spec = new TaskSpecification(taskCriteria);
        List<Task> tasks = taskRepository.findAll(spec);

        if(tasks.isEmpty())
            throw new NotFoundTasksException("No Found Tasks with your criteria");
        LOGGER.log(Level.INFO, "searchTasks method finished");
        return tasks.stream().map(TaskMapper::mapToTaskDto).collect(Collectors.toList());
    }
    @Override
    public Page<TaskDto> findTasksWithPagination(int offset, int pageSize , TaskDto criteria ){
        LOGGER.log(Level.INFO, "findTasksWithPagination method started");

        Task taskCriteria =
                Task.builder()
                        .title(criteria.getTitle())
                        .description(criteria.getDescription())
                        .status(criteria.getStatus_id() == null ? null : TaskStatusMapper.mapToTaskStatus(taskStatusService.getTaskStatusByID(criteria.getStatus_id())))
                        .dueDate(criteria.getDueDate())
                        .user(criteria.getUser_id() == null ? null : userService.getUserByID(criteria.getUser_id()))
                        .build();

        TaskSpecification spec = new TaskSpecification(taskCriteria);
        Page<Task> pageOfTasks = taskRepository.findAll(spec,PageRequest.of(offset, pageSize));
        LOGGER.log(Level.INFO, "findTasksWithPagination method finished");
        return pageOfTasks.map(TaskMapper::mapToTaskDto);
    }
    @Override
    public Page<TaskDto> findTasksWithPaginationAndSorting(int offset,int pageSize,String field ,TaskDto criteria)  {
        LOGGER.log(Level.INFO, "findTasksWithPaginationAndSorting method started");

        Task taskCriteria =
                Task.builder()
                        .title(criteria.getTitle())
                        .description(criteria.getDescription())
                        .status(criteria.getStatus_id() == null ? null : TaskStatusMapper.mapToTaskStatus(taskStatusService.getTaskStatusByID(criteria.getStatus_id())))
                        .dueDate(criteria.getDueDate())
                        .user(criteria.getUser_id() == null ? null : userService.getUserByID(criteria.getUser_id()))
                        .build();

        TaskSpecification spec = new TaskSpecification(taskCriteria);
        Page<Task> pageOfTasks = taskRepository.findAll(spec,PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        LOGGER.log(Level.INFO, "findTasksWithPaginationAndSorting method finished");

        return  pageOfTasks.map(TaskMapper::mapToTaskDto);
    }
    @Override
    public List<TaskDto> getTasksWithUpcomingDeadlines(LocalDate date) {
        LOGGER.log(Level.INFO, "getTasksWithUpcomingDeadlines method started");
        List<TaskDto> taskDtos = taskRepository.findTasksByDueDate(date).stream().map(TaskMapper::mapToTaskDto).collect(Collectors.toList());
        LOGGER.log(Level.INFO, "getTasksWithUpcomingDeadlines method finished");
        return taskDtos;
    }
}
