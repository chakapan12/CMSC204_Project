/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a generic singly linked list that stores 
 *              DictionaryEntry objects. It supports adding, removing, searching, 
 *              and converting elements to an array.
 * Due: 03/29/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * A singly linked list implementation that stores DictionaryEntry objects.
 * Provides basic operations such as add, remove, search, and traversal.
 * 
 * @param <T> type of elements, must extend DictionaryEntry
 */
public class MyLinkedList<T extends DictionaryEntry> {

	/**
	 * Inner Node class represents each element in the list. Each node stores data
	 * and a reference to the next node.
	 */
	private class Node {
		private Node next;
		private T data;

		/**
		 * Constructs a node with the given data.
		 * 
		 * @param data the data to store in the node
		 */
		public Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/** Reference to the first node in the list */
	private Node firstNode;

	/** Number of elements in the list */
	private int size;

	/**
	 * Constructs an empty linked list.
	 */
	public MyLinkedList() {
		firstNode = null;
		size = 0;
	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks whether the list is empty.
	 * 
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param anEntry the element to add
	 * @throws IllegalArgumentException if the element is null
	 */
	public void add(T anEntry) {
		if (anEntry == null) {
			throw new IllegalArgumentException();
		}

		Node newNode = new Node(anEntry);

		if (isEmpty()) {
			// If list is empty, new node becomes first node.
			firstNode = newNode;
		} else {
			Node current = firstNode;

			// Traverse to the last node
			while (current.next != null) {
				current = current.next;
			}
			// Attach new node to the end
			current.next = newNode;

		}
		size++;
	}

	/**
	 * Removes a word from the list.
	 * 
	 * @param word the word to remove
	 * @return true if the word was removed, false otherwise
	 * @throws IllegalArgumentException if word is null or empty
	 */
	public boolean removeWord(String word) {
		if (word == null || word.isEmpty()) {
			throw new IllegalArgumentException();
		}

		if (isEmpty()) {
			return false;
		}

		// If removed word is the first node
		if (firstNode.data.getWord().equals(word)) {
			firstNode = firstNode.next;
			size--;
			return true;
		}

		// Traverse and check next node
		Node current = firstNode;
		while (current.next != null) {
			if (current.next.data.getWord().equals(word)) {
				// Bypass the node to remove it
				current.next = current.next.next;
				size--;
				return true;
			}
			current = current.next;
		}

		return false;
	}

	/**
	 * Searches for a word in the list.
	 * 
	 * @param word the word to find
	 * @return the matching DictionaryEntry if found, null otherwise
	 */
	public T findWord(String word) {
		Node current = firstNode;

		while (current != null) {
			if (current.data.getWord().equals(word)) {
				return current.data;
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Converts the list into an array of words.
	 * 
	 * @return an array containing all words in the list
	 */
	public String[] toWordArray() {
		String[] words = new String[size];

		Node current = firstNode;
		int index = 0;

		// Traverse and copy words into array
		while (current != null) {
			words[index++] = current.data.getWord();
			current = current.next;
		}
		return words;
	}

	/**
	 * Returns a string representation of the list.
	 * Format: [word1: freqency, word2: frequency, ...]
	 * 
	 * @return string representation of the list
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node current = firstNode;

		while (current != null) {
			sb.append(current.data);
			if (current.next != null) {
				sb.append(", ");
			}
			current = current.next;
		}
		sb.append("]");
		return sb.toString();
	}

}
