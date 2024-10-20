package banquemisr.challenge05.task_management.entity;

import banquemisr.challenge05.task_management.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(name = "priority_id")
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDate createdOn;
    private LocalDate updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;
}
