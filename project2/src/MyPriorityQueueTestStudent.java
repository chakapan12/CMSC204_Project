
/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class contains public JUnit 5 tests used to validate a MyPriorityQueue Class
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyPriorityQueueTestStudent {

	private MyPriorityQueue<Order> pq;
	private OrderComparator op;

	@BeforeEach
	void setUp() throws Exception {
		op = new OrderComparator();
		pq = new MyPriorityQueue<>(3, op);

	}

	@AfterEach
	void tearDown() throws Exception {
		op = null;
		pq = null;
	}

	@Test
	void testConstructorWithNullComparatorThrowException() {
		assertThrows(IllegalArgumentException.class, () -> new MyPriorityQueue<>(null));
	}

	@Test
	void testConstructorWithInvalidCapacityThrowException() {
		assertThrows(IllegalArgumentException.class, () -> new MyPriorityQueue<>(0, op));
	}

	@Test
	void testConstructorExceedsMaxCapacityThrowsException() {
		assertThrows(IllegalArgumentException.class,
				() -> new MyPriorityQueue<>(PriorityQueueADT.MAX_CAPACITY + 1, op));
	}

	@Test
	void testNewQueueIsEmpty() {
		assertTrue(pq.isEmpty());
		assertEquals(0, pq.size());
	}

	@Test
	void testEnqueueNullItemThrowException() {
		assertThrows(IllegalArgumentException.class, () -> pq.enqueue(null));
	}

	@Test
	void testEnqueueWhenFullThrowException() {
		pq.enqueue(new Order("T001", 10));
		pq.enqueue(new Order("T002", 5));
		pq.enqueue(new Order("T001", 20));
		assertThrows(IllegalStateException.class, () -> pq.enqueue(new Order("T001", 15)));
	}

	@Test
	void testEnqueueAddAndMaintainPriorityOrder() {
		pq.enqueue(new Order("T001", 10));
		pq.enqueue(new Order("T002", 5)); // The highest priority
		pq.enqueue(new Order("T003", 20));

		// Order T002 should be at front
		assertEquals("T002", pq.peek().getId());

	}

	@Test
	void testDequeueRemoveInpriorityOrder() {
		pq.enqueue(new Order("T001", 10)); // The secound highest priority
		pq.enqueue(new Order("T002", 5)); // The highest priority
		pq.enqueue(new Order("T003", 20));

		// Order T002 shoud be removed and returned
		assertEquals("T002", pq.dequeue().getId());
		assertEquals(2, pq.size()); // size should decrease to 2

		// Order T001 should be removed and returned next
		assertEquals("T001", pq.dequeue().getId());
		assertEquals(1, pq.size()); // size should decrease to 1
	}

	@Test
	void testDequeueOnEmptyThrowException() {
		assertThrows(NoSuchElementException.class, () -> pq.dequeue());
	}

	@Test
	void testPeekOnEmptyThrowException() {
		assertThrows(NoSuchElementException.class, () -> pq.peek());
	}

	@Test
	void testPeekDoesNotRemove() {
		pq.enqueue(new Order("T001", 10)); // The secound highest priority
		pq.enqueue(new Order("T002", 5)); // The highest priority

		// Order T002 shoud be returned, but does not removed
		assertEquals("T002", pq.peek().getId());
		assertEquals(2, pq.size()); // still 2

		// Now confirm it is still there
		assertEquals("T002", pq.dequeue().getId());
		assertEquals(1, pq.size());
	}

	@Test
	void testToArray() {
		pq.enqueue(new Order("T001", 10));
		pq.enqueue(new Order("T002", 5));
		pq.enqueue(new Order("T003", 20));

		Object[] arr = pq.toArray();
		String[] expectedIds = { "T002", "T001", "T003" };

		for (int i = 0; i < arr.length; i++) {
			Order o = (Order) arr[i];
			assertEquals(expectedIds[i], o.getId());
		}

	}

}
