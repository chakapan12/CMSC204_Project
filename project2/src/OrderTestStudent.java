/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class contains public JUnit 5 tests used to validate an Order Class
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains public JUnit 5 tests used to validate an Order Class
 */
class OrderTestStudent {

	private Order o1;
	private Order o2;


	@BeforeEach
	void setUp() throws Exception {
		o1 = new Order("A01", 2);
		o2 = new Order("C01", 2);
	}

	@AfterEach
	void tearDown() throws Exception {
		o1 = null;
		o2 = null;

	}

	@Test
	void testConstructorThrowsForNegativeDeadline() {
		assertThrows(IllegalArgumentException.class, () -> new Order("Z01", -1));
	}

	@Test
	void testConstructorThrowsForNullId() {
		assertThrows(IllegalArgumentException.class, () -> new Order(null, 10));
	}

	@Test
	void testConstructorThrowsForBlankId() {
		assertThrows(IllegalArgumentException.class, () -> new Order("   ", 10));
	}

	@Test
	void testSetAndGetArrivalMinuteValid() {

		// Set arrival time for o2 equal 2
		o1.setArrivalMinute(2);
		assertEquals(2, o1.getArrivalMinute());

		// Default arrival time
		assertEquals(-1, o2.getArrivalMinute());

	}

	@Test
	void testSetArrivalMinuteWithInvalidArgument() {

		// Set arrival minuste should throw IllegalArgumentException.
		assertThrows(IllegalArgumentException.class, () -> o2.setArrivalMinute(-5));
	}

	@Test
	void testGetId() {
		assertEquals("A01", o1.getId());
	}

	@Test
	void testGetDeadlineMinute() {
		assertEquals(2, o1.getDeadlineMinute());
	}

}
