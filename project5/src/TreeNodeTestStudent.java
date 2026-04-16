import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeNodeTestStudent {

	private TreeNode<String> node;

	@BeforeEach
	void setUp() throws Exception {
		node = new TreeNode<>("a");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConstructerAndGetData() {
		TreeNode<String> node1 = new TreeNode<>("b");
		assertEquals("b", node1.getData());
		assertNull(node1.getLeftChild());
		assertNull(node1.getRightChild());
	}


	@Test
	void testSetData() {
		node.setData("z");
		assertEquals("z", node.getData());
	}

	@Test
	void testSetAndGetLeftChild() {
		TreeNode<String> left = new TreeNode<>("left");
		node.setLeftChild(left);
		
		assertEquals(left, node.getLeftChild());
		assertEquals("left", node.getLeftChild().getData());
	}

	@Test
	void testSetAndGetRightChild() {
		TreeNode<String> right = new TreeNode<>("right");
		node.setRightChild(right);
		
		assertEquals(right, node.getRightChild());
		assertEquals("right", node.getRightChild().getData());
	}

}
