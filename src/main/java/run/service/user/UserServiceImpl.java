package run.service.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.client.security.custom.JwtUserFactory;
import run.persistence.user.User;
import run.persistence.user.role.Role;
import run.service.core.ResponseStatus;
import run.service.user.role.RoleRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);

    private static final String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<?> createUser(User user) {
        User oldUser = userRepository.findByUsername(user.getUsername());
        LOGGER.info(user.getUsername());
        if (oldUser != null) {
            return new ResponseEntity<>(new ResponseStatus(false, "User with such username already exist."),
                    HttpStatus.CREATED);
        }
        Role role = roleRepository.findByRole(ROLE_USER);
        if (role == null) {
            return new ResponseEntity<>(new ResponseStatus(false, "No role."),
                    HttpStatus.CREATED);

        }
        user.addRole(role);
        userRepository.save(user);
        return new ResponseEntity<>(new ResponseStatus(true, "User created."),
                HttpStatus.CREATED);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
