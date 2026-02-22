/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class contains public JUnit 5 tests used to validate an OrderComparator Class
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderComparatorTestStudent {

	private Order o1;
	private Order o2;
	private Order o3;
	private OrderComparator oc;

	@BeforeEach
	void setUp() throws Exception {
		o1 = new Order("A001", 5);
		o2 = new Order("A002", 7);
		o3 = new Order("A003", 5);
		oc = new OrderComparator();
	}

	@AfterEach
	void tearDown() throws Exception {
		o1 = null;
		o2 = null;
		o3 = null;
	}

	@Test
	void testCompareTwoOrderwithDifferentDeadline() {
		// o1: deadline = 5, o2: dealline = 7
		// o1 should have higher priority -> compare should be negative
		assertTrue(oc.compare(o1, o2) < 0);
		assertTrue(oc.compare(o2, o1) > 0);
	}

	@Test
	void testCompareTwoOrderwithSameDeadline() {
		o1.setArrivalMinute(2);
		o3.setArrivalMinute(5);
		
		// Same deadline, o1 arrived earlier -> compare should be negative
		assertTrue(oc.compare(o1, o3) < 0);
		assertTrue(oc.compare(o3, o1) > 0);
	}
	
	@Test
	void testCompareEqualOrders() {
	    o1.setArrivalMinute(3);
	    o3.setArrivalMinute(3);
	    
	 // Same deadline and same arrival -> compare should be 0
	    assertEquals(0, oc.compare(o1, o3));
	}

}
