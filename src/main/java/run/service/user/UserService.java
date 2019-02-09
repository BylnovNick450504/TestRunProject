package run.service.user;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import run.persistence.user.User;

public interface UserService {
    ResponseEntity<?> createUser(User user);
    User getUserByToken(HttpServletRequest request);
}
