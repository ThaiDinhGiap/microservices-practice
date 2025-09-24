package mss301.giaptd.orderservice.services;

import mss301.giaptd.orderservice.dtos.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountService {
    @GetMapping("/api/accounts/{id}")
    AccountDTO getAccount(@PathVariable("id") Long id);
}

