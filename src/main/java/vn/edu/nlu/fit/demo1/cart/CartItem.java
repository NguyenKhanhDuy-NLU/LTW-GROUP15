package vn.edu.nlu.fit.demo1.cart;

import vn.edu.nlu.fit.demo1.model.Room;
import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Room room;
    private int quantity;
    private int nights;

    public CartItem(Room room, int quantity, int nights) {
        this.room = room;
        this.quantity = quantity;
        this.nights = (nights > 0) ? nights : 1;
    }

    public double getTotalPrice() {
        return room.getBasePrice() * quantity * nights;
    }
    public int getNights() { return nights; }
    public void setNights(int nights) { this.nights = nights; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
