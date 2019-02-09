package run.service.user;

import org.springframework.data.repository.CrudRepository;
import run.persistence.user.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}
