package model;

import exceptions.PriceException;
import exceptions.RatingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the MenuItem class.
public class MenuItemTest {
    private MenuItem testItem;
    private MenuItem testItemSameName;

    @BeforeEach
    void runBefore() {
        testItem = new MenuItem("iced latte", 5, 600);
        testItemSameName = new MenuItem("iced latte", 4, 700);
    }

    @Test
    void testConstructor() {
        assertEquals("iced latte", testItem.getName());
        assertEquals(5, testItem.getRating());
        assertEquals(600, testItem.getPrice());
    }

    @Test
    void testSetRating() {
        try {
            testItem.setRating(4);
            assertEquals(4, testItem.getRating());
        } catch (RatingException e) {
            fail("unexpected RatingException");
        }

        try {
            testItem.setRating(1);
            assertEquals(1, testItem.getRating());
        } catch (RatingException e) {
            fail("unexpected RatingException");
        }

        try {
            testItem.setRating(5);
            assertEquals(5, testItem.getRating());
        } catch (RatingException e) {
            fail("unexpected RatingException");
        }

        try {
            testItem.setRating(6);
            assertEquals(5, testItem.getRating());
            fail("expected RatingException");
        } catch (RatingException e) {
            // expected
        }

        try {
            testItem.setRating(0);
            assertEquals(5, testItem.getRating());
            fail("expected RatingException");
        } catch (RatingException e) {
            // expected
        }
    }

    @Test
    void testSetPrice() {
        try {
            testItem.setPrice(799);
            assertEquals(799, testItem.getPrice());
        } catch (PriceException e) {
            fail("unexpected PriceException");
        }

        try {
            testItem.setPrice(0);
            assertEquals(0, testItem.getPrice());
        } catch (PriceException e) {
            fail("unexpected PriceException");
        }

        try {
            testItem.setPrice(-1);
            assertEquals(0, testItem.getPrice());
            fail("expected PriceException");
        } catch (PriceException e) {
            // expected
        }
    }

    @Test
    void testEquals() {
        assertFalse(testItem.equals("iced latte"));
        assertEquals(testItem, testItemSameName);
        assertFalse(testItem.equals(null));
    }

    @Test
    void testHashCode() {
        assertEquals(testItem.hashCode(), testItemSameName.hashCode());
    }
}