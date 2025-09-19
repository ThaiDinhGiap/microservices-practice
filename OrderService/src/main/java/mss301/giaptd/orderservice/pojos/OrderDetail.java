package mss301.giaptd.orderservice.pojos;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orchid_id")
    private Long orchidId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrchidId() { return orchidId; }
    public void setOrchidId(Long orchidId) { this.orchidId = orchidId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
