package banquemisr.challenge05.task_management.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {
    public void sendSimpleEmail(String toEmail, String subject, String body);
}
