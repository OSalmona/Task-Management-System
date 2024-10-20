package banquemisr.challenge05.task_management.repository;

import banquemisr.challenge05.task_management.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus,Long> {
}
