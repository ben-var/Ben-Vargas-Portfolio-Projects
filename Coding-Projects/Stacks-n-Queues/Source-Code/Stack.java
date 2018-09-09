// package name redacted

/**
 * Interface for all Stack methods required in order
 * to be classified as a Stack data structure.
 * <p>
 * Methods include:
 * <br>
 * 1. push(E obj)
 * <br>
 * 2. pop()
 * <br>
 * 3. peek()
 * <br>
 * 4. empty()
 *
 * @author Ben Vargas
 *
 * @param <E>
 * 		generic data type to be instantiated upon constructor call
 */
public interface Stack<E> {
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
	public boolean empty();

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
	public E push(E obj);

	/**
	 * <b><i>pop</i></b>
	 * <p>
	 * Returns the object at the top of the stack and
	 * removes it
	 * @return
	 * 		item from top of stack that was removed
	 */
	public E pop();

	/**
	 * <b><i>peek</i></b>
	 * <p>
	 * Returns the item at the top of the stack without
	 * removing it.
	 * @return
	 * 		item currently at top of stack
	 */
	public E peek();


}
