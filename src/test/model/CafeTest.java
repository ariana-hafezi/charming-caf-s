package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests for the Cafe class.
class CafeTest extends JsonTest {
    private Cafe testCafe;
    private MenuItem testItemA;
    private MenuItem testItemB;
    private MenuItem testItemC;

    @BeforeEach
    void runBefore() {
        testCafe = new Cafe("Matchstick", "Vancouver");
        testItemA = new MenuItem("Iced Latte", 5, 500);
        testItemB = new MenuItem("Chocolate Croissant", 4, 450);
        testItemC = new MenuItem("Matcha Latte", 4, 605);
    }

    @Test
    void testConstructor() {
        assertEquals("Matchstick", testCafe.getName());
        assertEquals("Vancouver", testCafe.getLocation());
        assertEquals(0, testCafe.getTags().size());
        assertEquals(0, testCafe.getItems().size());
    }

    @Test
    void testAddTag() {
        testCafe.addTag("cozy");
        assertEquals(1, testCafe.getTags().size());
        assertTrue(testCafe.getTags().contains("cozy"));

        testCafe.addTag("study space");
        assertEquals(2, testCafe.getTags().size());
        assertTrue(testCafe.getTags().contains("cozy"));
        assertTrue(testCafe.getTags().contains("study space"));
    }

    @Test
    void testRemoveTag() {
        testCafe.addTag("inclusive");
        testCafe.addTag("chain");
        testCafe.removeTag("inclusive");

        assertEquals(1, testCafe.getTags().size());
        assertTrue(testCafe.getTags().contains("chain"));

        testCafe.removeTag("chain");
        assertEquals(0, testCafe.getTags().size());
    }

    @Test
    void testAddItem() {
        testCafe.addItem(testItemA);
        assertEquals(1, testCafe.getItems().size());
        assertTrue(testCafe.getItems().contains(testItemA));

        testCafe.addItem(testItemB);
        assertEquals(2, testCafe.getItems().size());
        assertTrue(testCafe.getItems().contains(testItemA));
        assertTrue(testCafe.getItems().contains(testItemB));
    }

    @Test
    void testRemoveItem() {
        testCafe.addItem(testItemA);
        testCafe.addItem(testItemB);

        testCafe.removeItem(testItemB);
        assertEquals(1, testCafe.getItems().size());
        assertTrue(testCafe.getItems().contains(testItemA));

        testCafe.removeItem(testItemA);
        assertEquals(0, testCafe.getItems().size());
    }

    @Test
    void testCalculateAverageRatingNoItems() {
        assertEquals(0, testCafe.calculateAverageRating());
    }

    @Test
    void testCalculateAverageRatingOneItem() {
        testCafe.addItem(testItemB);
        assertEquals(4.0, testCafe.calculateAverageRating());
    }

    @Test
    void testCalculateAverageRatingMultipleItems() {
        testCafe.addItem(testItemB);
        testCafe.addItem(testItemC);
        assertEquals(4.0, testCafe.calculateAverageRating());
    }

    @Test
    void testCalculateAverageRatingRounding() {
        testCafe.addItem(testItemA);
        testCafe.addItem(testItemB);
        testCafe.addItem(testItemC);
        assertEquals(4.3, testCafe.calculateAverageRating());
    }
}