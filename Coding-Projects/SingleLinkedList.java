import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Simple Singly LinkedList structure that implements some
 * methods from the List java interface.
 *
 * Class Invariant: Iterator and other methods using Collection
 * parameters have not been implemented as of 9/17/2017. As
 * such, some methods from the List<E> interface are stub
 * methods.
 *
 * @author Ben Vargas; some parts from Koffman & Wolfgang
 * @version 1.1
 * @param <E>
 * 			Generic type to be set during initialization of list
 */
public class SingleLinkedList<E> implements List<E>
{
	private Node<E> head = null;
	private int size = 0;

	/**
	 * Auto-converts a this SingleLinkedList to String when called as a String.
	 */
	@Override
	public String toString()
	{
		if(head == null)
		{
			return "NULL";
		}
		Node<E> nodeRef = head;
		StringBuilder result = new StringBuilder();

		while(nodeRef.next != null)	{
			result.append(nodeRef.data);

			if(nodeRef.next != null) {
				result.append(" ==> ");
			}
			nodeRef = nodeRef.next;
		}

		result.append(nodeRef.data);
		return result.toString();
	}

	//CONSTRUCTORS

	//default
	public SingleLinkedList() {
		//do nothing, since variables already initialized at declaration
	}

	//constructor with one node
	public SingleLinkedList(E data) {
		add(data);
	}

	//constructor to copy a linkedlist into a new list (deep copy of data)
	public SingleLinkedList(SingleLinkedList<E> other) {
		head = other.head;

		if(head != null) {
			Node<E> temp = head;

			while(temp.next != null) {
				temp.next = new Node<E>(temp.next.data) ;
			}
		}
	}

	/**
	 * Adds a reference to data at the end of the list.
	 * @param data
	 * 			data to be input into the list as a node
	 * @return
	 * 			always returns true due to nature of LinkedList list types
	 */
	public boolean add(E data) {
		Node<E> nodeRef = head;
		if(head == null) {
			addFirst(data);
		} else {
			while(nodeRef.next != null) {
				nodeRef = nodeRef.next;
			}
			addAfter(nodeRef, data);
		}
		return true; //always returns true, because Linked List can always be added to
	}

