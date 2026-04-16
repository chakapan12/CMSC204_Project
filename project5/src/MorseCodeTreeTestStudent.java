import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MorseCodeTreeTestStudent {

	private MorseCodeTree tree;

	@BeforeEach
	void setUp() throws Exception {
		tree = new MorseCodeTree();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetRoot() {
		assertNotNull(tree.getRoot());
		assertEquals("", tree.getRoot().getData());
	}

	@Test
	void testSetRoot() {
		TreeNode<String> newRoot = new TreeNode<String>("y");
		tree.setRoot(newRoot);
		assertEquals("y", tree.getRoot().getData());
	}

	@Test
	void testFetchSingleLetter() {

		assertEquals("a", tree.fetch(".-"));
		assertEquals("b", tree.fetch("-..."));
		assertEquals("c", tree.fetch("-.-."));
		assertEquals("d", tree.fetch("-.."));

	}
	
    @Test
    public void testFileNotFoundThrowsException() {
        File missing = new File("does_not_exist.txt");
        assertThrows(FileNotFoundException.class,
            () -> MorseCodeConverter.convertToEnglish(missing));
    }

	@Test
	void testToArrayListNotNull() {
		ArrayList<String> list = tree.toArrayList();
		assertNotNull(list);
	}

	@Test
	void testToArrayListContainsExpectedLeters() {
		ArrayList<String> list = tree.toArrayList();
		assertTrue(list.contains("a"));
		assertTrue(list.contains("d"));
		assertTrue(list.contains("h"));

	}

	void testToArrayListLNROrder() {
		// First letter in LNR order should be 'h' (leftmost leaf)
		ArrayList<String> list = tree.toArrayList();
		assertEquals("h", list.getFirst());
	}

	@Test
	public void testDeleteThrowsException() {
		assertThrows(UnsupportedOperationException.class, () -> tree.delete("a"));
	}

	@Test
	public void testUpdateThrowsException() {
		assertThrows(UnsupportedOperationException.class, () -> tree.update());
	}

}
