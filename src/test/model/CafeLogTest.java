package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import persistence.JsonReader;
import persistence.JsonTest;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the CafeLog class.
public class CafeLogTest extends JsonTest {
    private CafeLog testCafeLog;
    private Cafe testCafeA;
    private Cafe testCafeALowerCase;
    private Cafe testCafeADifferentLocation;
    private Cafe testCafeB;
    private Cafe testCafeC;
    private Cafe testCafeD;

    @BeforeEach
    void runBefore() {
        testCafeLog = new CafeLog();
        testCafeA = new Cafe("Matchstick", "Vancouver");
        testCafeALowerCase = new Cafe("matchstick", "Vancouver");
        testCafeADifferentLocation = new Cafe("Matchstick", "Calgary");
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

        testCafeLog.addCafe(testCafeA);
        result = testCafeLog.getCafes();
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));

        testCafeLog.addCafe(testCafeADifferentLocation);
        result = testCafeLog.getCafes();
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));

        testCafeLog.addCafe(testCafeALowerCase);
        result = testCafeLog.getCafes();
        assertEquals(2, result.size());
        assertTrue(result.contains(testCafeA));
        assertTrue(result.contains(testCafeALowerCase));

        testCafeLog.addCafe(testCafeB);

        result = testCafeLog.getCafes();
        assertEquals(3, result.size());
        assertTrue(result.contains(testCafeA));
        assertTrue(result.contains(testCafeB));
        assertTrue(result.contains(testCafeALowerCase));
    }

    @Test
    void testRemoveCafe() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);

        testCafeLog.removeCafe(testCafeB);
        List<Cafe> result = testCafeLog.getCafes();
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));

        testCafeLog.removeCafe(testCafeB);
        result = testCafeLog.getCafes();
        assertEquals(1, result.size());
        assertTrue(result.contains(testCafeA));

        testCafeLog.removeCafe(testCafeA);
        result = testCafeLog.getCafes();
        assertEquals(0, result.size());
    }

    @Test
    void testCafesByTagNoCafesWithTag() {
        testCafeA.addTag("study space");
        testCafeA.addTag("sleepy");
        testCafeB.addTag("affordable");

        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);

        List<Cafe> result = testCafeLog.cafesByTag("expensive");
        assertEquals(0, result.size());
    }

    @Test
    void testCafesByTag() {
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
    void testCafesByLocationNoCafesWithLocation() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);
        testCafeLog.addCafe(testCafeC);

        List<Cafe> result = testCafeLog.cafesByLocation("London");
        assertTrue(result.isEmpty());
    }

    @Test
    void testCafesByLocation() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);
        testCafeLog.addCafe(testCafeC);

        List<Cafe> result = testCafeLog.cafesByLocation("Vancouver");
        assertEquals(2, result.size());
        assertEquals(testCafeA, result.get(0));
        assertEquals(testCafeC, result.get(1));

        result = testCafeLog.cafesByLocation("chilliwack");
        assertEquals(1, result.size());
        assertEquals(testCafeB, result.get(0));
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

    @Test
    void testToJson() {
        testCafeA.addTag("cozy");
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);

        JSONObject json = testCafeLog.toJson();

        JsonReader reader = new JsonReader("testCafeLog");
        CafeLog cafeLog = reader.parseCafeLog(json);
        List<Cafe> readCafes = cafeLog.getCafes();

        Cafe readCafe = readCafes.get(0);
        checkCafe(testCafeA.getName(), testCafeA.getLocation(), testCafeA.getTags(), testCafeA.getItems(), readCafe);

        readCafe = readCafes.get(1);
        checkCafe(testCafeB.getName(), testCafeB.getLocation(), testCafeB.getTags(), testCafeB.getItems(), readCafe);

        assertEquals(2, readCafes.size());
    }

    @Test
    void testGetCafe() {
        testCafeLog.addCafe(testCafeA);
        testCafeLog.addCafe(testCafeB);
        assertEquals(testCafeA, testCafeLog.getCafe(testCafeA.getName()));
    }
}