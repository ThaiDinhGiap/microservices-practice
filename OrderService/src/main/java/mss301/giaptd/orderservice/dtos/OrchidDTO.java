package mss301.giaptd.orderservice.dtos;

public class OrchidDTO {
    private Long orchidID;
    private String orchidName;
    private double price;

    public Long getOrchidID() { return orchidID; }
    public void setOrchidID(Long orchidID) { this.orchidID = orchidID; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
