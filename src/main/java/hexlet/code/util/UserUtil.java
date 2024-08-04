package hexlet.code.util;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserUtil {
    private final UserRepository userRepository;

    public boolean isTheSameUser(Long id) {
        var user = getCurrentuser();
        var user2 = userRepository.findById(id);
        return user.getId().equals(user2.get().getId());
    }

    public User getCurrentuser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        } else {
            var email = authentication.getName();
            return userRepository.findByEmail(email).get();
        }
    }
}
