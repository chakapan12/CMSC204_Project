/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class implements a linked binary tree to represent
 *              Morse code. Each node represents a letter, and traversal
 *              is based on '.' (left) and '-' (right).
 * Due: 04/12/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.ArrayList;

/**
 * This class implements a linked binary tree to represent Morse code. Each node
 * represents a letter, and traversal is based on '.' (left) and '-' (right).
 * 
 * @author Chakapan Kanchana
 */
public class MorseCodeTree implements LinkedConverterTreeInterface<String> {

	private TreeNode<String> root;

	/**
	 * Constructs a MorseCodeTree and immediately builds it by inserting all 26
	 * alphabet letters.
	 */
	public MorseCodeTree() {
		buildTree();
	}

	/**
	 * Returns the root node of the tree.
	 *
	 * @return the root TreeNode
	 */
	@Override
	public TreeNode<String> getRoot() {
		return root;
	}

	/**
	 * Sets the root node of the tree.
	 *
	 * @param newNode the TreeNode to set as root
	 */
	@Override
	public void setRoot(TreeNode<String> newNode) {
		root = newNode;
	}

	/**
	 * Adds element to the correct position in the tree based on the code This
	 * method will call the recursive method addNode
	 * 
	 * @param code   the Morse code string (e.g. ".-")
	 * @param letter the English letter to store (e.g. "a")
	 * 
	 */
	@Override
	public void insert(String code, String letter) {
		if (root == null) {
			root = new TreeNode<>("");
		}
		addNode(root, code, letter);

	}

	/**
	 * This is a recursive method that adds element to the correct position in the
	 * tree based on the code.
	 * 
	 * @param root   the current node in the traversal
	 * @param code   the remaining Morse code to process
	 * @param letter the letter to insert at the final node
	 */
	@Override
	public void addNode(TreeNode<String> root, String code, String letter) {
		// Base case: no code left - store the letter at this node
		if (code.isEmpty()) {
			root.setData(letter);
			return;
		}

		// '.' means go left
		if (code.charAt(0) == '.') {
			if (root.getLeftChild() == null) {
				root.setLeftChild(new TreeNode<>(""));
			}
			addNode(root.getLeftChild(), code.substring(1), letter);

			// '-' means go right
		} else {
			if (root.getRightChild() == null) {
				root.setRightChild(new TreeNode<>(""));
			}
			addNode(root.getRightChild(), code.substring(1), letter);
		}

	}

	/**
	 * Fetch the data in the tree based on the code This method will call the
	 * recursive method fetchNode
	 * 
	 * @param code the Morse code string (e.g. ".-")
	 * @return the Englist letter found at the corresponding tree position
	 */
	@Override
	public String fetch(String code) {
		return fetchNode(root, code);
	}

	/**
	 * This is the recursive method that fetches the data of the TreeNode that
	 * corresponds with the code A '.' (dot) means traverse to the left.
	 * 
	 * @param root the current node in the traversal
	 * @param code the remaing Morse code to process
	 * @return the Englist letter found at the corresponding tree position
	 */
	@Override
	public String fetchNode(TreeNode<String> root, String code) {
		// Base case: no code left - return this node's letter
		if (code.isEmpty()) {
			return root.getData();
		}
		if (code.charAt(0) == '.') {
			return fetchNode(root.getLeftChild(), code.substring(1));
		} else {
			return fetchNode(root.getRightChild(), code.substring(1));
		}

	}

	/**
	 * This operation is not supported in the MorseCodeTree
	 */
	@Override
	public LinkedConverterTreeInterface<String> delete(String data) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * This operation is not supported in the MorseCodeTree
	 */
	@Override
	public LinkedConverterTreeInterface<String> update() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method builds the MorseCodeTree by inserting the nodes of the tree level
	 * by level based on the code.
	 */
	@Override
	public void buildTree() {

		insert(".-", "a");
		insert("-...", "b");
		insert("-.-.", "c");
		insert("-..", "d");
		insert(".", "e");
		insert("..-.", "f");
		insert("--.", "g");
		insert("....", "h");
		insert("..", "i");
		insert(".---", "j");
		insert("-.-", "k");
		insert(".-..", "l");
		insert("--", "m");
		insert("-.", "n");
		insert("---", "o");
		insert(".--.", "p");
		insert("--.-", "q");
		insert(".-.", "r");
		insert("...", "s");
		insert("-", "t");
		insert("..-", "u");
		insert("...-", "v");
		insert(".--", "w");
		insert("-..-", "x");
		insert("-.--", "y");
		insert("--..", "z");

	}

	/**
	 * Returns an ArrayList of the items in the linked Tree in LNR (Inorder)
	 * Traversal order Used for testing to make sure tree is built correctly
	 * 
	 * @return an ArrayList of node data in LNR order
	 */
	@Override
	public ArrayList<String> toArrayList() {
		ArrayList<String> list = new ArrayList<>();
		LNRoutputTraversal(root, list);
		return list;
	}

	/**
	 * The recursive method to put the contents of the tree in an ArrayList in LNR
	 * (Inorder)
	 */
	@Override
	public void LNRoutputTraversal(TreeNode<String> root, ArrayList<String> list) {
		if (root != null) {
			LNRoutputTraversal(root.getLeftChild(), list);
			list.add(root.getData());
			LNRoutputTraversal(root.getRightChild(), list);
		}
	}

}
