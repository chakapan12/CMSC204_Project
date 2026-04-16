import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class MorseCodeConverterTestStudent {

	@Test
	void testSingleLetter() {
		assertEquals("a", MorseCodeConverter.convertToEnglish(".-"));
		assertEquals("d", MorseCodeConverter.convertToEnglish("-.."));

	}

	void testSingleWord() {
		assertEquals("hello", MorseCodeConverter.convertToEnglish(".... . .-.. .-.. ---"));
	}

	@Test
	void testTwoWords() {
		assertEquals("hello world", MorseCodeConverter.convertToEnglish(".... . .-.. .-.. --- / .-- --- .-. .-.. -.."));
	}

	@Test
	void testTwoWordsAndMore() {
		assertEquals("i love you", MorseCodeConverter.convertToEnglish(".. / .-.. --- ...- . / -.-- --- ..-"));
	}
	
	@Test
	public void testConvertToEnglishWithExtraSpaces() {
		assertEquals("sos", MorseCodeConverter.convertToEnglish("...  ---   ..."));
	}

	@Test
	void testPrintTree() {
		assertEquals("h s v i f u e l r a p w j  b d x n c k y t z g q m o", MorseCodeConverter.printTree());
	}
	
	@Test
	void testConvertToEnglishFromFile() throws FileNotFoundException {
		File file = new File("src/howDoILoveThee.txt");
		assertEquals("how do i love thee let me count the ways", MorseCodeConverter.convertToEnglish(file));
		
	}
	
	@Test
	void testFileNotFoundThrowException() {
		File file = new File("src/fileNotExist.txt");
		assertThrows(FileNotFoundException.class, () -> MorseCodeConverter.convertToEnglish(file));
	}
	
	

}
