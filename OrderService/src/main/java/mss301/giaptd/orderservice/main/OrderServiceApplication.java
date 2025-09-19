package mss301.giaptd.orderservice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(basePackages = "mss301.giaptd.orderservice.services")
@ComponentScan({"mss301.giaptd.orderservice.controllers", "mss301.giaptd.orderservice.services"})
@EnableJpaRepositories(basePackages = "mss301.giaptd.orderservice.repositories")
@EntityScan(basePackages = "mss301.giaptd.orderservice.pojos")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
