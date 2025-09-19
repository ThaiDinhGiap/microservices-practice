package mss301.giaptd.orderservice.repositories;

import mss301.giaptd.orderservice.pojos.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
