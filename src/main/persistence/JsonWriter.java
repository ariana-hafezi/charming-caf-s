package persistence;

import model.CafeLog;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Note: Code influenced by JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes JSON representation of cafe log to file.
public class JsonWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    // throws FileNotFoundException if destination file cannot be opened to be written in
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of given cafe log into the file
    public void write(CafeLog cafeLog) {
        JSONObject json = cafeLog.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string into the file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
