package persistence;

import org.json.JSONObject;

// Note: Code influenced by JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents an object that can be written as a JSON object.
public interface Writable {

    // EFFECTS: returns this as a JSONObject
    JSONObject toJson();
}
