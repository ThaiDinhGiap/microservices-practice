package mss301.giaptd.orderservice.dtos;

import java.util.List;

public class OrderDTO {
    private long accountId;
    private List<OrderDetailDTO> details;
    private String orderStatus;

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public List<OrderDetailDTO> getDetails() { return details; }
    public void setDetails(List<OrderDetailDTO> details) { this.details = details; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
