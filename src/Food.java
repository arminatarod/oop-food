import java.util.HashSet;

public class Food {
    private int id, price, discount, discountTimestamp;
    private Restaurant restaurant;
    private double averageRating;
    private HashSet<Integer> raters = new HashSet<>();
    // baraye bakhsh graphic khobe aksesham dashte bashim !? ye string url
    private String name;
    private boolean activeDiscount, isActive;
    private HashSet<Integer> comments = new HashSet<>();

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public void setDiscount(int discount, int timestamp) {
        this.discount = discount;
        this.discountTimestamp = timestamp;
    }
    public int getDiscount() {
        return discount;
    }
    public int getDiscountTimestamp() {
        return discountTimestamp;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public boolean getActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public void setActiveDiscount(boolean activeDiscount) {
        this.activeDiscount = activeDiscount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public double getAverageRating() {
        return averageRating;
    }
    public int getRatingCount() {
        return raters.size();
    }
    public void addRating(int raterID, int rating) {
        averageRating = (averageRating * raters.size() + rating) / (raters.size() + 1);
        raters.add(raterID);
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public void editRating(int oldRating, int newRating) {
        averageRating = (averageRating * raters.size() - oldRating + newRating) / raters.size();
    }
    public HashSet<Integer> getComments() {
        return comments;
    }
    public HashSet<Integer> getRaters() {
        return raters;
    }
    static public Food getFood(int ID) {
        Food result = new Food();
        //TODO: get food from file
        return result;
    }
    static public void saveFood(int ID, Food food) {
        //TODO: save food to file
    }
}
