package run.service.user;

import org.springframework.http.ResponseEntity;
import run.persistence.user.User;

public interface UserService {
    ResponseEntity<?> createUser(User user);
}
