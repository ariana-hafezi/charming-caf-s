package persistence;

import model.Cafe;
import model.CafeLog;
import model.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

// Note: Code influenced by JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads cafe log from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads and returns the cafe log from the file
    // throws an IOException if an error occurs when trying to read the cafe log
    public CafeLog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCafeLog(jsonObject);
    }

    // EFFECTS: reads source file and returns it as a string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = lines(get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses cafe log from JSON object and returns it
    public CafeLog parseCafeLog(JSONObject jsonObject) {
        CafeLog cafeLog = new CafeLog();
        addCafes(cafeLog, jsonObject);
        return cafeLog;
    }

    // MODIFIES: cafeLog
    // EFFECTS: parses cafes from JSON object and adds them to the log
    private void addCafes(CafeLog cafeLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cafes");
        for (Object json : jsonArray) {
            JSONObject cafe = (JSONObject) json;
            addCafe(cafeLog, cafe);
        }
    }

    // MODIFIES: cafeLog
    // EFFECTS: parses cafe from JSON object and adds it to the log
    private void addCafe(CafeLog cafeLog, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        Cafe cafe = new Cafe(name, location);
        addItems(cafe, jsonObject);
        addTags(cafe, jsonObject);
        cafeLog.addCafe(cafe);
    }

    // MODIFIES: cafe
    // EFFECTS: parses items from JSON object and adds them to the cafe
    private void addItems(Cafe cafe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject item = (JSONObject) json;
            addItem(cafe, item);
        }
    }

    // MODIFIES: cafe
    // EFFECTS: parses item from JSON object and adds it to the cafe
    private void addItem(Cafe cafe, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        int price = jsonObject.getInt("price");
        MenuItem item = new MenuItem(name, rating, price);
        cafe.addItem(item);
    }

    // MODIFIES: cafe
    // EFFECTS: parses tags from JSON object and adds them to the cafe
    private void addTags(Cafe cafe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tags");
        Set<String> tags = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tags.add(jsonArray.getString(i));
        }
        for (String tag : tags) {
            cafe.addTag(tag);
        }
    }
}
