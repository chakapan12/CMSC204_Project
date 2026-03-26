
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryBuilderTestStudent {
	private DictionaryBuilder dictionary;
	private String testData;
	private File tempFile;

	@BeforeEach
	void setUp() throws Exception {

		dictionary = new DictionaryBuilder(10);

		testData = "The quick brown fox jumps over the lazy dog.";

		tempFile = File.createTempFile("sample", ".txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
			writer.write(testData.replace("\n", System.lineSeparator()));
		}

	}

	@AfterEach
	void tearDown() throws Exception {
		dictionary = null;
	}

	@Test
	void testConstructorWithNormalEstimateSize() {
		// Estmated size = 20
		DictionaryBuilder db = new DictionaryBuilder(20);

		// Get size and verify its form of 4k + 3 primpe
		// Recommeded size = 23 / 0.6 = 39
		// Next prime should be 43
		assertEquals(43, db.getTableSize());

	}

	@Test
	void testConstructorWithNegativeEstimateSize() {
		// Estmated size = -5
		DictionaryBuilder db = new DictionaryBuilder(-5);

		// Get size and verify its form of 4k + 3 primpe
		// Since etimate size is less than 10. We set it to 10
		// Recommeded size = 10 / 0.6 = 17
		// Next prime should be 19
		assertEquals(19, db.getTableSize());

	}

	@Test
	void testConstructorwithInvalidFile() {
		assertThrows(FileNotFoundException.class, () -> new DictionaryBuilder("testFile"));
	}

	@Test
	void testConstructorwithValidFile() {
		
		// "The quick brown fox jumps over the lazy dog."
		// total words = 9, uniqueWords = 8
		assertDoesNotThrow(() -> new DictionaryBuilder(tempFile.getAbsolutePath()));

	}
	
	@Test
	void testConstructorLoadWordsFromFile() throws FileNotFoundException {
		
		// "The quick brown fox jumps over the lazy dog."
		// total words = 9, uniqueWords = 8
		dictionary = new DictionaryBuilder(tempFile.getAbsolutePath());
		assertEquals(9, dictionary.getTotalWords());
		assertEquals(8, dictionary.getTotalUniqueWords());
	}

	@Test
	void testAddWordAddGetTotalWords() {

		dictionary.addWord("apple");
		assertEquals(1, dictionary.getTotalWords());

		dictionary.addWord("orange");
		assertEquals(2, dictionary.getTotalWords());

		dictionary.addWord("orange");
		assertEquals(3, dictionary.getTotalWords());

	}

	@Test
	void testAddWordWithDifferentCase() {
		dictionary.addWord("apple");
		assertEquals(1, dictionary.getFrequency("apple"));

		// apple and ApplE should be treated the same.
		// Frequncy count is incremented by 1
		dictionary.addWord("ApplE");
		assertEquals(2, dictionary.getFrequency("apple"));
	}

	@Test
	void testAddWordWithEmptyString() {

		// empty word should not be added
		dictionary.addWord("");
		assertEquals(0, dictionary.getTotalWords());

	}

	@Test
	void testAddWordWithPuctuation() {

		// only apple should be added not apple!
		dictionary.addWord("apple!");
		assertEquals(1, dictionary.getFrequency("apple"));
	}

	@Test
	void testAddMultipleWords() {

		// appleorange will be added.
		// because sanitzeWord() remove space
		dictionary.addWord("apple orange");
		assertEquals(1, dictionary.getFrequency("appleorange"));
	}

	@Test
	void testAddNull() {

		// null should not be added
		dictionary.addWord(null);
		assertEquals(0, dictionary.getTotalWords());

	}

	@Test
	void testGetFrequency() {

		dictionary.addWord("apple");
		assertEquals(1, dictionary.getFrequency("apple"));

		dictionary.addWord("orange");
		assertEquals(1, dictionary.getFrequency("orange"));

		dictionary.addWord("apple");
		assertEquals(2, dictionary.getFrequency("apple"));

	}

	@Test
	void testRemoveEmptyWord() {
		assertThrows(DictionaryEntryNotFoundException.class, () -> dictionary.removeWord(""));
	}

	@Test
	void testRemoveNull() {
		assertThrows(DictionaryEntryNotFoundException.class, () -> dictionary.removeWord(""));
	}

	@Test
	void testRemoveNonExistingWord() {
		assertThrows(DictionaryEntryNotFoundException.class, () -> dictionary.removeWord("Banana"));
	}

	@Test
	void testRemoveExistingWord() throws DictionaryEntryNotFoundException {
		dictionary.addWord("orange");
		dictionary.addWord("orange");
		dictionary.addWord("apple");
		
		// Total words = 3
		// frequency of orange = 2
		dictionary.removeWord("orange");
		// Total words should be 1
		assertEquals(1, dictionary.getTotalWords());
		
		dictionary.removeWord("apple");
		// Total words should be 0
		assertEquals(0, dictionary.getTotalWords());

	}

	@Test
	void testGetAllWords() {
		dictionary.addWord("orange");
		dictionary.addWord("apple");
		dictionary.addWord("banana");
		
		// should return sorted list of [apple, banana, orange]
		List<String> list= dictionary.getAllWords();
		assertEquals("[apple, banana, orange]", list.toString());
	}

	@Test
	void testGetTotalWords() {
		dictionary.addWord("orange");
		dictionary.addWord("apple");
		dictionary.addWord("apple");
		assertEquals(3, dictionary.getTotalWords());
	}

	@Test
	void testGetTotalUniqueWords() {
		dictionary.addWord("orange");
		dictionary.addWord("apple");
		dictionary.addWord("apple");
		assertEquals(2, dictionary.getTotalUniqueWords());
	}

	@Test
	void testGetLoadFactor() {
		
	}

	@Test
	void testGetTableSize() {
		
		// Estimated size = 10
		// recomended size = 10 / 0.6 = 17
		// next 4k + 3 prime = 19
		assertEquals(19, dictionary.getTableSize());
	}

	@Test
	void testSanitizeWord() {
		assertEquals("apple", dictionary.sanitizeWord("APPle!"));
		assertEquals("apple", dictionary.sanitizeWord("ApP123le!"));
	}

}
