package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests for the MenuItem class.
public class MenuItemTest {
    private MenuItem testItem;

    @BeforeEach
    void runBefore() {
        testItem = new MenuItem("iced latte", 5, 600);
    }

    @Test
    void testConstructor() {
        assertEquals("iced latte", testItem.getName());
        assertEquals(5, testItem.getRating());
        assertEquals(600, testItem.getPrice());
    }

    @Test
    void testSetRating() {
        testItem.setRating(4);
        assertEquals(4, testItem.getRating());

        testItem.setRating(1);
        assertEquals(1, testItem.getRating());
    }

    @Test
    void testSetPrice() {
        testItem.setPrice(799);
        assertEquals(799, testItem.getPrice());
    }
}
