package Models;

import lombok.Data;

@Data
public class Food {
    private Integer id;
    private String name;
    private Double price;

    public Food(Integer id, String name, Double price) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}