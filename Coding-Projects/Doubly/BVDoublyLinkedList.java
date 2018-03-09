//package name redacted

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * <b><i>BVDoublyLinkedList</i></b>
 * <p>
 * DoublyLinkedList structure that implements most
 * methods from the List java interface. Also contains ListIterator
 * defined as an inner class.
 * <p>
 * <b>Class Invariant:</b> Some methods using Collection
 * parameters have not been implemented as of 9/27/2017. As
 * such, some methods from the List<E> interface are stub
 * methods. More methods were implemented in version 1.1.
 * <p>
 * <b>Class Invariant:</b> As of now, Unit Testing has not been
 * performed to thoroughly test this structure. In the future
 * versions, would like to build Unit Testing to verify
 * each method works in edge cases.
 *
 * @author Ben Vargas
 * @version 1.1
 * @param <E>
 * 			Generic type to be set during initialization of list
 */
public class BVDoublyLinkedList<E> implements List<E> {
	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0;

	/**
	 * Auto-converts a this DoublyLinkedList to String when called as a String.
	 * <p>
	 * Modified to iterate through each element via an Iterator in version 1.1
	 */
	@Override
	public String toString() {
		if(head == null) {
			return "NULL";
		}

		ListIterator<E> iter = listIterator();
		E data;
		StringBuilder result = new StringBuilder();

		while (iter.hasNext()) {
			data = iter.next();
			result.append(data);

			if(iter.hasNext()) {
				result.append(" ==> ");
			}
		}
		return result.toString();
	}

	//CONSTRUCTORS

	//default
	public BVDoublyLinkedList() {
		//do nothing, since variables already initialized at declaration
	}

	//constructor with one node
	public BVDoublyLinkedList(E data) {
		add(data);
	}

	//constructor to copy a linkedlist into a new list (deep copy of data)
	public BVDoublyLinkedList(BVDoublyLinkedList<E> other) {
		head = other.head;

		if (head != null) {
			Node<E> temp = head;

			while (temp.next != null) {
				temp.next = new Node<E>(temp.next.data);
				size++;
			}
		}
	}

	/**
	 * <b><i>add (to end)</i></b>
	 * <p>
	 * Adds a reference to data at the end of the list.
	 * @param data
	 * 		data to be input into the list as a node
	 * @return
	 * 		always returns true due to nature of LinkedList list types
	 */
	public boolean add(E data) {
		ListIterator<E> iter = listIterator(size);
		iter.add(data);
		return true; //always returns true (can always add to end of linkedlist)
	}

	/**
	 * <b><i>add (to index)</i></b>
	 * <p>
	 * Adds a reference to data (a node), inserting it before the position
	 * index.
	 * @param index
	 * 		location (sequential order) at which data will be placed
	 * @param data
	 * 		data to be inserted into the list as a node
	 * @throws IndexOutOfBoundsException
	 * 		if index passed to method is less than 0 or greater than size
	 */
	public void add(int index, E data) {
		ListIterator<E> iter = listIterator(index);
		iter.add(data);
	}

	/**
	 * <b><i>remove(Object data)</i></b>
	 * <p>
	 * Removes first instance of specified data from linked list and
	 * returns true if successful, false if data not found.
	 *
	 * @param data
	 * 		data to be searched for and removed from list
	 * @return
	 * 		true if data found in list
	 * 		<br>
	 * 		false if data not found in list
	 */
	@Override
	public boolean remove(Object data) {
		int index = indexOf(data); //-1 if not found, returns false
		if (index == -1) {
			return false;
		}

		//moving to item and removing it
		ListIterator<E> iter = listIterator(index);
		iter.next();
		iter.remove();

		return true; //if got to this line, item was found and removed
	}

	/**
	 * <b><i>remove(int index)</i></b>
	 * <p>
	 * Removes node from list with specified index location.
	 * @param index
	 * 		location of node to be removed
	 * @return
	 * 		data that was removed
	 * @throws IndexOutOfBoundsException
	 * 		if index is < 0 or >= size
	 */
	public E remove(int index) {
		ListIterator<E> iter = listIterator(index);
		if (!iter.hasNext()) {
			throw new IndexOutOfBoundsException();
		}

		E oldData = iter.next();
		iter.remove();
		return oldData;
	}

	/**
	 * <b><i>get</i></b>
	 * <p>
	 * Get the data value at an index location.
	 *
	 * @param index
	 * 		The position of the element to return
	 * @return
	 * 		the data at the index location
	 * @throws IndexOutOfBoundsException
	 * 		if index is out of range
	 */
	public E get(int index) {
		ListIterator<E> iter = listIterator(index);
		E data = iter.next();
		return data;
	}

