import JavaFood.AdminPanel;

public class Main {
    public static void main(String[] args) {
        AdminPanel adminPanel = new AdminPanel();
        adminPanel.loadFromJSONFile("data2.json");
        // RestaurantIsClose Exception -> Restaurant is closed
    }
}
