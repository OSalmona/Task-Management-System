package banquemisr.challenge05.task_management.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getCurrentLoginUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Un Authenticated User : " + username));
    }
    public User getUserByID(Long Id){
        return userRepository.findById(Id)
                .orElseThrow(() -> new UsernameNotFoundException("Un Authenticated User : " + Id));
    }
}
