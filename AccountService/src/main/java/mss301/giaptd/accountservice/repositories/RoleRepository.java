package mss301.giaptd.accountservice.repositories;

import mss301.giaptd.accountservice.pojos.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
