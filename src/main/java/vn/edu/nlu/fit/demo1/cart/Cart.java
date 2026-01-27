package vn.edu.nlu.fit.demo1.cart;

import vn.edu.nlu.fit.demo1.model.Room;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Integer, CartItem> data = new HashMap<>();


    public void add(Room room, int quantity, int nights) {
        if (data.containsKey(room.getId())) {
            CartItem item = data.get(room.getId());
            item.setQuantity(item.getQuantity() + quantity);
            item.setNights(nights);
        } else {
            data.put(room.getId(), new CartItem(room, quantity, nights));
        }
    }

    public double getTotalMoney() {
        double total = 0;
        for (CartItem item : data.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void update(int id, int quantity) {
        if (data.containsKey(id)) {
            if (quantity < 1) {
                data.remove(id);
            } else {
                data.get(id).setQuantity(quantity);
            }
        }
    }

    public CartItem deleteRoom(int id) {
        return data.remove(id);
    }

    public List<CartItem> removeAll() {
        List<CartItem> removedItems = new ArrayList<>(data.values());
        data.clear();
        return removedItems;
    }

    public List<CartItem> getList() {
        return new ArrayList<>(data.values());
    }


}
