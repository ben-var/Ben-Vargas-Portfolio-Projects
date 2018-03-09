import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class implements some (but not all) of the methods of the
 * Java ArrayList class.
 *
 * Class Invariant: Not all methods from the List java
 * interface have been implemented.
 *
 * @author Ben Vargas
 * @version 1.0
 *
 * @param <E>
 * 			Generic type that should be cast when instantiated.
 */
public class FirstArrayList<E> implements List<E> {
	//Data fields
	/** The default initial capacity */
	private static final int INITIAL_CAPACITY = 10;

	/** The underlying data array of FirstArrayList */
	private E[] theData;

	/** The current size */
	private int size = 0;

	/** The current capacity (includes empty space) */
	private int capacity = 0;

	//generic constructor
	@SuppressWarnings("unchecked") //Suppress because E[] is generic
	public FirstArrayList() {
		capacity = INITIAL_CAPACITY;
		theData = (E[]) new Object[capacity];
	}

	//efficient constructor
	@SuppressWarnings("unchecked") //Suppress because E[] is generic
	public FirstArrayList(int knownCapacity) {
		capacity = knownCapacity;
		theData = (E[]) new Object[capacity];
	}

	/**
	 * Automatically converts the list of objects to a string if
	 * code calls the object as a String type.
	 *
	 * Should be overwritten in implementation for better formatting.
	 */
	public String toString() {
		String string = "";
		for(int i = 0; i < size; i++) {
				string += "["+this.get(i)+"]";
		}

		return string;
	}

	/**
	 * Gets the current size of the FirstArrayList
	 * @return size
	 * 			the current length of the FirstArrayList
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds a reference to anEntry at the end of the
	 * FirstArrayList. Always returns true
	 * @param anEntry
	 * 			data to be added to the FirstArrayList
	 * @return
	 * 			always returns true because size is dynamic
	 */
	public boolean add(E anEntry) {
		if(size >= capacity) {
			reallocate();
		}
		theData[size] = anEntry;
		size++;

		return true;
	}

	/**
	 * Adds a reference to anEntry, inserting it before
	 * the item at position index.
	 * @param index
	 * 			ordered index location (0 <= n < size) referencing
	 * 			location of data in FirstArrayList
	 * @param anEntry
	 * 			data to be added to the FirstArrayList
	 */
	public void add(int index, E anEntry) {
		//ensuring valid index was passed
		if(index < 0 || index > size) {
			//unchecked exception
			throw new ArrayIndexOutOfBoundsException(index);
		}

		//expands array if at capacity
		if(size >= capacity) {
			reallocate();
		}

		//shifting data and leaving space for added data
		for(int i = size; i > index; i--) {
			theData[i] = theData[i-1];
		}

		//inserting item (must be done after shift)
		theData[index] = anEntry;
		size++;
	}

	/**
	 * Doubles the current capacity of the array inside the
	 * FirstArrayList object to make more room.
	 *
	 * Original data is copied identically for non-empty indexes.
	 */
	public void reallocate() {
		capacity *= 2;
		theData = Arrays.copyOf(theData, capacity);
	}

	/**
	 * Returns a reference to the element at a position index
	 * @param index
	 * 			ordered index location (0 <= n < size) referencing
	 * 			location of data in FirstArrayList
	 * @return previousIndex
	 * 			returns reference to element at position index
	 */
	public E get(int index) {
		//ensuring valid index was passed
		if(index < 0 || index > size) {
			//unchecked exception
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return theData[index];
	}

	/**
	 * Sets the element at position index to reference anEntry
	 * @param index
	 * 			ordered index location (0 <= n < size) referencing
	 * 			location of data in FirstArrayList
	 * @param newData
	 * 			data to overwrite oldData that was stored before
	 * 			set() method call
	 * @return oldData
	 * 			data that is overwritten by newValue
	 */
	public E set(int index, E newData) {
		//ensuring valid index was passed
		if(index < 0 || index > size)	{
			//unchecked exception
			throw new ArrayIndexOutOfBoundsException(index);
		}
		E oldData = theData[index];
		theData[index] = newData;

		return oldData;
	}

	/**
	 * Searches for target and returns the position of the first
	 * occurrence, or -1 if target is not in FirstArrayList
	 * @param target
	 * 			data that will be searched for in FirstArrayList
	 * @return
	 * 			index of target data found in FirstArrayList
	 * 			<br>
	 * 			OR
	 * 			<br>
	 * 			-1 if target is not found
	 */
	public int indexOf(Object target) {
		for(int i = 0; i < size; i++) {
			if(theData[i] == target) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns and removes the item at the position index
	 * and shifts the items that follow it to fill the vacated space
	 * @param index
	 * @return
	 * 			data removed
	 */
	public E remove(int index) {
		//ensuring valid index was passed
		if(index < 0 || index > size) {
			//unchecked exception
			throw new ArrayIndexOutOfBoundsException(index);
		}
		//getting oldData for return before overwrite
		E oldData = theData[index];

		//overwriting data from index passed --> size
		for(int i = index + 1; i < size; i++)	{
			theData[i-1] = theData[i];
		}
		size--;
		return oldData;
	}

	/**
	 * Searches the entire FirstArrayList for data input as a
	 * parameter.
	 * @param data
	 * 			data to be searched for
	 * @return
	 * 			true if FirstArrayList contains param data
	 *			<br>
	 * 			false if FirstArrayList does NOT contain param data
	 */
	public boolean contains(Object data) {
		for(E element : theData) {
			if(data.equals(element)) {
				return true;
			}
		}
		return false;
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
	public void clear() {
		// TODO Stub method to be implemented later
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Stub method to be implemented later
		return false;
	}

	@Override
	public boolean isEmpty() {
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
	public boolean remove(Object o) {
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
