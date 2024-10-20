package banquemisr.challenge05.task_management.controller;

import banquemisr.challenge05.task_management.dto.PageResponseDto;
import banquemisr.challenge05.task_management.dto.TaskDto;
import banquemisr.challenge05.task_management.entity.Task;
import banquemisr.challenge05.task_management.service.TaskService;
import banquemisr.challenge05.task_management.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskStatusService taskStatusService;
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<TaskDto>> getAllTasksForAdmin() {
        return ResponseEntity.ok(taskService.getAllTasksForAdmin());
    }
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TaskDto>>  getAllTasksForUser() {
        return ResponseEntity.ok(taskService.getAllTasksForUser());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto savedTaskDto = taskService.createTask(taskDto);
        return new ResponseEntity<>(savedTaskDto , HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDetails) {
        TaskDto updatedTask = taskService.updateTask(id, taskDetails);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTask);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
    @PostMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<TaskDto>> searchTasks(@RequestBody TaskDto criteria) {
        return ResponseEntity.ok(taskService.searchTasks(criteria));
    }
    @PostMapping("/pagination/{offset}/{pageSize}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<PageResponseDto<Page<TaskDto>>> getProductsWithPaginationForAdmin(@PathVariable int offset, @PathVariable int pageSize , @RequestBody TaskDto criteria ) {

        Page<TaskDto> taskWithPagination = taskService.findTasksWithPagination(offset, pageSize ,criteria );
        return ResponseEntity.ok(new PageResponseDto<>(taskWithPagination.getSize(), taskWithPagination));
    }
    @PostMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<PageResponseDto<Page<TaskDto>>> getProductsWithPaginationAndSortForAdmin(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field , @RequestBody TaskDto criteria) {
        Page<TaskDto> productsWithPagination = taskService.findTasksWithPaginationAndSorting(offset, pageSize, field ,criteria );
        return ResponseEntity.ok(new PageResponseDto<>(productsWithPagination.getSize(), productsWithPagination));
    }

}
