package model;

import java.util.HashSet;
import java.util.Set;

// Represents a menu item at a cafe with a name, rating, tags, and price in cents.
public class MenuItem {
    private final String name;
    private int rating;
    private int price;
    private Set<ItemTag> tags;

    // REQUIRES: name has non-zero length, rating is [1, 5], and price >= 0
    // EFFECTS: constructs a menu item with the given name, rating, price, and zero tags
    public MenuItem(String name, int rating, int price) {
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.tags = new HashSet<>();
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

    // MODIFIES: this
    // EFFECTS: adds the given tag to the item's tags, does not allow duplicates
    public void addTag(ItemTag tag) {
        tags.add(tag);
    }

    // REQUIRES: tag be in the item's tags
    // MODIFIES: this
    // EFFECTS: removes the given tag from the items tags
    public void removeTag(ItemTag tag) {
        tags.remove(tag);
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

    public Set<ItemTag> getTags() {
        return tags;
    }

    public enum ItemTag {
        WINTER, SPRING, SUMMER, FALL,
        ICED, HOT,
        SWEET, BITTER, SAVOURY,
        DARK, MEDIUM, LIGHT,
        CAFFEINATED, DECAF
    }
}
