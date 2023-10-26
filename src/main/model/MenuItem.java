package model;

import exceptions.PriceException;
import exceptions.RatingException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a menu item at a cafe with a name, rating, and price in cents.
public class MenuItem implements Writable {
    private final String name;
    private int rating;
    private int price;


    // REQUIRES: name be non-zero length
    // EFFECTS: constructs a menu item with the given name, rating, and price
    public MenuItem(String name, int rating, int price) throws RatingException, PriceException {
        setRating(rating);
        setPrice(price);
        this.name = name;

    }

    // MODIFIES: this
    // EFFECTS: sets the rating of the item to the given rating
    public void setRating(int rating) throws RatingException {
        if (rating < 1 || rating > 5) {
            throw new RatingException("\nsorry, that's an invalid rating!");
        }
        this.rating = rating;
    }

    // MODIFIES: this
    // EFFECTS: sets the price of the item to the given price in cents
    public void setPrice(int price) throws PriceException {
        if (price < 0) {
            throw new PriceException("\nsorry, that's an invalid price!");
        }
        this.price = price;
    }

    @Override
    // EFFECTS: returns this item as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("rating", rating);
        json.put("price", price);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // getters:

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }
}