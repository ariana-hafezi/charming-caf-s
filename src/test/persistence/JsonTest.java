package persistence;

import model.Cafe;
import model.CafeLog;
import model.MenuItem;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Note: Code influenced by JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Methods for testing JSON classes.
public class JsonTest {
    protected CafeLog cafeLog;

    @BeforeEach
    protected void setUp() {
        cafeLog = new CafeLog();
    }

    public void checkCafe(String name, String location, Set<String> tags, List<MenuItem> items, Cafe cafe) {
        assertEquals(name, cafe.getName());
        assertEquals(location, cafe.getLocation());
        assertEquals(tags, cafe.getTags());
        checkItems(items, cafe);
    }


    private void checkItems(List<MenuItem> items, Cafe cafe) {
        int i = 0;
        List<MenuItem> cafeItems = cafe.getItems();
        for (MenuItem item : items) {
            MenuItem cafeItem = cafeItems.get(i);
            assertEquals(item.getName(), cafeItem.getName());
            assertEquals(item.getRating(), cafeItem.getRating());
            assertEquals(item.getPrice(), cafeItem.getPrice());
            i++;
        }
    }
}
