import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryEntryTestStudent {

	private DictionaryEntry entry1;
	private DictionaryEntry entry2;
	private DictionaryEntry entry3;

	@BeforeEach
	void setUp() throws Exception {
		entry1 = new DictionaryEntry("testword");
		entry2 = new DictionaryEntry("testword");
		entry3 = new DictionaryEntry("otherword");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDictionaryEntry() {
		// Valid constructor argument
		assertDoesNotThrow(() -> new DictionaryEntry("apple"));

		// Invalid constructor argument
		assertThrows(IllegalArgumentException.class, () -> new DictionaryEntry(null));
	}

	@Test
	void testIncrementFrequencyAndGetFrequency() {
		// Call increntFrequency method for entry
		entry1.incrementFrequency();
		assertEquals(2, entry1.getFrequency()); // frequency count should be incremented by 1
	}

	@Test
	void testGetWord() {
		assertEquals("testword", entry1.getWord());
	}

	@Test
	void testToString() {
		assertEquals("testword: 1", entry1.toString());
	}

	@Test
	void testEqualsObject() {
		assertTrue(entry1.equals(entry2));
		assertFalse(entry1.equals(entry3));
	}

	@Test
	void testCompareTo() {
		assertTrue(entry1.compareTo(entry2) == 0); // entry1 equals to entry2
		assertTrue(entry1.compareTo(entry3) > 0); // entry1 comes after entry3 (t comes after o)
		assertTrue(entry3.compareTo(entry1) < 0); // entry3 comes before entry1 (o comes before t)
	}

}
