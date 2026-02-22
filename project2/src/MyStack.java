/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a generic stack implementation using an array.
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A generic stack implementation using an array. 
 * Elements are added and removed using LIFO (Last-In, First-Out) order.
 * 
 * @param <T> the type of elements stored in the stack
 */
public class MyStack<T> implements StackADT<T> {

	private T[] stack;
	private int topIndex;
	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Creates a stack with the default capacity.
	 */
	public MyStack() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates a stack with a specified initial capacity.
	 *
	 * @param initialCapacity the initial size of the stack array
	 * @throws IllegalStateException    if capacity exceeds maximum allowed
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	public MyStack(int initialCapacity) {
		if (initialCapacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a stack whose capacity exceeds allowed maximum.");
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be at least 1.");
		}

		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;

	}

	/**
	 * Adds an item to the top of the stack.
	 *
	 * @param item the element to be pushed onto the stack
	 * @throws IllegalArgumentException if item is null
	 * @throws IllegalStateException    if the stack is full
	 */
	@Override
	public void push(T item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (topIndex + 1 >= stack.length) {
			throw new IllegalStateException("Stack is full.");
		}
		stack[topIndex + 1] = item;
		topIndex++;

	}

	/**
	 * Removes and returns the top element of the stack.
	 *
	 * @return the element removed from the top
	 * @throws NoSuchElementException if the stack is empty
	 */
	@Override
	public T pop() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		}
	}

	/**
	 * Returns the top element without removing it.
	 *
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	@Override
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return stack[topIndex];
		}
	}

	/**
	 * Checks whether the stack is empty.
	 *
	 * @return true if the stack has no elements, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return topIndex < 0;
	}

	/**
	 * Returns the number of elements in the stack.
	 *
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return topIndex + 1;
	}

	/**
	 * Returns an array containing the elements of the stack.
	 *
	 * @return an array of stack elements
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(stack, size());
	}

}
