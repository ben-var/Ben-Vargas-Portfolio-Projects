// package name redacted

import java.util.List;
import java.util.EmptyStackException;

/** NOTE - to run this code, need to modify these import statements (they are deprecated) */
import hw3.SingleLinkedList;
import lab7.Stack;

/**
 * Another implementation of a stack using my own code from
 * HW3 (i.e., SingleLinkedList). This was additional work
 * for additional practice and testing of the data structures
 * I have written so far as of 10/3/2017.
 *
 * @author Ben Vargas
 * @version 1.0
 *
 * @param <E>
 * 		Generic class set at stack instantiation.
 */
public class ListStack<E> implements Stack<E> {

	private List<E> theData;

	// simple toString to avoid errors when called as string
	public String toString() {
		return theData.toString();
	}

	/**
	 * <b><i>Default Constructor</i></b>
	 * <p>
	 * Creates empty stack with a SingleLinkedList structure
	 */
	public ListStack() {
		theData = new SingleLinkedList<E>();
	}

	/**
	 * <b><i>empty</i></b>
	 * <p>
	 * Checks to see if a stack is empty, and returns a
	 * boolean.
	 * @return
	 * 		<b>true</b> if stack is empty
	 * 		<br>
	 * 		<b>false</b> if stack is not empty
	 */
	@Override
	public boolean empty() {
		return theData.isEmpty();
	}

	/**
	 * <b><i>push</i></b>
	 * <p>
	 * Pushes an item onto the top of the stack
	 * and returns the item pushed.
	 * @param obj
	 * 		data that is to be pushed onto top of stack
	 * @return
	 * 		data that was pushed (equivalent to param)
	 */
	@Override
	public E push(E obj) {
		theData.add(0, obj);
		return obj;
	}

	/**
	 * <b><i>pop</i></b>
	 * <p>
	 * Returns the object at the top of the stack and
	 * removes it
	 * @return
	 * 		item from top of stack that was removed
	 */
	@Override
	public E pop() {
		if (this.empty()) {
			throw new EmptyStackException();
		}
		return theData.remove(0);
	}

	/**
	 * <b><i>peek</i></b>
	 * <p>
	 * Returns the item at the top of the stack without
	 * removing it.
	 * @return
	 * 		item currently at top of stack
	 */
	@Override
	public E peek() {
		if (this.empty()) {
			throw new EmptyStackException();
		}
		return theData.get(0);
	}

}
