import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyLinkedListTestStudent {

	private MyLinkedList<DictionaryEntry> list;

	@BeforeEach
	void setUp() throws Exception {
		// Create an empty list
		list = new MyLinkedList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		list = null;
	}

	@Test
	void testMyLinkedList() {
		assertDoesNotThrow(() -> new MyLinkedList<>());
	}

	@Test
	void testSizeAndAdd() {
		// Should be zero after creating
		assertEquals(0, list.size());

		// Add word "apple" to the list
		list.add(new DictionaryEntry("apple"));
		// Should increase by 1 after adding
		assertEquals(1, list.size());

		// Add word "Apple" to the list
		list.add(new DictionaryEntry("Apple"));
		// Should increase by 1 after adding because apple and Apple are not equal
		assertEquals(2, list.size());
	}

	@Test
	void testAddNullEntry() {
		assertThrows(IllegalArgumentException.class, () -> list.add(null));
	}

	@Test
	void testIsEmpty() {
		assertTrue(list.isEmpty());
	}

	@Test
	void testRemoveWord() {
		// Add word "orange" to the list
		list.add(new DictionaryEntry("orange"));

		// Remove word "apple" from the list
		// Should return true for successful removing
		assertTrue(list.removeWord("orange"));
	}

	@Test
	void testRemoveWordInAnEmptyList() {
		// Remove and empty list
		// Should return false
		assertFalse(list.removeWord("apple"));
	}

	@Test
	void testRemoveWordNotInAnEmptyList() {
		// Add word "orange" to the list
		list.add(new DictionaryEntry("orange"));

		// Remove word "apple" from the list
		// Should return false because apple does not in the list
		assertFalse(list.removeWord("apple"));
	}

	@Test
	void testFindWord() {
		// Add word "orange" to the list
		list.add(new DictionaryEntry("orange"));

		// Search for orange in the list
		assertEquals("orange", list.findWord("orange").getWord());

		// Search for apple should return null
		assertNull(list.findWord("Apple"));
	}

	@Test
	void testToWordArray() {
		// Add three words in the list
		list.add(new DictionaryEntry("orange"));
		list.add(new DictionaryEntry("apple"));
		list.add(new DictionaryEntry("banana"));

		String[] words = list.toWordArray();

		assertEquals("orange", words[0]);
		assertEquals("apple", words[1]);
		assertEquals("banana", words[2]);
	}

	@Test
	void testToString() {
		// Add two words in the list
		list.add(new DictionaryEntry("orange"));
		list.add(new DictionaryEntry("apple"));
		
		assertEquals("[orange: 1, apple: 1]", list.toString());
	}

}