	/**
	 * Adds a reference to data (a node), inserting it before the position
	 * index.
	 * @param index
	 * 			location (sequential order) at which data will be placed
	 * @param data
	 * 			data to be inserted into the list as a node
	 */
	public void add(int index, E data) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		if(index == 0) {
			addFirst(data);
		}	else {
			Node<E> node = getNode(index-1);
			addAfter(node, data);
 		}

	}

	/**
	 * Helper method that adds a new node to the beginning of a LinkedList
	 * @param data
	 * 			data stored in new node added to list
	 */
	private void addFirst(E data) {
		Node<E> temp = new Node<E>(data, head);
		head = temp;
		size++;
	}

	/**
	 * Helper method that adds a new node to after a specified node to
	 * the list.
	 * @param node
	 * 			node at which new node will be placed after
	 * @param data
	 * 			data to be inserted into the list
	 */
	private void addAfter(Node<E> node, E data) {
		Node<E> temp = new Node<E>(data, node.next);
		node.next = temp;
		size++;
	}

	/**
	 * Removes specified data from linked list and
	 * returns true if successful.
	 * @param data
	 * 			data to be searched for and removed from list
	 * @return
	 * 			true if data found in list
	 * 			<br>
	 * 			false if data not found
	 */
	@Override
	public boolean remove(Object data) {
		int index = indexOf(data); //-1 if not found

		if(index < 0 || index > size || head == null) {
			return false; //if not found
		}
		//if data is found
		if(head.data == data) {
			removeFirst();
		}	else {
			removeAfter(getNode(index - 1));
		}

		return true; //if got to this line, item was found and removed
	}

	/**
	 * Removes node from list with specified index location.
	 * @param index
	 * 			location of node to be removed
	 * @return
	 * 			data that was removed
	 * @throws
	 * 			IndexOutOfBoundsException if index is out of range
	 */
	public E remove(int index) {
		E oldData;

		//if invalid index
		if(index <  0 || index > size || head == null) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		//if data stored in linkedlist
		if(head.data == getNode(index).data) {
			oldData = this.removeFirst();
		}	else {
			oldData = removeAfter(getNode(index - 1));
		}

		return oldData;
	}

	/**
	 * Remove the node after a given node
	 * @param node
	 * 			The node before the one to be removed
	 * @return
	 * 			The data from the removed node, or null
	 * 			if there is no node to remove
	 */
	private E removeAfter(Node<E> node) {
		Node <E> temp = node.next;
		if(temp != null) {
			node.next = temp.next;
			size--;
			return temp.data;
		}
		return null;
	}

	/**
	 * Remove the first node from the list
	 * @return
	 * 			The removed node's data or null if the
	 * 			list is empty.
	 */
	private E removeFirst() {
		Node<E> temp = head;
		if(head != null) {
			head = head.next;
		}
		//Return data at old head or null if list is empty
		if(temp != null) {
			size--;
			return temp.data;
		}

		return null;
	}

	/**
	 * Get the data value at an index location.
	 * @param index
	 * 			The position of the element to return
	 * @return
	 * 			the data at the index location
	 * @throws
	 * 			IndexOutOfBoundsException if index is out of range
	 */
	public E get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		Node<E> node = getNode(index);

		return node.data;
	}

	/**
	 * Set the data value at an index location
	 * @param index
	 * 			The position of the item to change
	 * @param newData
	 * 			The new data to override the index location with in list
	 * @return
	 * 			The data value previously at the index location
	 * @throws
	 * 			IndexOutOfBoundsException if index is out of range
	 */
	public E set(int index, E newData) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		Node<E> node = getNode(index);
		E result = node.data;
		node.data = newData;

		return result;
	}

	/** returns current size of linkedlist */
	public int size() {
		return size;
	}

	/**
	 * Iterates through linkedlist in order to find a node at
	 * a specific index location.
	 * <p>
	 * Note - if index above size passe
	 * @param index
	 * 			location to search list for to get return node
	 * @return
	 * 			node at location index
	 */
	private Node<E> getNode(int index) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		Node<E> node = head;
		for(int i = 0; i < index && node != null; i++) {
			node = node.next;
		}
		return node;
	}

	/**
	 * Searches a linkedlist for a specified data input parameter.
	 * <p>
	 * Note - calls Object.equals() UNLESS overwritten by
	 * type of LinkedList set during initialization of list.
	 * @param data
	 * 			data to search the linkedlist for
	 * @return
	 * 			index where data is located
	 * 			<br>
	 * 			OR
	 * 			<br>
	 * 			-1 if data not found
	 */
	public int indexOf(Object inData) {
		int index = 0;
		Node<E> node = head;

		while(node != null) {
			if(node.data.equals(inData)) {
				return index;
			}
			index++;
			node = node.next;
		}
		return -1;
	}

	/**
	 * Checks if a linkedlist is empty
	 * @return
	 * 			True if head is null
	 * 			<br>
	 * 			False otherwise
	 */
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		return false;
	}

	/** simply sets head to null. Old nodes deleted automatically by java. */
	public void clear() {
		head = null;
	}
	/**
	 * Equals checks if another SingleLinkedList is equivalent
	 * to this linked list.
	 * <p>
	 * Note - calls Object.equals() for data inside nodes
	 * UNLESS @Override is completed for the equals() method
	 * within the <E> object, which is initialized when a
	 * SingleLinkedList is created.
	 * @param other
	 * 			Other linkedlist to compare this list with
	 * @return
	 * 			TRUE if lists are equivalent
	 * 			<br>
	 * 			FALSE if lists are NOT equivalent
	 */
	public boolean equals(SingleLinkedList<E> other) {
		Node<E> thisNode;
		Node<E> thatNode;

		if(this.size() != other.size()) {
			return false;
		}
		thisNode = head;
		thatNode = other.head;
		while(thisNode != null && thatNode != null)	{
			if(!(head.data.equals(thatNode.data))) {
				return false;
			}
			thisNode = thisNode.next;
			thatNode = thatNode.next;
		}
		return true;
	}

	//Node inner static class (i.e., nested class for List)
	private static class Node<E> {
		private E data;
		private Node<E> next = null;

		/**
		 * Creates a new node with a null next node pointer
		 * @param data
		 * 				holds data stored in Node
		 */
		private Node(E data) {
			this.data = data;
			this.next = null;
		}

		/**
		 * Creates a new node that points to another node
		 * @param data
		 * 				holds data stored in Node
		 * @param nodeRef
		 * 				The node referenced by this new node
		 */
		private Node(E data, Node<E> nodeRef)	{
			this.data = data;
			this.next = nodeRef;
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
	public boolean contains(Object o) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Stub method to be implemented later
		return null;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Stub method to be implemented later
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Stub method to be implemented later
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Stub method to be implemented later
		return null;
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
