package mss301.giaptd.accountservice.configs;

import mss301.giaptd.accountservice.pojos.Account;
import mss301.giaptd.accountservice.pojos.Role;
import mss301.giaptd.accountservice.repositories.AccountRepository;
import mss301.giaptd.accountservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() > 0 || accountRepository.count() > 0) {
            System.out.println("Data already exists, skipping initialization");
            return;
        }

        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleRepository.save(userRole);

        Account admin = new Account();
        admin.setAccountName("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123");
        admin.setRole(adminRole);
        accountRepository.save(admin);

        Account user = new Account();
        user.setAccountName("user");
        user.setEmail("user@example.com");
        user.setPassword("user123");
        user.setRole(userRole);
        accountRepository.save(user);

        System.out.println("Data initialized successfully");
    }
}