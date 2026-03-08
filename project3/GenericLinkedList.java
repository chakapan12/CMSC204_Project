import java.util.ListIterator;
import java.util.NoSuchElementException;

public class GenericLinkedList<T> implements Iterable<T> {

	// Inner Node class
	private class Node {
		T data;
		Node previous;
		Node next;

		public Node(T data) {
			this.data = data;
			previous = null;
			next = null;
		}
	}

	private Node first;
	private Node last;
	private int size;

	public GenericLinkedList() {
		first = null;
		last = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void add(T anEntry) {
		if (anEntry == null) {
			throw new IllegalArgumentException();
		}
		Node newNode = new Node(anEntry);

		if (isEmpty()) {
			first = last = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}
		size++;
	}

	public void addFirst(T anEntry) {
		if (anEntry == null) {
			throw new IllegalArgumentException();
		}
		Node newNode = new Node(anEntry);
		if (isEmpty()) {
			first = last = newNode;
		} else {
			first.previous = newNode;
			newNode.next = first;
			first = newNode;
		}
		size++;
	}

	public void addLast(T anEntry) {
		add(anEntry);
	}

	public T getFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return first.data;
	}

	public T getLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return last.data;
	}

	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}

	public T removeFirst() {
		Node f = first;
		if (f == null) {
			throw new NoSuchElementException();
		}
		return unlinkFirst(f);
	}

	public T removeLast() {
		Node l = last;
		if (l == null) {
			throw new NoSuchElementException();
		}
		return unlinkLast(l);
	}

	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0)
			return removeFirst();
		if (index == size - 1)
			return removeLast();

		Node current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		// unlink current node
		return unlink(current);
	}

	public boolean remove(T element) {
		Node current = first;
		while (current != null) {
			if (current.data.equals(element)) {
				unlink(current);
				return true;
			}
			current = current.next;
		}
		return false; // Element not found

	}

	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Unlink non null first node f.
	 */
	public T unlinkFirst(Node f) {

		T element = f.data;
		Node next = f.next;
		f.data = null;
		f.next = null;
		first = next;

		// If list only has one node
		if (next == null) {
			last = null;
		} else {
			next.previous = null;
		}
		size--;
		return element;

	}

	/**
	 * Unlink non null last node l.
	 */
	public T unlinkLast(Node l) {

		T element = l.data;
		Node previous = l.previous;
		l.data = null;
		l.previous = null;
		last = previous;

		// If list only has one node
		if (previous == null) {
			first = null;
		} else {
			previous.next = null;
		}
		size--;
		return element;

	}

	/**
	 * Unlink non null node x.
	 */
	public T unlink(Node x) {

		T element = x.data;

		Node previous = x.previous;
		Node next = x.next;

		// if x is the first node
		if (previous == null) {
			first = next;
		} else {
			previous.next = next;
			x.previous = null;
		}

		// x is the last node
		if (next == null) {
			last = previous;
		} else {
			next.previous = previous;
			x.next = null;
		}
		size--;
		x.data = null;
		return element;

	}

	public boolean contains(T element) {
		Node current = first;
		while (current != null) {
			if (current.data.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	public Object[] toArray() {
		Object[] array = new Object[size];
		Node current = first;
		int i = 0;
		while (current != null) {
			array[i++] = current.data;
			current = current.next;
		}
		return array;
	}

	@Override
	public ListIterator<T> iterator() {
		return new GenericIterator();
	}

	private class GenericIterator implements ListIterator<T> {

		private Node nextNode;
		private Node lastReturned;
		private int nextIndex;

		public GenericIterator() {
			nextNode = first;
			lastReturned = null;
			nextIndex = 0;
		}

		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = nextNode;
			nextNode = nextNode.next;
			nextIndex++;
			return lastReturned.data;

		}

		@Override
		public boolean hasPrevious() {
			return nextIndex > 0;
		}

		@Override
		public T previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (nextNode == null) {
				nextNode = last;
			} else {
				nextNode = nextNode.previous;
			}
			lastReturned = nextNode;
			nextIndex--;
			return lastReturned.data;
		}

		@Override
		public void remove() {
			// Cannot call remove before calling next() or previous()
			if (lastReturned == null)
				throw new IllegalStateException();

			Node lastNext = lastReturned.next;

			// unlink lastReturn node from the list
			unlink(lastReturned);

			// check if nextNode is the lastReturned
			if (nextNode == lastReturned) {
				// set nextNode to the lastReturn.next
				nextNode = lastNext;
			} else {
				nextIndex--;
			}
			lastReturned = null;

		}

		@Override
		public void set(T e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(T e) {
			throw new UnsupportedOperationException();

		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}
	}

}
