package Models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Discount {
    private int id;
    private String code;
    private Integer userId;
    private DiscountType discountType;
    private LocalDate expireDate;
    private Double amount;
    private Integer percentage;
    private Boolean isUsed = false;

    public enum DiscountType {
        PERCENTAGE,
        AMOUNT
    }

    public Discount(Integer id, String code, Double amount, LocalDate expireDate, Integer userId) {
        if (expireDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expire date cannot be in the past.");
        }

        this.id = id;
        this.code = code;
        this.amount = amount != null ? amount : 0.0; // مقدار پیش‌فرض اگر null بود
        this.expireDate = expireDate;
        this.userId = userId;
        this.discountType = DiscountType.AMOUNT;
        this.isUsed = false; // مقدار پیش‌فرض false
    }

    public Discount(Integer id, String code, Integer percentage, LocalDate expireDate, Integer userId) {
        if (expireDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expire date cannot be in the past.");
        }

        this.id = id;
        this.code = code;
        this.percentage = percentage != null ? percentage : 0; // مقدار پیش‌فرض اگر null بود
        this.expireDate = expireDate;
        this.userId = userId;
        this.discountType = DiscountType.PERCENTAGE;
        this.isUsed = false; // مقدار پیش‌فرض false
    }
}
