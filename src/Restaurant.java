import java.util.ArrayList;

public class Restaurant {
    /// ye ide ine ke ye aray joda baraye sefareshaye faal begirim, bad ke tahvil dade shodo polesh raft to hesab bezarimesh to order
    private int location, money, ratingCount;
    private double averageRating;
    private ArrayList<Integer> orders, comments, menu;
    private String foodType;

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    public String getFoodType() {
        return foodType;
    }
    public void setLocation(int location) {
        this.location = location;
    }
    public int getLocation() {
        return location;
    }
    public int getOrder(int index) {
        return orders.get(index);
    }
    public void addOrder(int orderID) {
        orders.add(orderID);
    }
    public ArrayList<Integer> getOrders() {
        return orders;
    }
    public int getMoney() {
        return money;
    }
    public void addMoney(int value) {
        money += value;
    }

    public ArrayList<Integer> getMenu() {
        return menu;
    }
    public void addMenuItem (int menuItemID) {
        menu.add(menuItemID);
    }

    public double getAverageRating() {
        return averageRating;
    }
    public int getRatingCount() {
        return ratingCount;
    }
    public void addRating(int rating) {
        averageRating = (averageRating * ratingCount + rating) / (ratingCount + 1);
        ratingCount++;
    }

    public void editRating(int oldRating, int newRating) {

    }

    public void addComment(int commentID) {
        comments.add(commentID);
    }
    public int getComment(int index) {
        return comments.get(index);
    }
    public ArrayList<Integer> getComments() {
        return comments;
    }
}