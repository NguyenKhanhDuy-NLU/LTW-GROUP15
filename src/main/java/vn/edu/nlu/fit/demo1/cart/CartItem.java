package vn.edu.nlu.fit.demo1.cart;

import vn.edu.nlu.fit.demo1.model.Room;
import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Room room;
    private int quantity;

    public CartItem(Room room, int quantity) {
        this.room = room;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return room.getBasePrice() * quantity;
    }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
