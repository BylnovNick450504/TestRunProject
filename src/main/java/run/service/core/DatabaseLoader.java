package run.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import run.persistence.user.RunRecord;
import run.persistence.user.User;
import run.persistence.user.role.Role;
import run.service.record.RunRecordRepository;
import run.service.user.UserRepository;
import run.service.user.role.RoleRepository;

@Component
public class DatabaseLoader implements ApplicationRunner {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RunRecordRepository runRecordRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository,
                          RoleRepository roleRepository,
                          RunRecordRepository runRecordRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.runRecordRepository = runRecordRepository;
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

        RunRecord runRecord1 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-09 00:00");
        RunRecord runRecord2 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-10 00:00");
        RunRecord runRecord3 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-16 00:00");
        RunRecord runRecord4 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-17 00:00");
        RunRecord runRecord5 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-23 00:00");
        RunRecord runRecord6 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-24 00:00");
        RunRecord runRecord7 = new RunRecord(user1, 1000.0, 1000.0, "2018-02-16 18:00");

        runRecordRepository.save(runRecord1);
        runRecordRepository.save(runRecord2);
        runRecordRepository.save(runRecord3);
        runRecordRepository.save(runRecord4);
        runRecordRepository.save(runRecord5);
        runRecordRepository.save(runRecord6);
        runRecordRepository.save(runRecord7);

    }
}
