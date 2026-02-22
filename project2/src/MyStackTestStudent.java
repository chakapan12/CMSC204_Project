/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class contains public JUnit 5 tests used to validate a MyStack Class
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyStackTestStudent {

	private MyStack<String> stack;

	@BeforeEach
	void setUp() throws Exception {
		stack = new MyStack<>(3);

	}

	@AfterEach
	void tearDown() throws Exception {
		stack = null;
	}

	@Test
	void testConstructorInvalidCapacityThrowException() {
		assertThrows(IllegalArgumentException.class, () -> new MyStack<String>(0));
	}

	@Test
	void testConstructorCapacityExceedMaximumThrowException() {
		assertThrows(IllegalStateException.class, () -> new MyStack<String>(StackADT.MAX_CAPACITY + 1));
	}

	@Test
	void testNewStackIsEmpty() {
		assertTrue(stack.isEmpty());
		assertEquals(0, stack.size());
	}

	@Test
	void testPushIncreaseSizeAndNotEmpty() {
		stack.push("A");
		assertFalse(stack.isEmpty());
		assertEquals(1, stack.size());
	}

	@Test
	void testPushNullThrowException() {
		assertThrows(IllegalArgumentException.class, () -> stack.push(null));
	}

	@Test
	void testPushWhenFullThrowException() {
		stack.push("A");
		stack.push("B");
		stack.push("C");
		assertThrows(IllegalStateException.class, () -> stack.push("D"));
	}

	@Test
	void testPopReturnTOpAndRemove() {
		stack.push("A");
		stack.push("B");
		assertEquals("B", stack.pop());
		assertEquals(1, stack.size());
	}

	@Test
	void testPopOnEmptyStackThrowException() {
		assertThrows(NoSuchElementException.class, () -> stack.pop());
	}

	@Test
	void testPeekDoesNotRemove() {
		stack.push("A");
		stack.push("B");
		assertEquals("B", stack.peek());
		assertEquals(2, stack.size()); // size still 2
	}

	@Test
	void testPeekOnEmptyStackThrowException() {
		assertThrows(NoSuchElementException.class, () -> stack.peek());
	}

	@Test
	void testToArray() {
		stack.push("A");
		stack.push("B");
		stack.push("C");
		
		Object[] arr = stack.toArray();
		assertArrayEquals(new Object[] { "A", "B", "C" }, arr);
	}

}
