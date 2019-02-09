package run.persistence.user.role;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import run.persistence.core.BaseEntity;
import run.persistence.user.User;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @NotNull
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User userItem) {
        userItem.addRole(this);
        users.add(userItem);
    }
}
