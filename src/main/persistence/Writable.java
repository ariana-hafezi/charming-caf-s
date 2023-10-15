package persistence;

import org.json.JSONObject;

// Note: modelled after JsonSerializationDemo's Writable interface.
// Represents an object that can be written as a JSON object.
public interface Writable {

    // EFFECTS: returns this as a JSONObject
    JSONObject toJson();
}
