package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Double.parseDouble;

// Represents a cafe with a name, location, tags, and menu items a customer has tried.
public class Cafe implements Writable {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".0");
    private final String name;
    private final String location;
    private final Set<String> tags;
    private final List<MenuItem> items;

    // REQUIRES: name and location have non-zero length
    // EFFECTS: constructs a cafe with the given name and location, no tags, and no items
    public Cafe(String name, String location) {
        this.name = name;
        this.location = location;
        this.tags = new HashSet<>();
        this.items = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given tag to the cafe's tags, doesn't allow duplicates
    public void addTag(String tag) {
        tags.add(tag);
    }

    // REQUIRES: tag be in the cafe's tags
    // MODIFIES: this
    // EFFECTS: removes the given tag from the cafe's tags
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    // MODIFIES: this
    // EFFECTS: adds the given item to the list of items tried at the cafe
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // REQUIRES: item be in the cafe's list of items
    // MODIFIES: this
    // EFFECTS: removes the given item from the list of items tried at the cafe
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    // EFFECTS: returns the average rating of every item rated at a cafe to one decimal place
    public double calculateAverageRating() {
        int numItems = 0;
        int total = 0;
        double average;

        for (MenuItem item : items) {
            total += item.getRating();
            numItems++;
        }

        if (numItems == 0) {
            return 0;
        } else {
            average = (double) total / numItems;
        }

        return parseDouble(DECIMAL_FORMAT.format(average));
    }

    @Override
    // EFFECTS: returns this cafe as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("location", location);
        json.put("tags", tagsToJson());
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: returns the cafe's tags as a JSONArray
    private JSONArray tagsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String tag :  tags) {
            jsonArray.put(tag);
        }

        return jsonArray;
    }

    // EFFECTS: returns the items for this cafe as a JSONArray
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MenuItem item : items) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }

    // getters:

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}