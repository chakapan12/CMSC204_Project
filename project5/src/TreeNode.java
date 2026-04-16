/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a generic TreeNode used in the MorseCodeTree.
 *              Each node stores data and references to left and right child nodes.
 * Due: 04/12/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * This class represents a generic TreeNode used in the MorseCodeTree. Each node
 * stores data and references to left and right child nodes.
 * 
 * @param <T> the type of elements stored in the TreeNode
 * @author Chakapan Kanchana
 */
public class TreeNode<T> {

	private T data;
	private TreeNode<T> left;
	private TreeNode<T> right;

	/**
	 * Creates a new TreeNode with given data and initializes left and right
	 * children to null.
	 * 
	 * @param data the data to store in this node
	 */
	public TreeNode(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	/**
	 * Returns the data stored in this node.
	 * 
	 * @return the data of this node
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data for this node.
	 * 
	 * @param data the data to be stored in this node
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Returns the left child of this node.
	 * 
	 * @return reference to the left child node
	 */
	public TreeNode<T> getLeftChild() {
		return left;
	}

	/**
	 * Sets the data for this node.
	 * 
	 * @param data the data to be stored in this node
	 */
	public void setLeftChild(TreeNode<T> left) {
		this.left = left;
	}

	/**
	 * Returns the right child of this node.
	 * 
	 * @return reference to the right child node
	 */
	public TreeNode<T> getRightChild() {
		return right;
	}

	/**
	 * Returns the right child of this node.
	 * 
	 * @return reference to the right child node
	 */
	public void setRightChild(TreeNode<T> right) {
		this.right = right;
	}

}
