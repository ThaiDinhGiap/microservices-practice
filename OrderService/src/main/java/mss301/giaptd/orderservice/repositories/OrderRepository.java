package mss301.giaptd.orderservice.repositories;

import mss301.giaptd.orderservice.pojos.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
