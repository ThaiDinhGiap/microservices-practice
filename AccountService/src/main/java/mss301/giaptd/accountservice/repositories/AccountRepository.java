package mss301.giaptd.accountservice.repositories;

import mss301.giaptd.accountservice.pojos.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    Account findByAccountName(String accountName);

    List<Account> findByRoleRoleId(int roleID);

    List<Account> findByRoleRoleName(String roleName);

    List<Account> findByEmailContaining(String emailPattern);

    boolean existsByEmail(String email);

    List<Account> findByRoleRoleNameAndEmailContaining(String roleName, String email);

    List<Account> findByAccountNameContaining(String accountName);

    default List<Account> search(String emailPattern) {
        return findByEmailContaining(emailPattern);
    }
}
