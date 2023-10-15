package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests for the CafeLog class.
public class CafeLogTest {
    private CafeLog testCafeLog;
    private Cafe testCafeA;
    private Cafe testCafeB;
    private Cafe testCafeC;
    private Cafe testCafeD;

    @BeforeEach
    void runBefore() {
        testCafeLog = new CafeLog();
        testCafeA = new Cafe("Matchstick", "Vancouver");
        testCafeB = new Cafe("Smoking Gun Coffee", "Chilliwack");
        testCafeC = new Cafe("Blue Chip", "Vancouver");
        testCafeD = new Cafe("Butter Baked Goods", "Vancouver");

        MenuItem item1 = new MenuItem("1", 1, 700);
        MenuItem item2 = new MenuItem("2", 2, 700);
        MenuItem item3 = new MenuItem("3", 3, 700);
        MenuItem item4 = new MenuItem("4", 4, 700);

        testCafeA.addItem(item1);
        testCafeA.addItem(item2);
        testCafeB.addItem(item3);
        testCafeB.addItem(item4);
        testCafeC.addItem(item1);
        testCafeC.addItem(item2);
        testCafeD.addItem(item2);
        testCafeD.addItem(item3);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCafeLog.getCafes().size());
    }

    @Test
    void testAddCafe() {
        testCafeLog.addCafe(testCafeA);

        List<Cafe> result = testCafeLog.getCafes();
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));

        testCafeLog.addCafe(testCafeB);

        result = testCafeLog.getCafes();
        assertEquals(2, result.size());
        assertTrue(result.contains(testCafeA));
        assertTrue(result.contains(testCafeB));
    }

    @Test
    void testRemoveCafe() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);

        testCafeLog.removeCafe(testCafeB);

        List<Cafe> result = testCafeLog.getCafes();
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));

        testCafeLog.removeCafe(testCafeA);
        result = testCafeLog.getCafes();
        assertEquals(0, result.size());
    }

    @Test
    void testNoCafesWithTag() {
        testCafeA.addTag("study space");
        testCafeA.addTag("sleepy");
        testCafeB.addTag("affordable");

        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);

        List<Cafe> result = testCafeLog.cafesByTag("expensive");
        assertEquals(0, result.size());
    }

    @Test
    void testCafesWithTag() {
        testCafeA.addTag("busy");
        testCafeA.addTag("loud");
        testCafeB.addTag("comfortable");
        testCafeC.addTag("comfortable");
        testCafeC.addTag("unique");

        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);
        testCafeLog.addCafe(testCafeC);

        List<Cafe> result = testCafeLog.cafesByTag("comfortable");
        assertEquals(2, result.size());
        assertTrue(result.contains(testCafeB));
        assertTrue(result.contains(testCafeC));

        result = testCafeLog.cafesByTag("loud");
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));
    }

    @Test
    void testRankNoCafes() {
        assertEquals(0, testCafeLog.rankCafes().size());
    }

    @Test
    void testRankOneCafe() {
        testCafeLog.addCafe(testCafeA);
        assertEquals(1, testCafeLog.rankCafes().size());
    }

    @Test
    void testRankCafesAlreadyOrdered() {
        testCafeLog.addCafe(testCafeB);
        testCafeLog.addCafe(testCafeA);

        List<Cafe> result = testCafeLog.rankCafes();
        assertEquals(2, result.size());
        assertEquals(testCafeB, result.get(0));
        assertEquals(testCafeA, result.get(1));
    }

    @Test
    void testRankCafesOutOfOrder() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);

        List<Cafe> result = testCafeLog.rankCafes();
        assertEquals(2, result.size());
        assertEquals(testCafeB, result.get(0));
        assertEquals(testCafeA, result.get(1));
    }

    @Test
    void testRankCafesSameRank() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeC);
        testCafeLog.addCafe(testCafeB);

        List<Cafe> result = testCafeLog.rankCafes();
        assertEquals(3, result.size());
        assertEquals(testCafeB, result.get(0));
        assertEquals(testCafeA, result.get(1));
        assertEquals(testCafeC, result.get(2));
    }

    @Test
    void testRankCafesMany() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeC);
        testCafeLog.addCafe(testCafeB);
        testCafeLog.addCafe(testCafeD);

        List<Cafe> result = testCafeLog.rankCafes();
        assertEquals(4, result.size());
        assertEquals(testCafeB, result.get(0));
        assertEquals(testCafeD, result.get(1));
        assertEquals(testCafeA, result.get(2));
        assertEquals(testCafeC, result.get(3));
    }
}
