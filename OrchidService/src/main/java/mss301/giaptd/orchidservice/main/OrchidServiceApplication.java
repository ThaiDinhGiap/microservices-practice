package mss301.giaptd.orchidservice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"mss301.giaptd.orchidservice.controllers", "mss301.giaptd.orchidservice.configs", "mss301.giaptd.orchidservice.services"})
@EnableJpaRepositories(basePackages = "mss301.giaptd.orchidservice.repositories")
@EntityScan(basePackages = "mss301.giaptd.orchidservice.pojos")
public class OrchidServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchidServiceApplication.class, args);
    }

}
