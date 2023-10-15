package persistence;

import model.Cafe;
import model.MenuItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Note: modelled after JsonSerializationDemo's JsonReaderTest class.
// Tests for the JSONReader class.
public class JsonReaderTest extends JsonTest {

    @Test
    void testNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist.json");
        try {
            cafeLog = reader.read();
            fail("IOException expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyCafeLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCafeLog.json");
        try {
            cafeLog = reader.read();
            List<Cafe> cafes = cafeLog.getCafes();
            assertEquals(0, cafes.size());

        } catch (IOException e) {
            fail("should have been able to read file");
        }
    }

    @Test
    void testCafeLog() {
        JsonReader reader = new JsonReader("./data/testReaderCafeLog.json");
        try {
            cafeLog = reader.read();
            List<Cafe> cafes = cafeLog.getCafes();
            assertEquals(2, cafes.size());

            Set<String> tags = new HashSet<>();
            tags.add("cozy");
            tags.add("unique");
            List<MenuItem> items = new ArrayList<>();
            MenuItem item1 = new MenuItem("iced latte", 4, 500);
            items.add(item1);

            checkCafe("matchstick", "Vancouver", tags, items, cafes.get(0));

            tags.clear();
            items.clear();
            item1 = new MenuItem("iced mocha", 5, 650);
            MenuItem item2 = new MenuItem("spiced oat latte", 4, 450);
            items.add(item1);
            items.add(item2);

            checkCafe("smoking gun coffee", "Chilliwack", tags, items, cafes.get(1));

        } catch (IOException e) {
            fail("should have been able to read file");
        }
    }
}
