package mss301.giaptd.orderservice.services;

import mss301.giaptd.orderservice.dtos.OrchidDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orchid-service")
public interface OrchidService {
    @GetMapping("/api/orchids/{id}")
    OrchidDTO getOrchid(@PathVariable("id") Long id);
}