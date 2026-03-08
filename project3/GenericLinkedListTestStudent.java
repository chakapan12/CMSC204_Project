import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericLinkedListTestStudent {

	private GenericLinkedList<String> list;

	@BeforeEach
	void setUp() throws Exception {
		list = new GenericLinkedList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		list = null;
	}

	@Test
	public void testNewListIsEmpty() {
		assertTrue(list.isEmpty());
		assertEquals(0, list.size());
	}

	@Test
	public void testAddAndGet() {
		list.add("A");
		list.add("B");
		list.add("C");

		assertEquals(3, list.size());
		assertEquals("A", list.getFirst());
		assertEquals("C", list.getLast());
		assertEquals("B", list.get(1));
	}

	@Test
	public void testAddFirstAndAddLast() {
		list.addFirst("B");
		list.addFirst("A");
		list.addLast("C");

		assertEquals(3, list.size());
		assertEquals("A", list.getFirst());
		assertEquals("C", list.getLast());
	}

	@Test
	public void testAddNullThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> list.add(null));
	}

	@Test
	public void testRemoveFirst() {
		list.add("A");
		list.add("B");
		list.add("C");

		assertEquals("A", list.removeFirst());
		assertEquals(2, list.size());
		assertEquals("B", list.getFirst());
	}

	@Test
	public void testRemoveLast() {
		list.add("A");
		list.add("B");
		list.add("C");

		assertEquals("C", list.removeLast());
		assertEquals(2, list.size());
		assertEquals("B", list.getLast());
	}

	@Test
	public void testRemoveByIndex() {
		list.add("A");
		list.add("B");
		list.add("C");

		assertEquals("B", list.remove(1));
		assertEquals(2, list.size());
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
	}

	@Test
	public void testRemoveByElement() {
		list.add("A");
		list.add("B");
		list.add("C");

		assertTrue(list.remove("B"));
		assertFalse(list.contains("B"));
		assertEquals(2, list.size());
	}

	@Test
	public void testContainsAndClear() {
		list.add("A");
		list.add("B");

		assertTrue(list.contains("A"));
		assertFalse(list.contains("X"));

		list.clear();
		assertTrue(list.isEmpty());
		assertEquals(0, list.size());
	}

	@Test
	public void testToArray() {
		list.add("A");
		list.add("B");

		Object[] arr = list.toArray();
		assertEquals(2, arr.length);
		assertEquals("A", arr[0]);
		assertEquals("B", arr[1]);
	}

	@Test
	public void testIteratorNext() {
		list.add("A");
		list.add("B");
		list.add("C");

		ListIterator<String> it = list.iterator();

		assertTrue(it.hasNext());
		assertEquals("A", it.next());
		assertEquals("B", it.next());
		assertEquals("C", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIteratorPrevious() {
		list.add("A");
		list.add("B");

		ListIterator<String> it = list.iterator();
		it.next();
		it.next();

		assertTrue(it.hasPrevious());
		assertEquals("B", it.previous());
		assertEquals("A", it.previous());
		assertFalse(it.hasPrevious());
	}

	@Test
	public void testIteratorRemove() {
		list.add("A");
		list.add("B");
		list.add("C");

		ListIterator<String> it = list.iterator();
		assertEquals("A", it.next());
		it.remove();

		assertEquals(2, list.size());
		assertEquals("B", list.getFirst());
		assertFalse(list.contains("A"));
	}

	@Test
	public void testIteratorRemoveWithoutNextOrPrevious() {
		list.add("A");
		ListIterator<String> it = list.iterator();
		assertThrows(IllegalStateException.class, () -> it.remove());
	}

	public void testGetFirstOnEmptyList() {
		assertThrows(NoSuchElementException.class, () -> list.getFirst());
	}

	@Test
	public void testRemoveFirstOnEmptyList() {
		assertThrows(NoSuchElementException.class, () -> list.removeFirst());
	}

	@Test
	public void testGetInvalidIndex() {
		list.add("A");
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
	}

}
