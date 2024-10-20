package banquemisr.challenge05.task_management.repository;

import banquemisr.challenge05.task_management.entity.Task;
import banquemisr.challenge05.task_management.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> , JpaSpecificationExecutor<Task> {
    Optional<Task> findByUser(User user);
    List<Task> findTasksByDueDate(LocalDate dueDate);
}
