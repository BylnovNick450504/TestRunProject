package run.service.user.role;


import org.springframework.data.repository.CrudRepository;
import run.persistence.user.role.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
