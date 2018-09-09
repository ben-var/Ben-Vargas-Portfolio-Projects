// package name redacted

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/**
 * <b><i><u>ArrayStack</u></i></b>
 * <p>
 * Simple stack object based off an ArrayList.
 * <br>
 * Data should be added via the push() method,
 * and removed via the pop() method.
 * <p>
 * Data flows via <b>FIFO</b> format <i>(i.e., first-in, first-out)</i>
 *
 * @author Ben Vargas
 * @version 1.0
 *
 * @param <E>
 * 		Generic type to be initialized upon creation
 * 		of a new stack.
 */
public class ArrayStack<E> implements Stack<E> {

	//Stack has underlying data structure of an ArrayList
	private List<E> theData;

	//simple toString to avoid errors when called as string
	public String toString() {
		return theData.toString();
	}

	/**
	 * <b><i>Default Constructor</i></b>
	 * <p>
	 * Creates empty stack with initial capacity of 10
	 */
	public ArrayStack() {
		theData = new ArrayList<E>();
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
	 * <b><i>peek</i></b>
	 * <p>
	 * Returns the item at the top of the stack without
	 * removing it.
	 * @return
	 * 		item currently at top of stack
	 */
	@Override
	public E peek() {
		if (empty()) {
			throw new EmptyStackException();
		}
		return theData.get(theData.size() - 1);
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
		if (empty()) {
			throw new EmptyStackException();
		}
		return theData.remove(theData.size() - 1);
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
		theData.add(obj);
		return obj;
	}

	/**
	 * Simple main per instructions of lab. Detailed testing
	 * performed via JUnit testing.
	 * <p>
	 * Testing is within the ArrayStackUnitTest.java file
	 * <p>
	 * <b>Simple Algorithm:</b>
	 * <br>
	 * 1. Instantiate new ArrayStack
	 * <br>
	 * 2. Test that empty() prints true
	 * <br>
	 * 3. Push values to stack
	 * <br>
	 * 4. Test that empty() prints false
	 * <br>
	 * 5. Peek value of stack to see if top is last item pushed
	 * <br>
	 * 6. Pop value of stack until stack is empty and print out reversed order
	 */
	public static void main(String[] args) {
		ArrayStack<Integer> stack = new ArrayStack<Integer>();

		System.out.println("Result of empty() called on"
								+ " empty stack: " + stack.empty() + "\n");

		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}

		System.out.println("Result of empty() called on"
								+ " full stack: " + stack.empty() + "\n");

		System.out.println("Top of stack after adding 0->9 to "
								+ "stack: " + stack.peek() + "\n");

		System.out.println("Printing stack from top to bottom w/pop():");
		System.out.println("---------");

		while (!stack.empty()) {
			System.out.println(stack.pop());
		}
	}
}
