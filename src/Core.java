import java.util.ArrayList;
import java.util.HashMap;

public class Core {
    Map map = new Map();
    int accounts, restaurants, foods, orders, comments;
    HashMap<String, ArrayList<Integer>> restaurantNames = new HashMap<>(), foodNames = new HashMap<>();
    int loggedInAccount = -1, loggedInUser = -1, loggedInAdmin = -1, loggedInDeliveryman = -1;
    int selectedRestaurant = -1, selectedFood = -1;
    Core() {
        //TODO: get the number of accounts, restaurants, etc. from the files
        for (int restaurantID = 0; restaurantID < restaurants; restaurantID++) {
            restaurantNames.putIfAbsent(Restaurant.getRestaurant(restaurantID).getName(), new ArrayList<>());
            restaurantNames.get(Restaurant.getRestaurant(restaurantID).getName()).add(restaurantID);
        }
        for (int foodID = 0; foodID < foods; foodID++) {
            foodNames.putIfAbsent(Food.getFood(foodID).getName(), new ArrayList<>());
            foodNames.get(Food.getFood(foodID).getName()).add(foodID);
        }
    }
    public void login(String userName, String password) {
        if (loggedInAccount != -1) {
            System.out.println("You are already logged in.");
            return;
        }
        for (int accountID = 0; accountID < accounts; accountID++) {
            if (userName.equals(Account.getAccount(accountID).getUsername())) {
                if (password.equals(Account.getAccount(accountID).getPassword())) {
                    if (Account.getAccount(accountID).getType().equals("User"))
                        loggedInUser = Account.getAccount(accountID).getId();
                    else if (Account.getAccount(accountID).getType().equals("Admin"))
                        loggedInUser = Account.getAccount(accountID).getId();
                    else
                        loggedInDeliveryman = Account.getAccount(accountID).getId();
                    loggedInAccount = Account.getAccount(accountID).getId();
                    System.out.println("Logged in successfully.");
                    return;
                }
                System.out.println("Incorrect password!");
                return;
            }
        }
        System.out.println("There is no account with this username!");
    }
    public void logout() {
        if (loggedInAccount == -1) {
            System.out.println("No one has logged in!");
        } else {
            if (loggedInDeliveryman != -1) {
                System.out.println("Logged out successfully.");
                loggedInDeliveryman = -1;
            } else if (loggedInUser != -1) {
                System.out.println("Logged out successfully.");
                loggedInAdmin = -1;
            } else {
                System.out.println("Logged out successfully.");
                loggedInUser = -1;
            }
        }
    }
    public void addAccount(String type, String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        Account account = new Account(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts++);
        account.setType(type);
        Account.saveAccount(account.getId(), account);
        if (account.getType().equals("User")) {
            User user = new User(username, password, recoveryQuestion, recoveryQuestionAnswer, account.getId());
            User.saveUser(user.getId(), user);
        } else if (account.getType().equals("Admin")) {
            Admin admin = new Admin(username, password, recoveryQuestion, recoveryQuestionAnswer, account.getId());
            Admin.saveAdmin(admin.getId(), admin);
        } else {
            Deliveryman deliveryman = new Deliveryman(username, password, recoveryQuestion, recoveryQuestionAnswer, account.getId());
            Deliveryman.saveDeliveryman(deliveryman.getId(), deliveryman);
        }
    }
    public void showLocation() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        System.out.println(Restaurant.getRestaurant(selectedRestaurant).getLocation());
    }
    public void editLocation(int nodeID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        Restaurant.getRestaurant(selectedRestaurant).setLocation(nodeID);
        System.out.println("The location has been updated successfully.");
    }
    public void showFoodType() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        for (String s : Restaurant.getRestaurant(selectedRestaurant).getFoodType())
            System.out.println(s);
    }
    public int addFoodType(String type, boolean forSure) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        } else if (Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(type)) {
            System.out.println("The restaurant already contains this food type!");
            return 3;
        } else if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO ADD THIS FOOD TYPE TO YOUR RESTAURANT?");
            return 4;
        } else {
            Restaurant.getRestaurant(selectedRestaurant).addFoodType(type);
            System.out.println("Food type added successfully.");
            return 0;
        }
    }
    public int removeFoodType(String type, boolean forSure) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(type)) {
            System.out.println("The restaurant does not contain this food type!");
            return 3;
        } else if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO REMOVE THIS FOOD TYPE FROM YOUR RESTAURANT?");
            return 4;
        } else {
            Restaurant.getRestaurant(selectedRestaurant).removeFoodType(type);
            Restaurant.getRestaurant(selectedRestaurant).deleteMenu();
            return 0;
        }
    }
    public int editFoodType(String typeToRemove, String typeToAdd, boolean forSure) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(typeToRemove)) {
            System.out.println("The restaurant does not contain the given food type!");
            return 3;
        } else if (Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(typeToAdd)) {
            System.out.println("The restaurant already contains the given food type!");
            return 4;
        } else if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO EDIT THIS FOOD TYPE IN YOUR RESTAURANT?");
            return 5;
        } else {
            Restaurant.getRestaurant(selectedRestaurant).removeFoodType(typeToRemove);
            Restaurant.getRestaurant(selectedRestaurant).addFoodType(typeToAdd);
            Restaurant.getRestaurant(selectedRestaurant).deleteMenu();
            return 0;
        }
    }
    public void showMenu() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        for (int i : Restaurant.getRestaurant(selectedRestaurant).getMenu())
            System.out.println("ID: " + i + ", NAME: " + Food.getFood(i).getName() + ", PRICE: " + Food.getFood(i).getPrice() + ", DISCOUNT: " + Food.getFood(i).getDiscount() + ", RATING: " + Food.getFood(i).getAverageRating() + ", IS ACTIVE: " + Food.getFood(i).getActive());
    }
    public void editFoodPrice(int foodID, int price) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setPrice(price);
            System.out.println("Information updated successfully.");
        }
    }
    public void editFoodName(int foodID, String newName) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setName(newName);
            System.out.println("Information updated successfully.");
        }
    }
    public void addFood(String foodName, int foodPrice) {

    }
    public void activateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setActive(true);
            System.out.println("Information updated successfully.");
        }
    }
    public void deactivateFood(int foodID) {

    }
    public void discountFood(int foodID, int discountPercentage, int timestamp) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setDiscount(discountPercentage, timestamp);
            System.out.println("Information updated successfully.");
        }
    }
    public void selectFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            selectedFood = foodID;
            System.out.println("Food selected successfully.");
        }
    }
    public void displayRating() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            System.out.println("AVERAGE RATING: " + Food.getFood(selectedFood).getAverageRating());
        }
    }
    public void displayComments() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            for (int i : Food.getFood(selectedFood).getComments())
                System.out.println("ID: " + i + ", USER: " + Account.getAccount(Comment.getComment(i).getCommenter()).getUsername() + ", CONTENT: " + Comment.getComment(i).getContent() + ", RESPONSE: " + Comment.getComment(i).getAnswer());
        }
    }
    public void addResponse(int commentID, String message) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (!Food.getFood(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        } else if (Comment.getComment(commentID).getAnswer() != null) {
            System.out.println("This comment has already been answered!");
        } else {
            Comment.getComment(commentID).setAnswer(message);
            System.out.println("Response added successfully.");
        }
    }
    public void editResponse(int commentID, String message) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (!Food.getFood(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        } else if (Comment.getComment(commentID).getAnswer() == null) {
            System.out.println("This comment has never been answered!");
        } else {
            Comment.getComment(commentID).setAnswer(message);
            System.out.println("Response edited successfully.");
        }
    }
    public void deselectFood() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            selectedFood = -1;
            System.out.println("Food unselected successfully.");
        }
    }
    public void deselectRestaurant() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else {
            selectedRestaurant = -1;
            System.out.println("Restaurant unselected successfully.");
        }
    }
    public void displayActiveOrders() {

    }
    public void editOrderStatus(int OrderId, String Status) {

    }
    // time handel shavad
    public void editOrderDeliveryTime(int OrderTime, int time) {

    }
    public void showOrderHistory() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else {
            for (int i : Restaurant.getRestaurant(selectedRestaurant).getOrders())
                System.out.println("ID: " + i + ", STATUS: " + Order.getOrder(i).getStatus() + ", DELIVERYMAN: " + Order.getOrder(i).getDeliveryman() + ", USER: " + Order.getOrder(i).getUser().getUsername() + ", PRICE: " + Order.getOrder(i).getPrice());
        }
    }
    /// modirate sefaresh :::::::
    public void selectRestaurant(int restaurantID) {
        if (loggedInAdmin == -1 && loggedInUser == -1) {
            System.out.println("No admin has logged in!");
        } else {
            selectedRestaurant = restaurantID;
            System.out.println("Restaurant successfully selected.");
        }
    }
    public void searchRestaurantName(String name) {

    }
    public void searchFoodName(String name) {

    }
    public void addComment(String content) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (Food.getFood(selectedFood).getRestaurant().getAdmin() == loggedInAdmin) {
            System.out.println("You cannot write comments on your own products!");
        } else {
            Comment c = new Comment();
            c.setCommenter(loggedInAccount);
            c.setContent(content);
            c.setId(comments++);
            Comment.saveComment(c.getId(), c);
            Food.getFood(selectedFood).getComments().add(c.getId());
            System.out.println("Comment added successfully.");
        }
    }
    public void editComment(int commentID, String content) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (Comment.getComment(commentID).getCommenter() != loggedInAccount) {
            System.out.println("You can only edit your own comments!");
        } else {
            Comment.getComment(commentID).setContent(content);
            System.out.println("Comment edited successfully.");
        }
    }
    public void submitRating(int rating) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (Food.getFood(selectedFood).getRaters().contains(loggedInAccount)) {
            System.out.println("You have already added a rating for this food!");
        } else {
            Food.getFood(selectedFood).addRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void editRating(int rating) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (!Food.getFood(selectedFood).getRaters().contains(loggedInAccount)) {
            System.out.println("You have not submitted a rating for this food!");
        } else {
            Food.getFood(selectedFood).addRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void addToCart(int count) {

    }
    public void selectOrder(int id) {

    }
    public void showCart() {

    }
    public void confirmOrder() {

    }
    public void showEstimatedDeliveryTime() {

    }
    public void chargeAccount(int value) {

    }
    public void displayAccountBalance() {

    }
    public void showPath() {

    }
    public void suggestFood() {

    }
}
