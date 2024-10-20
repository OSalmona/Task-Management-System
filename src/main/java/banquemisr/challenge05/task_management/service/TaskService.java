package banquemisr.challenge05.task_management.service;

import banquemisr.challenge05.task_management.dto.TaskDto;
import banquemisr.challenge05.task_management.entity.Task;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    public List<TaskDto> getAllTasksForAdmin();
    public List<TaskDto> getAllTasksForUser();

    public TaskDto getTaskById(Long task_id);
    public TaskDto createTask(TaskDto task);
    public TaskDto updateTask(Long id, TaskDto taskDetails);
    public void deleteTask(Long id);
    public List<TaskDto> searchTasks(TaskDto criteria);
    public Page<TaskDto> findTasksWithPagination(int offset, int pageSize , TaskDto criteria );
    Page<TaskDto> findTasksWithPaginationAndSorting(int offset,int pageSize,String field ,TaskDto criteria);
    public List<TaskDto> getTasksWithUpcomingDeadlines(LocalDate date);
}
