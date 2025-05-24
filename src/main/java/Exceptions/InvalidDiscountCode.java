package Exceptions;

import Models.Food;

public class InvalidDiscountCode extends RuntimeException {
    public InvalidDiscountCode(String message) {
        super(message);
    }
}
