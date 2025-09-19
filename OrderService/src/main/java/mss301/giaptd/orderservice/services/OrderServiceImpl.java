package mss301.giaptd.orderservice.services;

import mss301.giaptd.orderservice.dtos.AccountDTO;
import mss301.giaptd.orderservice.dtos.OrchidDTO;
import mss301.giaptd.orderservice.dtos.OrderDTO;
import mss301.giaptd.orderservice.dtos.OrderDetailDTO;
import mss301.giaptd.orderservice.pojos.Order;
import mss301.giaptd.orderservice.pojos.OrderDetail;
import mss301.giaptd.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrchidService orchidClient;

    @Autowired
    private AccountService accountClient;

    @Override
    public Order createOrder(OrderDTO request) {
        AccountDTO accountDTO = accountClient.getAccount(request.getAccountId());
        Order order = new Order();
        order.setOrderDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        order.setOrderDetails(new java.util.ArrayList<>());
        order.setAccountId(accountDTO.getAccountID());
        order.setOrderStatus(request.getOrderStatus());

        double total = 0;
        for (OrderDetailDTO detail : request.getDetails()) {
            OrchidDTO orchid = orchidClient.getOrchid(detail.getOrchidId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrchidId(orchid.getOrchidID());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setPrice(orchid.getPrice());
            total += orchid.getPrice() * detail.getQuantity();
            order.getOrderDetails().add(orderDetail);
        }
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }
}