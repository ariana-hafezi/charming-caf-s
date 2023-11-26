package model;

import exceptions.PriceException;
import exceptions.RatingException;
import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;
import java.util.Objects;

import static java.lang.Double.parseDouble;

// Represents a menu item at a cafe with a name, rating, and price in dollars.
public class MenuItem implements Writable {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".00");
    private final String name;
    private int rating;
    private double price;


    // REQUIRES: name be non-zero length
    // EFFECTS: constructs a menu item with the given name, rating, and price
    public MenuItem(String name, int rating, double price) throws RatingException, PriceException {
        this.name = name;
        setRating(rating);
        setPrice(price);
    }

    // MODIFIES: this
    // EFFECTS: sets the rating of the item to the given rating
    public void setRating(int rating) throws RatingException {
        if (rating < 1 || rating > 5) {
            throw new RatingException("\nsorry, that's an invalid rating!");
        }
        this.rating = rating;
        EventLog.getInstance().logEvent(new Event(name + "'s rating set to " + rating + " stars"));
    }

    // MODIFIES: this
    // EFFECTS: sets the price of the item to the given price in dollars
    public void setPrice(double price) throws PriceException {
        if (price < 0) {
            throw new PriceException("\nsorry, that's an invalid price!");
        }
        this.price = price;
        price = parseDouble(DECIMAL_FORMAT.format(price));
        EventLog.getInstance().logEvent(new Event(name + "'s price set to $" + price));
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

    public double getPrice() {
        return price;
    }
}