package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Represents a log of cafes the user has been to.
public class CafeLog implements Writable {
    private final List<Cafe> cafes;

    // EFFECTS: constructs a new cafe log with no cafes
    public CafeLog() {
        this.cafes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given cafe to the log
    public void addCafe(Cafe cafe) {
        if (!cafes.contains(cafe)) {
            cafes.add(cafe);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given cafe from the log
    public void removeCafe(Cafe cafe) {
        cafes.remove(cafe);
    }

    // EFFECTS: returns the cafes in the log that have been tagged with the given tag
    public List<Cafe> cafesByTag(String tag) {
        List<Cafe> result = new ArrayList<>();

        for (Cafe cafe : cafes) {
            Set<String> tags = cafe.getTags();
            if (tags.contains(tag)) {
                result.add(cafe);
            }
        }
        return result;
    }

    // EFFECTS: returns the cafes in the log that have the given location
    public List<Cafe> cafesByLocation(String location) {
        List<Cafe> result = new ArrayList<>();
        location = location.toLowerCase();

        for (Cafe cafe : cafes) {
            String cafeLocation = cafe.getLocation();
            cafeLocation = cafeLocation.toLowerCase();
            if (location.equals(cafeLocation)) {
                result.add(cafe);
            }
        }
        return result;
    }

    // EFFECTS: returns a list of the cafes in the log in order of highest to lowest average item rating
    public List<Cafe> rankCafes() {
        List<Cafe> sortCafes = new ArrayList<>(cafes);

        sortCafes.sort(new AverageRatingComparator());

        return sortCafes;
    }

    @Override
    // EFFECTS: returns this cafe log as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cafes", cafesToJson());
        return json;
    }


    // EFFECTS: returns the cafes in the cafe log as a JSONArray
    private JSONArray cafesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cafe cafe : cafes) {
            jsonArray.put(cafe.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the cafe with the given name
    public Cafe getCafe(String name) {
        Cafe namedCafe = null;
        for (Cafe cafe : cafes) {
            if (cafe.getName().equals(name)) {
                namedCafe = cafe;
            }
        }
        return namedCafe;
    }

    // getters:

    public List<Cafe> getCafes() {
        return cafes;
    }
}