package Users;

import JavaFood.AdminPanel;
import java.util.ArrayList;
import java.util.stream.Collectors;
import Models.Order;

public class User {
    private int id;
    private String name;
    public ArrayList<Order> orders = new ArrayList<Order>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }
}