package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.MenuItem.ItemTag.*;
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
        assertEquals(0, testItem.getTags().size());
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

    @Test
    void testAddTag() {
        testItem.addTag(WINTER);
        assertEquals(1, testItem.getTags().size());
        assertTrue(testItem.getTags().contains(WINTER));

        testItem.addTag(SWEET);
        assertEquals(2, testItem.getTags().size());
        assertTrue(testItem.getTags().contains(WINTER));
        assertTrue(testItem.getTags().contains(SWEET));
    }

    @Test
    void testRemoveTag() {
        testItem.addTag(WINTER);
        testItem.addTag(SWEET);

        testItem.removeTag(WINTER);
        assertEquals(1, testItem.getTags().size());
        assertTrue(testItem.getTags().contains(SWEET));

        testItem.removeTag(SWEET);
        assertEquals(0, testItem.getTags().size());
    }
}
