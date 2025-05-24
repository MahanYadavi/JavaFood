package Models;

import JavaFood.AdminPanel;
import lombok.Data;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Restaurant {
    public enum RestaurantTypes {
        FASTFOOD,
        IRANI,
        VEGETARIAN,
        KEBAB,
        SALAD,
        CAFE,
        SUPERMARKET,
        COFFEE
    }

    private RestaurantTypes type;
    private Integer id;
    private String name;
    private Double score = 0.0;
    private Integer scoreCounts = 0;
    private String address;
    int openHour, closeHour;
    // Food - quantity
    final LinkedHashMap<Food, Integer> foods = new LinkedHashMap<>();
    private Boolean isOpen = false;

    public Restaurant(Integer id, String name, String address, int openHour, int closeHour, RestaurantTypes type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.type = type;
    }

    public void openRestaurant() {
        LocalTime lt = LocalTime.now();
        LocalTime openTime = LocalTime.of(openHour, 0);
        LocalTime closeTime = LocalTime.of(closeHour, 0);

        if (lt.isAfter(openTime) && lt.isBefore(closeTime)) {
            this.isOpen = true;
        } else {
            this.isOpen = false;
        }

    }

    public void closeRestaurant() {
        if (this.isOpen) {
            this.isOpen = false;
        } else {
            return;
        }
    }

    public void addFood(Food food, Integer quantity) {
        foods.put(food, quantity);
    }

    public int getTodayOrdersCount() {
        int count = 0;
        for (Order o : AdminPanel.orders) {
            if (o.getRestaurant().getId().equals(this.id) &&
                    o.getOrderDateTime() != null &&
                    o.getOrderDateTime().toLocalDate().equals(AdminPanel.todayDate)) {
                count++;
            }
        }
        return count;
    }

    public int getAllOrdersCount() {
        int count = 0;
        for (Order o : AdminPanel.orders) {
            if (o.getRestaurant().getId().equals(this.id)) {
                count++;
            }
        }
        return count;
    }

    public Double getTodayOrdersAmount() {
        double count = 0.0;
        for (Order o : AdminPanel.orders) {
            if (o.getRestaurant().getId().equals(this.id) &&
                    o.getOrderDateTime() != null &&
                    o.getOrderDateTime().toLocalDate().equals(AdminPanel.todayDate)) {
                count += o.getTotalPrice();
            }
        }
        return count;
    }

    public Double getAllOrdersAmount() {
        double count = 0.0;
        for (Order o : AdminPanel.orders) {
            if (o.getRestaurant().getId().equals(this.id)) {
                count += o.getTotalPrice();
            }
        }
        return count;
    }

    public void updateFoodQuantity(Food food, Integer quantity) {
        if (foods.containsKey(food)) {
            foods.put(food, quantity);
        }
    }

    public Food getMostOrderedFood() {
        // tedad har qaza ke sefaresh dadeh shode ast
        Map<Food, Integer> foodCountMap = new HashMap<>();
        for (Order o : AdminPanel.orders) {
            if (o.getRestaurant().getId().equals(this.id)) {
                for (Map.Entry<Food, Integer> entry : o.getFoods().entrySet()) {
                    Food food = entry.getKey();
                    Integer quantity = entry.getValue();
                    foodCountMap.put(food, foodCountMap.getOrDefault(food, 0) + quantity);
                }
            }
        }

        Food mostOrderedFood = null;
        int maxCount = 0;
        for (Map.Entry<Food, Integer> entry : foodCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostOrderedFood = entry.getKey();
            }
        }

        return mostOrderedFood;
    }
}