	/**
	 * <b><i>set</i></b>
	 * <p>
	 * Set the data value at an index location
	 * @param index
	 * 		the location of the item that will be overwritten
	 * @param newData
	 * 		the new data to overwrite the data at the index location
	 * @return
	 * 		the data value previously at the index location
	 * @throws IndexOutOfBoundsException
	 * 		if index is less than 0 or greater than or equal to size
	 */
	@Override
	public E set(int index, E newData) {
		ListIterator<E> iter = listIterator(index);
		E oldData = iter.next();
		iter.set(newData);
		return oldData;
	}

	/**
	 * <b><i>size</i></b>
	 * <p>
	 * Returns number of elements in list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * <b><i>indexOf</i></b>
	 * <p>
	 * Searches the linkedlist for first instance of specified data
	 * input parameter using a listIterator.
	 * <p>
	 * Note - calls Object.equals() UNLESS explicitly overwritten by
	 * class file of type of LinkedList set during initialization of list.
	 *
	 * @param inData
	 * 			data to search the linkedlist for
	 * @return
	 * 			index where data is located
	 * 			<br>
	 * 			-1 if data not found
	 */
	@Override
	public int indexOf(Object inData) {
		E data;
		ListIterator<E> iter = listIterator();

		while (iter.hasNext()) { //O(1) runtime if head.data.equals(inData)
			data = iter.next();

			if (data.equals(inData)) {
				return iter.previousIndex();
			}
		}
		return -1; //if not found
	}

