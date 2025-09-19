package mss301.giaptd.accountservice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"mss301.giaptd.accountservice.controllers", "mss301.giaptd.accountservice.configs", "mss301.giaptd.accountservice.services"})
@EnableJpaRepositories(basePackages = "mss301.giaptd.accountservice.repositories")
@EntityScan(basePackages = "mss301.giaptd.accountservice.pojos")
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
