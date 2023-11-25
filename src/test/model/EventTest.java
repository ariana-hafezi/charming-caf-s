package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Note: Code from AlarmSystem example: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("cafe 'matchstick' added");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("cafe 'matchstick' added", e.getDescription());
		assertEquals(d, e.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "cafe 'matchstick' added", e.toString());
	}

    @Test
    public void testEqualsNullObject() {
        assertFalse(e.equals(null));

        assertFalse(e.equals("cafe 'matchstick' added"));
    }

    @Test
    public void testHashCode() {
        int hashCode = 13 * e.getDate().hashCode() + e.getDescription().hashCode();
        assertEquals(hashCode, e.hashCode());
    }
}
