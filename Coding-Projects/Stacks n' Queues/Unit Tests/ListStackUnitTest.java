// package name redacted

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Test;

import lab7.Stack;

/**
 * Standard JUnit tests for ListStack's methods.
 *
 * @author Ben Vargas
 */
public class ListStackUnitTest {
	@Test
	public void testEmpty() {
		Stack<Integer> empty = new ListStack<Integer>();
		Stack<Integer> full = new ListStack<Integer>();

		// testing empty called on empty stack
		Assert.assertTrue(empty.empty());

		// testing empty called on stack that is NOT empty
		full.push(1);
		Assert.assertTrue(!full.empty());
	}

	@Test
	public void testPeek() {
		Stack<Integer> empty = new ListStack<Integer>();
		Stack<Integer> full = new ListStack<Integer>();

		// testing return value of peek before and after pop is called
		full.push(1);
		full.push(2);

		int testTop1 = full.peek();
		full.pop();
		int testTop2 = full.peek();

		Assert.assertTrue(testTop1 == 2);
		Assert.assertTrue(testTop2 == 1);

		// testing peek called on empty stack
		boolean emptyThrown = false;
		try {
			empty.peek();
		} catch (EmptyStackException e) {
			emptyThrown = true;
		}

		Assert.assertTrue(emptyThrown);
	}

	@Test
	public void testPop() {
		Stack<Integer> hasElements = new ListStack<Integer>();

		hasElements.push(1);
		hasElements.push(2);
		hasElements.push(3);

		// testing return value of pop
		Assert.assertTrue(hasElements.pop() == 3);
		Assert.assertTrue(hasElements.pop() == 2);
		Assert.assertTrue(hasElements.pop() == 1);

		// testing pop called on empty stack
		boolean isItEmpty = false;
		try {
			hasElements.pop();
		} catch (EmptyStackException e) {
			isItEmpty = true;
		}

		Assert.assertTrue(isItEmpty);
	}

	@Test
	public void testPush() {
		Stack<Integer> stack = new ListStack<Integer>();

		int testInt1 = stack.push(1);
		int testInt2 = stack.push(2);
		int testInt3 = stack.push(3);
		// stack should appear as: (top) [3, 2, 1] (bottom)

		// testing push method return values
		Assert.assertTrue(testInt1 == 1);
		Assert.assertTrue(testInt2 == 2);
		Assert.assertTrue(testInt3 == 3);

		// testing that data inside stack is correct
		Assert.assertTrue(stack.peek() == 3);
		Assert.assertTrue(stack.pop() == 3);
		Assert.assertTrue(stack.peek() == 2);
	}
}
