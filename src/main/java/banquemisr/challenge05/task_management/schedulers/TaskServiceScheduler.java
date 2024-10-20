package banquemisr.challenge05.task_management.schedulers;

import banquemisr.challenge05.task_management.dto.TaskDto;
import banquemisr.challenge05.task_management.service.TaskService;
import banquemisr.challenge05.task_management.service.impl.EmailServiceImpl;
import banquemisr.challenge05.task_management.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TaskServiceScheduler {
    @Autowired
    private TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    private EmailServiceImpl emailService;
    @Scheduled(cron = "0 0 8 * * ?") // Schedule to run at 8 AM every day
    public void sendDeadlineNotifications() {
        List<TaskDto> tasks = taskService.getTasksWithUpcomingDeadlines(LocalDate.now().plusDays(1));
        tasks.forEach( task ->
                emailService.sendSimpleEmail(userService.getUserByID(task.getUser_id()).getUsername(),
                        "Task Deadline Reminder",
                        "Reminder: The task \"" + task.getTitle() + "\" is due tomorrow.")
        );
    }
}
