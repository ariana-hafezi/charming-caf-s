package persistence;

import model.Cafe;
import model.MenuItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Note: modelled after JsonSerializationDemo's JsonWriterTest class.
// Tests for the JSONWriter class.
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCafeLog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCafeLog.json");
            writer.open();
            writer.write(cafeLog);

            JsonReader reader = new JsonReader("./data/testWriterEmptyCafeLog.json");
            List<Cafe> cafes = cafeLog.getCafes();
            assertEquals(0, cafes.size());

        } catch (IOException e) {
            fail("unexpected IOException");
        }
    }

    @Test
    void testWriterCafeLog() {
        try {
            Cafe matchstick = new Cafe("matchstick", "Vancouver");
            matchstick.addTag("cozy");
            matchstick.addTag("unique");
            MenuItem itemMatchstick = new MenuItem("iced latte", 4, 500);
            matchstick.addItem(itemMatchstick);

            Cafe butter = new Cafe("butter baked goods", "Vancouver");
            butter.addTag("quiet");
            MenuItem itemButter = new MenuItem("raspberry chocolate cupcake", 4, 450);
            butter.addItem(itemButter);

            cafeLog.addCafe(matchstick);
            cafeLog.addCafe(butter);

            JsonWriter writer = new JsonWriter("./data/testWriterCafeLog.json");
            writer.open();
            writer.write(cafeLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCafeLog.json");
            cafeLog = reader.read();
            List<Cafe> cafes = cafeLog.getCafes();
            assertEquals(2, cafes.size());
            matchstick = cafes.get(0);
            butter = cafes.get(1);

            Set<String> tags = new HashSet<>();
            tags.add("cozy");
            tags.add("unique");
            List<MenuItem> items = new ArrayList<>();
            items.add(itemMatchstick);
            checkCafe("matchstick", "Vancouver", tags, items, matchstick);

            tags.clear();
            tags.add("quiet");
            items.clear();
            items.add(itemButter);
            checkCafe("butter baked goods", "Vancouver", tags, items, butter);

        } catch (IOException e) {
            fail("unexpected IOException");
        }
    }
}
