package mss301.giaptd.orderservice.dtos;

public class OrderDetailDTO {
    private Long orchidId;
    private int quantity;

    public Long getOrchidId() { return orchidId; }
    public void setOrchidId(Long orchidId) { this.orchidId = orchidId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
