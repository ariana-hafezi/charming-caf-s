package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Cafe.CafeTag.*;
import static model.MenuItem.ItemTag.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CafeTest {
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
        testCafe.addTag(COZY);
        assertEquals(1, testCafe.getTags().size());
        assertTrue(testCafe.getTags().contains(COZY));

        testCafe.addTag(STUDY_SPACE);
        assertEquals(2, testCafe.getTags().size());
        assertTrue(testCafe.getTags().contains(COZY));
        assertTrue(testCafe.getTags().contains(STUDY_SPACE));
    }

    @Test
    void testRemoveTag() {
        testCafe.addTag(QUEER);
        testCafe.addTag(WIFI);
        testCafe.removeTag(QUEER);

        assertEquals(1, testCafe.getTags().size());
        assertTrue(testCafe.getTags().contains(WIFI));

        testCafe.removeTag(WIFI);
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
    void testNoItemsWithTag() {
        testItemA.addTag(ICED);
        testItemA.addTag(BITTER);
        testItemB.addTag(SWEET);

        testCafe.addItem(testItemA);
        testCafe.addItem(testItemB);

        List<MenuItem> result = testCafe.itemsByTag(SAVOURY);
        assertEquals(0, result.size());
    }

    @Test
    void testItemsWithTag() {
        testItemA.addTag(ICED);
        testItemA.addTag(BITTER);
        testItemB.addTag(SWEET);
        testItemC.addTag(ICED);
        testItemC.addTag(SWEET);

        testCafe.addItem(testItemA);
        testCafe.addItem(testItemB);
        testCafe.addItem(testItemC);

        List<MenuItem> result = testCafe.itemsByTag(SWEET);
        assertEquals(2, result.size());
        assertTrue(result.contains(testItemB));
        assertTrue(result.contains(testItemC));

        result = testCafe.itemsByTag(ICED);
        assertEquals(2, result.size());
        assertTrue(result.contains(testItemA));
        assertTrue(result.contains(testItemC));
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