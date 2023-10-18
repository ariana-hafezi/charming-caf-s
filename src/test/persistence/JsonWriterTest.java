package persistence;

import model.Cafe;
import model.MenuItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


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
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCafeLog.json");
            cafeLog = reader.read();
            List<Cafe> cafes = cafeLog.getCafes();
            assertEquals(0, cafes.size());

        } catch (IOException e) {
            fail("unexpected IOException");
        }
    }

    @Test
    void testWriterCafeLog() {
        try {
            Cafe match = new Cafe("matchstick", "Vancouver");
            match.addTag("cozy");
            match.addTag("unique");
            MenuItem itemMatchstick = new MenuItem("iced latte", 4, 500);
            match.addItem(itemMatchstick);

            Cafe butter = new Cafe("butter baked goods", "Vancouver");
            butter.addTag("quiet");
            MenuItem itemButter = new MenuItem("raspberry chocolate cupcake", 4, 450);
            butter.addItem(itemButter);

            cafeLog.addCafe(match);
            cafeLog.addCafe(butter);

            JsonWriter writer = new JsonWriter("./data/testWriterCafeLog.json");
            writer.open();
            writer.write(cafeLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCafeLog.json");
            cafeLog = reader.read();
            List<Cafe> cafes = cafeLog.getCafes();
            assertEquals(2, cafes.size());
            Cafe readMatch = cafes.get(0);
            Cafe readButter = cafes.get(1);

            checkCafe(match.getName(), match.getLocation(), match.getTags(), match.getItems(), readMatch);
            checkCafe(butter.getName(), butter.getLocation(), butter.getTags(), butter.getItems(), readButter);

        } catch (IOException e) {
            fail("unexpected IOException");
        }
    }
}