	/**
	 * <b><i>isEmpty</i></b>
	 * <p>
	 * Checks if the linkedlist is empty
	 * @return
	 * 			True if head is null
	 * 			<br>
	 * 			False otherwise
	 */
	@Override
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	/**
	 * <b><i>clear</i></b>
	 * <p>
	 * Removes all elements in list by setting head and tail to null, and size to zero
	 */
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * <b><i>contains</i></b>
	 * <p>
	 * Iterates through list to find some object o
	 * passed to the method. If the object is found
	 * within the list, return true. If not, returns false.
	 *
	 * @param o
	 * 		object to be searched for within list
	 * @return
	 * 		true if object found in list
	 * 		<br>
	 * 		false if object not found in list
	 */
	@SuppressWarnings("unchecked") //for generic type-cast (OK to ignore)
	@Override
	public boolean contains(Object o) {
		o = (E) o; //triggers warning.

		ListIterator<E> iter = listIterator();
		E data;

		while (iter.hasNext()) {
			data = iter.next();
			if (data.equals(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <b><i>lastIndexOf</i></b>
	 * <p>
	 * Returns the index of the last occurrence of the specified element in this list
	 * <p>
	 * If the element is not in the list, returns -1
	 *
	 * @param o
	 * 		element to search the list for
	 * @return
	 * 		index location of last occurrence of element o
	 * 		<br>
	 * 		-1 if element not found
	 */
	@SuppressWarnings("unchecked") //from Object to E typecast
	@Override
	public int lastIndexOf(Object o) {
		o = (E) o; //generates warning

		ListIterator<E> iter = listIterator();
		int indexReturn = -1; //default if not overwritten

		E data;
		while (iter.hasNext()) {
			data = iter.next();

			if(data.equals(o)) {
				indexReturn = iter.previousIndex();
			}
		}
		return indexReturn;
	}

	/**
	 * <b><i>iterator</i></b>
	 * <p>
	 * Returns an iterator over the elements in this list in proper sequence
	 *
	 * @return
	 * 		a new iterator over the elements in the list in proper sequence
	 */
	@Override
	public Iterator<E> iterator() {
		return new DoubleListIterator(0);
	}

	/**
	 * <b><i>listIterator</i></b>
	 * <p>
	 * Returns a ListIterator over the elements in this list in proper sequence
	 *
	 * @return
	 * 		a new ListIterator over the elements in the list in proper sequence
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new DoubleListIterator(0);
	}


	/**
	 * <b><i>listIterator(index)</i></b>
	 * <p>
	 * Returns a ListIterator starting at a specified index location.
	 * <p>
	 * The first call to next will return the item at the passed index
	 * location. The first call to previous will return the item at the
	 * location of the passed index - 1.
	 *
	 * @param index
	 * 		index location to start the iterator at
	 * @return
	 * 		a new DoubleListIterator object that starts at index
	 * @throws IndexOutOfBoundsException
	 * 		if index passed < 0 or > size
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new DoubleListIterator(index);
	}

	/**
	 * <b><i>equals</i></b>
	 * <p>
	 * Equals checks if another DoublyLinkedList is equivalent
	 * to this linked list.
	 * <p>
	 * Note - calls Object.equals() for data inside nodes
	 * UNLESS @Override is completed for the equals() method
	 * within the <E> object, which is initialized when a
	 * DoublyLinkedList is created.
	 *
	 * @param other
	 * 			Other linkedlist to compare this list with
	 * @return
	 * 			TRUE if lists are equivalent
	 * 			<br>
	 * 			FALSE if lists are NOT equivalent
	 */
	public boolean equals(BVDoublyLinkedList<E> other) {
		Node<E> thisNode;
		Node<E> thatNode;

		if (this.size() != other.size()) {
			return false;
		} else if (this.tail.data != other.tail.data) { //to avoid O(n) runtime
			return false;
		}

		thisNode = head;
		thatNode = other.head;

		for (int i = 0; i < size; i++) {
			if (!(head.data.equals(thatNode.data))) {
				return false;
			}
			thisNode = thisNode.next;
			thatNode = thatNode.next;
		}
		return true;
	}

	/**
	 * <b>DoubleListIterator</b>
	 * <p>
	 * <i>Javadoc documentation inherited from the ListIterator
	 * interface, and as such, have been omitted from this .java file.</i>
	 * <p>
	 * The method implementation(s) follow the guidelines of the ListIterator
	 * interface, unless otherwise explicitly stated.
	 * @author Ben Vargas (adapted from Koffman and Wolfgang)
	 *
	 */
	private class DoubleListIterator implements ListIterator<E> {

		private Node<E> nextItem;
		private Node<E> lastItemReturned;
		private int index = 0;

		/**
		 * <b><i>Constructor for DoubleListIterator</b></i>
		 * <p>
		 * Default Constructor creates a ListIterator at
		 * the index specific by the parameter int passed
		 * to the constructor method.
		 * @param i
		 * 		index to set the initial place of the iterator at
		 */
		public DoubleListIterator(int i) {
			if (i < 0 || i > size) {
				throw new IndexOutOfBoundsException("Invalid index: " + i);
			}
			lastItemReturned = null;
			//if statement below prevents unnecessary O(n) runtime
			if (i == size) {
				index = size;
				nextItem = null;
			} else {
				//if index passed is 0 (start at head), loop does not run
				nextItem = head;
				for (index = 0; index < i; index++) {
					nextItem = nextItem.next;
				}
			}
		}

		/**
		 * <b><i>add</b></i>
		 * <p>
		 * Inserts object obj into the list just before the item that would be
		 * returned by the next call to next(), and after the item that would
		 * have been returned by a call to previous().
		 * <p>
		 * If previous() is called after add, the newly inserted object will
		 * be returned.
		 */
		@Override
		public void add(E obj) {
			//adding to empty list
			if (head == null) {
				head = new Node<E>(obj);
				tail = head;

			} else if (nextItem == head) {
				//inserting at head
				Node<E> newNode = new Node<E>(obj);
				newNode.next = nextItem;
				nextItem.prev = newNode;
				head = newNode;

			} else if (nextItem == null) {
				//inserting at tail
				Node<E> newNode = new Node<E>(obj);
				tail.next = newNode;
				newNode.prev = tail;
				tail = newNode;

			} else {
				//inserting into arbitrary middle location (not head, tail, or empty list)
				Node<E> newNode = new Node<E>(obj);
				Node<E> nodeBehindNew = nextItem.prev; //renamed for code clarity

				newNode.prev = nextItem.prev;
				nodeBehindNew.next = newNode;
				newNode.next = nextItem;
				nextItem.prev = newNode;
			}

			size++;
			index++;
			lastItemReturned = null;
		}

		/**
		 * <b><i>hasNext</b></i>
		 * <p>
		 * Returns true if next call to next() will not throw an exception
		 *
		 * @return
		 * 		true if next() will not throw an exception
		 * 		<br>
		 * 		false is next() will throw an exception
		 */
		@Override
		public boolean hasNext() {
			return nextItem != null;
		}

		/**
		 * <b><i>hasPrevious</b></i>
		 * <p>
		 * Returns true if next call to previous() will not throw an exception
		 *
		 * @return
		 * 		true if previous will not throw an exception
		 * 		<br>
		 * 		false if previous will throw an exception
		 */
		@Override
		public boolean hasPrevious() {
			return (nextItem == null && size != 0)
					|| nextItem.prev != null;
		}

		/**
		 * <b><i>next</b></i>
		 * <p>
		 * Returns next object and moves the iterator forward.
		 *
		 * @return
		 * 		data within object that is next (in front of the iterator)
		 * @throws NoSuchElementException
		 * 		if iterator is at the end of the list
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			lastItemReturned = nextItem;
			nextItem = nextItem.next;
			index++;
			return lastItemReturned.data;
		}

		/**
		 * <b><i>nextIndex</b></i>
		 * <p>
		 * Returns index of item that will be returned by next call to next()
		 * <br>
		 * If iterator is at the end, the list size is returned.
		 *
		 * @return
		 * 		index of item that will be returned upon call to next()
		 * 		<br>
		 * 		list size if the iterator is at the end of the list
		 *
		 */
		@Override
		public int nextIndex() {
			if (!hasNext()) {
				throw new IndexOutOfBoundsException(Integer.toString(index+1));
			}
			return index+1;
		}

		/**
		 * <b><i>previous</b></i>
		 * <p>
		 * Returns previous object and moves iterator backwards
		 *
		 * @return
		 * 		data inside previous node
		 * @throws NoSuchElementException
		 * 		if iterator is at beginning of list
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}

			if (nextItem == null) {
				nextItem = tail;
			}
			else {
				nextItem = nextItem.prev;
			}
			lastItemReturned = nextItem;
			index --;
			return lastItemReturned.data;
		}

		/**
		 * <b><i>previousIndex</b></i>
		 * <p>
		 * Returns the index of the item that will be returned by the next call to previous.
		 *
		 * @return
		 * 		Index of item that will be returned by next call to previous
		 * 		<br>
		 * 		-1 if iterator is at beginning of list.
		 */
		@Override
		public int previousIndex() {
			return index-1;
		}

		/**
		 * <b><i>remove</b></i>
		 * <p>
		 * Removes from the list the last element that was returned by next() or previous()
		 * <p>
		 * This call can only be made once per call to next or previous.
		 * It can be made only if add(E) has not been called after the last call to next or previous.
		 *
		 * @throws IllegalStateException
		 * 		If call to remove() is not preceded by a call to next() or previous()
		 */
		@Override
		public void remove() {
			if (lastItemReturned == null) {
				throw new IllegalStateException();
			}
			/**
			 * 3 Cases:
			 *
			 * 1. If head == tail (size of list is 1)
			 * 2. If previous() was last called
			 * 		a. Check if at head
			 * 		b. Check if at tail
			 * 		c. Default case
			 * 3. If next() was last called
			 * 		a. Check if at head
			 * 		b. Check if at tail
			 * 		c. Default case
			 */
			//CASE 1
			if (size == 1) {
				clear(); //sets size to 0
				lastItemReturned = null;
				return; //avoids size-- decrement at end of cases

			} else if (lastItemReturned == nextItem) { //CASE 2 (a, b, and c)
				if (lastItemReturned == head) {
					head = nextItem.next;
					head.prev = null;
					nextItem = head;

				} else if (lastItemReturned == tail) {
					tail = nextItem.prev;
					tail.next = null;
					nextItem = null;

				} else {
					Node<E> behindIterator = nextItem.prev;
					Node<E> twoNodesAhead = nextItem.next;
					//move links to 'delete' node (java will delete it eventually)
					twoNodesAhead.prev = behindIterator;
					behindIterator.next = twoNodesAhead;
					//advance iterator past node removed
					nextItem = nextItem.next;
				}

			} else if (lastItemReturned.next == nextItem) { //CASE 3 (a, b, and c)
				if (lastItemReturned == head) {
					head = nextItem;
					head.prev = null;

				} else if (lastItemReturned == tail) {
					tail = lastItemReturned.prev;
					tail.next = null;

				} else {
					nextItem.prev = lastItemReturned.prev;
					lastItemReturned.prev.next = nextItem;
				}
			}
			/** This code does not execute if size == 1 */
			size--;
			lastItemReturned = null;
		}

		/**
		 * <b><i>set</b></i>
		 * <p>
		 * Replaces last item returned from call to next or previous with obj.
		 *
		 * @throws IllegalStateException
		 * 		when call to set is not preceded by call to next or previous
		 */
		@Override
		public void set(E obj) {
			if (lastItemReturned == null) {
				throw new IllegalStateException();
			}
			lastItemReturned.data = obj;
		}
	}

	//Node inner static class (i.e., nested class for List)
	private static class Node<E> {
		private E data;
		private Node<E> next = null;
		private Node<E> prev = null;

		/**
		 * <b><i>Node Constructor (default)</b></i>
		 * <p>
		 * Creates a new node with a null next node pointer
		 * @param data
		 * 		holds data stored in Node
		 */
		private Node(E data) {
			this.data = data;
		}

		/**
		 * <b><i>Node Constructor (with param)</b></i>
		 * <p>
		 * Creates a new node that points contains a reference
		 * to the next node in the list and the previous node
		 *
		 * @param data
		 * 		holds data stored in Node
		 * @param nodeRefNext
		 * 		The node next of the new Node object
		 * @param nodeRefPrev
		 * 		The node previous of the new Node object
		 */
		private Node(E data, Node<E> nodeRefNext, Node<E> nodeRefPrev) {
			this.data = data;
			this.next = nodeRefNext;
			this.prev = nodeRefPrev;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Stub method to be implemented later
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Stub method to be implemented later
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Stub method to be implemented later
		return null;
	}
}
