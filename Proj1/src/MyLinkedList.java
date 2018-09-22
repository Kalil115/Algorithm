import java.util.Iterator;

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType> {

	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;

	/**
	 * Construct an empty LinkedList.
	 */
	public MyLinkedList() {
		doClear();
	}

	private void clear() {
		doClear();
	}

	/**
	 * Change the size of this collection to zero.
	 */
	public void doClear() {
		beginMarker = new Node<>(null, null, null);
		endMarker = new Node<>(null, beginMarker, null);
		beginMarker.next = endMarker;

		theSize = 0;
		modCount++;
	}

	/**
	 * Returns the number of items in this collection.
	 * 
	 * @return the number of items in this collection.
	 */
	public int size() {
		return theSize;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Adds an item to this collection, at the end.
	 * 
	 * @param x any object.
	 * @return true.
	 */
	public boolean add(AnyType x) {
		add(size(), x);
		return true;
	}

	/**
	 * Adds an item to this collection, at specified position. Items at or after
	 * that position are slid one position higher.
	 * 
	 * @param x   any object.
	 * @param idx position to add at.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size(),
	 *                                   inclusive.
	 */
	public void add(int idx, AnyType x) {
		addBefore(getNode(idx, 0, size()), x);
	}

	/**
	 * Adds an item to this collection, at specified position p. Items at or after
	 * that position are slid one position higher.
	 * 
	 * @param p Node to add before.
	 * @param x any object.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size(),
	 *                                   inclusive.
	 */
	private void addBefore(Node<AnyType> p, AnyType x) {
		Node<AnyType> newNode = new Node<>(x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}

	/**
	 * Returns the item at position idx.
	 * 
	 * @param idx the index to search in.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public AnyType get(int idx) {
		return getNode(idx).data;
	}

	/**
	 * Changes the item at position idx.
	 * 
	 * @param idx    the index to change.
	 * @param newVal the new value.
	 * @return the old value.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public AnyType set(int idx, AnyType newVal) {
		Node<AnyType> p = getNode(idx);
		AnyType oldVal = p.data;

		p.data = newVal;
		return oldVal;
	}

	public void swap(int idx1, int idx2) {
		Node<AnyType> node1 = getNode(idx1);
		Node<AnyType> node2 = getNode(idx2);

		if (node1 == node2)
			return;
		else if (node1.next == node2 || node2.next == node1) {
			node1.next = node2.next;
			node2.prev = node1.prev;
			node1.next.prev = node1;
			node2.prev.next = node2;
			node2.next = node1;
			node1.prev = node2;
			modCount++;
		} else {
			Node<AnyType> a = node1.prev;
			Node<AnyType> b = node1.next;
			node1.next = node2.next;
			node2.next.prev = node1;
			node2.prev.next = node1;
			node1.prev = node2.prev;
			node2.next = b;
			b.prev = node2;
			a.next = node2;
			node2.prev = a;
			modCount++;
		}

	}

	public void shift(int s) {
		int position = s % this.theSize;
		Node<AnyType> newBegin;
		if (position == 0) {
			return;
		} else if (position > 0) {
			newBegin = getNode(position);
		} else {
			position = Math.abs(position);
			newBegin = getNode(size() - position);
		}
		Node<AnyType> newEnd = newBegin.prev;
		Node<AnyType> oldBegin = beginMarker.next;
		Node<AnyType> oldEnd = endMarker.prev;
		oldBegin.prev = oldEnd;
		oldEnd.next = oldBegin;
		newBegin.prev = beginMarker;
		beginMarker.next = newBegin;
		newEnd.next = endMarker;
		endMarker.prev = newEnd;
		modCount++;
	}

	public void erase(int idx, int num) {
		if (idx + num > theSize)
			throw new IndexOutOfBoundsException("erase index: " + idx + "; elements: " + num + "; size: " + size());
		for (int i = 0; i < num; i++) {
			remove(idx);
		}
	}
	
	public void insertList(MyLinkedList<AnyType> lst, int idx) {
		if(idx > theSize)
			throw new IndexOutOfBoundsException("insert index: " + idx +"; size: "+size());
		Iterator<AnyType> itr = lst.iterator();
		while (itr.hasNext()) {
			this.add(idx, itr.next());
			idx++;
		}

	}

	/**
	 * Gets the Node at position idx, which must range from 0 to size( ) - 1.
	 * 
	 * @param idx index to search at.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1,
	 *                                   inclusive.
	 */
	private Node<AnyType> getNode(int idx) {
		return getNode(idx, 0, size() - 1);
	}

	/**
	 * Gets the Node at position idx, which must range from lower to upper.
	 * 
	 * @param idx   index to search at.
	 * @param lower lowest valid index.
	 * @param upper highest valid index.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx is not between lower and upper,
	 *                                   inclusive.
	 */
	private Node<AnyType> getNode(int idx, int lower, int upper) {
		Node<AnyType> p;

		if (idx < lower || idx > upper)
			throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());

		if (idx < size() / 2) {
			p = beginMarker.next;
			for (int i = 0; i < idx; i++)
				p = p.next;
		} else {
			p = endMarker;
			for (int i = size(); i > idx; i--)
				p = p.prev;
		}

		return p;
	}

	/**
	 * Removes an item from this collection.
	 * 
	 * @param idx the index of the object.
	 * @return the item was removed from the collection.
	 */
	public AnyType remove(int idx) {
		return remove(getNode(idx));
	}

	/**
	 * Removes the object contained in Node p.
	 * 
	 * @param p the Node containing the object.
	 * @return the item was removed from the collection.
	 */
	private AnyType remove(Node<AnyType> p) {
		p.next.prev = p.prev;
		p.prev.next = p.next;
		theSize--;
		modCount++;

		return p.data;
	}

	/**
	 * Returns a String representation of this collection.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[ ");

		for (AnyType x : this)
			sb.append(x + " ");
		sb.append("]");

		return new String(sb);
	}

	/**
	 * Obtains an Iterator object used to traverse the collection.
	 * 
	 * @return an iterator positioned prior to the first element.
	 */
	public java.util.Iterator<AnyType> iterator() {
		return new LinkedListIterator();
	}

	/**
	 * This is the implementation of the LinkedListIterator. It maintains a notion
	 * of a current position and of course the implicit reference to the
	 * MyLinkedList.
	 */
	private class LinkedListIterator implements java.util.Iterator<AnyType> {
		private Node<AnyType> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext() {
			return current != endMarker;
		}

		public AnyType next() {
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException();
			if (!hasNext())
				throw new java.util.NoSuchElementException();

			AnyType nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		public void remove() {
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException();
			if (!okToRemove)
				throw new IllegalStateException();

			MyLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove = false;
		}
	}

	/**
	 * This is the doubly-linked list node.
	 */
	private static class Node<AnyType> {

		public AnyType data;
		public Node<AnyType> prev;
		public Node<AnyType> next;

		public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
			data = d;
			prev = p;
			next = n;
		}
	}

}

class TestLinkedList {
	public static void main(String[] args) {
		MyLinkedList<Integer> lst = new MyLinkedList<>();
		for (int i = 0; i < 10; i++)
			lst.add(i);
		for (int i = 20; i < 30; i++)
			lst.add(0, i);
		System.out.printf("Original list: "+ lst);
		System.out.println(" size: " + lst.size());
		
		//swap
		lst.swap(10, 10);
		lst.swap(1, 2);
		lst.swap(5, 7);
		lst.swap(13, 18);
		System.out.println("swapped list: " + lst);
		
		
		//shift
		lst.shift(-21);
		System.out.printf("shifted list: " + lst);
		System.out.println(" size: " + lst.size());
		lst.shift(8);
		System.out.printf("shifted list: " + lst);
		System.out.println(" size: " + lst.size());
		lst.shift(-1);
		System.out.printf("shifted list: " + lst);
		System.out.println(" size: " + lst.size());
		lst.shift(-4);
		System.out.printf("shifted list: " + lst);
		System.out.println(" size: " + lst.size());
	

		//erase
		lst.erase(1, 3);
		System.out.printf("erased list:" + lst);
		System.out.println(" size: " + lst.size());

		//insertList
		MyLinkedList<Integer> lst2 = new MyLinkedList<>();
		for (int i = 30; i < 40; i++)
			lst2.add(i);
		System.out.printf("list2: " + lst2);
		System.out.println("size: " + lst2.size());

		lst.insertList(lst2, 4);
		System.out.printf("inserted list: " + lst);
		System.out.println(" size: " + lst.size());

		
	}
}
