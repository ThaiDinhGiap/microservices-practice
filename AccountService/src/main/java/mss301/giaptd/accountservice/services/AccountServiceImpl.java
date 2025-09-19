package mss301.giaptd.accountservice.services;

import mss301.giaptd.accountservice.pojos.Account;
import mss301.giaptd.accountservice.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(int id, Account account) {
        if (accountRepository.existsById(id)) {
            account.setAccountID(id);
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account findByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    @Override
    public List<Account> findByRoleRoleId(int roleId) {
        return accountRepository.findByRoleRoleId(roleId);
    }

    @Override
    public List<Account> findByRoleRoleName(String roleName) {
        return accountRepository.findByRoleRoleName(roleName);
    }

    @Override
    public List<Account> searchAccountsByEmail(String emailPattern) {
        return accountRepository.findByEmailContaining(emailPattern);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return accountRepository.existsByEmail(email);
    }
}
