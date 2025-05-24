package Users;

import Models.Restaurant;

import java.util.ArrayList;

public class RestaurantOwner extends User {
    public ArrayList<Restaurant> restaurants;

    public RestaurantOwner(int id, String name) {
        super(id, name);
        // TODO Auto-generated constructor stub
    }

    // getting Resturants
    public ArrayList<Restaurant> getRestaurants() {
        return this.restaurants;
    }
}