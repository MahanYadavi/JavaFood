package Models;

import Exceptions.*;
import JavaFood.AdminPanel;
import Models.Discount.DiscountType;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Data
public class Order {
    private Integer id;
    // Food - quantity
    private final HashMap<Food, Integer> foods = new HashMap<>();
    private Double discountPrice = 0.0;
    private Integer userId;
    private Restaurant restaurant;
    private LocalDateTime orderDateTime;
    private Double totalPrice = 0.0;
    private ReceivingType receivingType;
    private Boolean isPaid = false, isScored = false;
    private Integer score;

    public Order(Integer id, Integer userId, Restaurant restaurant, ReceivingType receivingType) {
        this.id = id;
        this.userId = userId;
        this.restaurant = restaurant;
        this.receivingType = receivingType;
    }

    public enum ReceivingType {
        IN_PERSON,
        COURIER
    }

    public void addFoodToOrder(Integer foodId, int orderQuantity) {
        Food selectedFood = null;
        for (Food food : restaurant.getFoods().keySet()) {
            if (food.getId().equals(foodId)) {
                selectedFood = food;
                break;
            }
        }
        if (selectedFood == null) {
            throw new FoodNotFound();
        }

        Integer availableQuantity = restaurant.getFoods().get(selectedFood);
        if (availableQuantity < orderQuantity) {
            throw new FoodOutOfStock();
        }
        boolean isopen = restaurant.getIsOpen();
        if (isopen) {
            foods.put(selectedFood, foods.getOrDefault(selectedFood, 0) + orderQuantity);

            restaurant.getFoods().put(selectedFood, availableQuantity - orderQuantity);

            this.totalPrice += selectedFood.getPrice() * orderQuantity;
        } else {
            throw new RestaurantIsClose();
        }
    }

    public void addDiscount(String code) {
        boolean found = false;

        for (Discount d : AdminPanel.discounts) {
            if (d.getCode().equals(code)) {
                found = true;

                if (!d.getIsUsed()) {
                    if (d.getDiscountType() == Discount.DiscountType.AMOUNT) {
                        this.totalPrice -= d.getAmount();
                    } else if (d.getDiscountType() == Discount.DiscountType.PERCENTAGE) {
                        double result = (1 - (d.getPercentage() / 100.0)) * this.totalPrice;
                        this.totalPrice = result;
                    }
                    d.setIsUsed(true);
                } else {
                    throw new InvalidDiscountCode("This discount code is already used.");
                }
                break;
            }
        }

        if (!found) {
            throw new InvalidDiscountCode("Wrong Discount Code");
        }
    }

    public void pay(Double amount) {
        /*
         * Year/Month/Day Hour:Minute
         */
        String formattedDateTime = orderDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

        if (Double.compare(this.totalPrice, amount) == 0) {
            this.isPaid = true;
            AdminPanel.todayDate = this.orderDateTime.toLocalDate();
        } else {
            throw new PayException();
        }
    }

    public void scoreOrder(Integer score) {
        if (this.isScored) {
            throw new InvalidScore("Already scored");
        } else if (!this.isPaid) {
            throw new InvalidScore("Not paid yet");
        } else if (score < 1 || score > 5) {
            throw new InvalidScore("Invalid score");
        } else {
            this.score = score;
            isScored = true;
        }
    }
}