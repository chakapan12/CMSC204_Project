/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a generic PriorityQueue implementation using an array.
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A generic priority queue implementation using an array. Elements are stored
 * in sorted order based on the provided Comparator. The element with the
 * highest priority is stored at index 0.
 *
 * @param <T> the type of elements stored in the priority queue
 */
public class MyPriorityQueue<T> implements PriorityQueueADT<T> {

	private static final int DEFAULT_CAPACITY = 10;
	private Comparator<? super T> comparator;
	private T[] queue;
	private int size;

	/**
	 * Creates a priority queue with default capacity.
	 *
	 * @param comparator the Comparator used to determine priority
	 */
	public MyPriorityQueue(Comparator<? super T> comparator) {
		this(DEFAULT_CAPACITY, comparator);
	}

	/**
	 * Creates a priority queue with a specified initial capacity.
	 *
	 * @param initialCapacity the initial size of the queue
	 * @param comparator      the Comparator used to determine priority
	 * @throws IllegalArgumentException if comparator is null
	 * @throws IllegalArgumentException if capacity is invalid
	 */
	public MyPriorityQueue(int initialCapacity, Comparator<? super T> comparator) {

		if (comparator == null) {
			throw new IllegalArgumentException("Comparator cannot be null.");
		}

		if (initialCapacity > MAX_CAPACITY) {
			throw new IllegalArgumentException("Attempt to create a queue whose capacity exceeds allowed maximum.");
		}

		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be at least 1.");
		}

		@SuppressWarnings("unchecked")
		T[] tempQueue = (T[]) new Object[initialCapacity];
		queue = tempQueue;
		size = 0;
		this.comparator = comparator;
	}

	/**
	 * Inserts an element into the priority queue while maintaining sorted order.
	 *
	 * @param item the element to add
	 * @throws IllegalArgumentException if item is null
	 * @throws IllegalStateException    if the queue is full
	 */
	@Override
	public void enqueue(T item) {

		if (item == null) {
			throw new IllegalArgumentException("item cannot be null");
		}

		if (size == queue.length) {
			throw new IllegalStateException("PriorityQueue is full");
		}

		// Keep array sorted so "high priority" is at index 0
		int i = size - 1;

		// Shift right until correct spot found
		while (i >= 0 && comparator.compare(item, queue[i]) < 0) {
			queue[i + 1] = queue[i];
			i--;
		}
		queue[i + 1] = item;
		size++;

	}

	/**
	 * Removes and returns the element with the highest priority.
	 *
	 * @return the element removed from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public T dequeue() {

		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T result = queue[0];

		// Shift left to fill the hole
		for (int i = 1; i < size; i++) {
			queue[i - 1] = queue[i];
		}

		queue[size - 1] = null;
		size--;

		return result;
	}

	/**
	 * Returns the element with the highest priority without removing it.
	 *
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return queue[0];
		}
	}

	/**
	 * Checks whether the priority queue is empty.
	 *
	 * @return true if the queue is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return the current size of the queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns an array containing the elements of the queue in priority order.
	 *
	 * @return an array of queue elements
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(queue, size);
	}

}
