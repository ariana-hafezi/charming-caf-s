package model;

// Represents a menu item at a cafe with a name, rating, and price in cents.
public class MenuItem {
    private final String name;
    private int rating;
    private int price;

    // REQUIRES: name has non-zero length, rating is [1, 5], and price >= 0
    // EFFECTS: constructs a menu item with the given name, rating, and price
    public MenuItem(String name, int rating, int price) {
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    // REQUIRES: rating in [1, 5]
    // MODIFIES: this
    // EFFECTS: sets the rating of the item to the given rating
    public void setRating(int rating) {
        this.rating = rating;
    }

    // REQUIRES: price >= 0
    // MODIFIES: this
    // EFFECTS: sets the price of the item to the given price in cents
    public void setPrice(int price) {
        this.price = price;
    }

    // getters:

    public String getName() {
        return name; //stub
    }

    public int getRating() {
        return rating; //stub
    }

    public int getPrice() {
        return price; //stub
    }
}