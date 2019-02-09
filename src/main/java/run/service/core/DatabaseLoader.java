package run.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import run.persistence.user.User;
import run.persistence.user.role.Role;
import run.service.user.UserRepository;
import run.service.user.role.RoleRepository;

@Component
public class DatabaseLoader implements ApplicationRunner {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository,
                          RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user1 = new User("nick","1234", "main@tut.by","nick");
        User user3 = new User("let","letmein", "zap@tut.by", "evgen");
        User user4 = new User("mosya","password", "helg@tut.by", "olga");
        User user5 = new User("qwerty","1234", "helg@tut.by", "olga");
        User user6 = new User("a","1234", "helg@tut.by", "olga");

        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");

        roleRepository.save(role1);
        roleRepository.save(role2);
        user1.addRole(role1);
        user3.addRole(role2);
        user4.addRole(role1);
        user5.addRole(role1);
        user6.addRole(role2);

        userRepository.save(user1);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
    }
}
