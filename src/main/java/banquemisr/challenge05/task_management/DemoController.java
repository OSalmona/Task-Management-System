package banquemisr.challenge05.task_management;

import banquemisr.challenge05.task_management.user.User;
import banquemisr.challenge05.task_management.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @Autowired
    UserRepository userRepository;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sayHello(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Un Authenticated User : " + username));
        return ResponseEntity.ok(currUser.getAuthorities().toString());
    }
}