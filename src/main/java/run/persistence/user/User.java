package run.persistence.user;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import run.persistence.core.BaseEntity;
import run.persistence.user.role.Role;

@Entity
@Table(name = "person")
public class User extends BaseEntity {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_person")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RunRecord> runRecords = new ArrayList<>();

    public User() {
    }

    public User(String username,
                String password,
                String email,
                String name) {
        this.username = username;
        setPassword(password);
        this.email = email;
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addRole(Role roleItem) {
        roles.add(roleItem);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<RunRecord> getRunRecords() {
        return runRecords;
    }

    public void setRunRecords(List<RunRecord> runRecords) {
        this.runRecords = runRecords;
    }

    public void addRunRecord(RunRecord record) {
        runRecords.add(record);
        record.setUser(this);
    }
}
