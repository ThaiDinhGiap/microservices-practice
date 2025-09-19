package mss301.giaptd.orderservice.services;

import mss301.giaptd.orderservice.dtos.OrderDTO;
import mss301.giaptd.orderservice.pojos.Order;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    Order createOrder(OrderDTO request);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(int orderId);
    void deleteOrder(int orderId);
}
