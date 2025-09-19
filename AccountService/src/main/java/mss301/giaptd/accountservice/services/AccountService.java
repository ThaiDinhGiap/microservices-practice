package mss301.giaptd.accountservice.services;

import mss301.giaptd.accountservice.pojos.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {
    List<Account> getAllAccounts();

    Optional<Account> getAccountById(int id);

    Account createAccount(Account account);

    Account updateAccount(int id, Account account);

    void deleteAccount(int id);

    Account findByEmail(String email);

    Account findByAccountName(String accountName);

    List<Account> findByRoleRoleId(int roleId);

    List<Account> findByRoleRoleName(String roleName);

    List<Account> searchAccountsByEmail(String emailPattern);

    boolean checkEmailExists(String email);
}