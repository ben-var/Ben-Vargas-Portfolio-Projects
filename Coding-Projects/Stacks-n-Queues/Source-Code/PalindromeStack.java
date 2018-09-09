// package name redacted

import lab7.ArrayStack;
import lab7.Stack;

/**
 * <b><i><u>PalindromeStack</u></i></b>
 * <p>
 * This program will test whether a string is a palindrome or not.
 * The primary structure used for this function is a stack.
 * <p>
 * <u>Algorithm:</u>
 * <br>
 * 1. Declare and initialize set of positive test cases
 * <br>
 * 2. Print **checkPalindrome() results on each case
 * <br>
 * 3. Declare and initialize set of negative test cases
 * <br>
 * 4. Print **checkPalindrome() results on each case
 * <p>
 * <u>**Algorithm for checkPalindrome(String s):</u>
 * <br>
 * 1. Remove case sensitive by setting string to lower case
 * <br>
 * 2. Remove all spaces and punctuation from string
 * <br>
 * 3. If blank string is passed (or only punctuation/spaces), return false.
 * 	  This avoids a false positive.
 * <br>
 * 4. Instantiate two stacks
 * <br>
 * 5. Push each character of the passed string into stack1
 * <br>
 * 6. Pop half of stack1, and push values into stack2 (size from String.length())
 * <br>
 * 7. IF string has odd number of alphabetical characters, pop from stack1
 * <br>
 * 8. WHILE neither stack is empty, pop and compare each character stored.
 * <br>
 * 9. IF set of characters popped are not equal, return false
 * <br>
 * 10. If no false return value reached, return true
 *
 * @author Ben Vargas
 * @version 1.0
 */
public class PalindromeStack {

	public static void main(String[] args) {

		// PALINDROME TEST CASES (Strings)

		/**
		 * POSITIVE TEST CASES
		 *
		 * 1. case of palindrome with uppercase and odd # of alphanumeric chars
		 * 2. case of palindrome with uppercase, spaces, and punctuation, and numbers
		 * 3. case of palindrome with uppercase, spaces, and even # of alphanumeric chars
		 * 4. case of palindrome with numbers
		 */
		String oddPalin = "RaceCar";
		String hasSpacePalin = " Race, Car";
		String evenPalin = "Never Odd Or Even";
		String palinWithNum = "1racecar1";

		System.out.println("Next 4 Cases should result in TRUE return value:");
		System.out.println(checkPalindrome(oddPalin));
		System.out.println(checkPalindrome(hasSpacePalin));
		System.out.println(checkPalindrome(evenPalin));
		System.out.println(checkPalindrome(palinWithNum));

		/**
		 * NEGATIVE TEST CASES
		 *
		 * 1. case of non-palindrome with punctuation, integers and even # of alphanumeric chars
		 * 2. case of non-palin with odd # of chars
		 * 3. case of empty string
		 * 4. case of palindrome with numbers that result in a non-palindrome
		 */
		String notPalin = "Supercalifragilisticexpialidocious!231!!";
		String oddNumNotPalin = "odd";
		String blank = "";
		String palinRuined = "3racecar2";

		System.out.println("\nNext 4 Cases should result in FALSE return value:");
		System.out.println(checkPalindrome(notPalin));
		System.out.println(checkPalindrome(oddNumNotPalin));
		System.out.println(checkPalindrome(blank));
		System.out.println(checkPalindrome(palinRuined));
	}

	/**
	 * <b><i>checkPalindrome</i></b>
	 * <p>
	 * Takes as input a string and determined whether the string
	 * is a palindrome. Spaces, and punctuation will be removed
	 * from the string if the string contains them. Numbers are
	 * treated as valid characters. Returns boolean for palindrome
	 * test.
	 *
	 * @param s
	 * 		String that will be tested for palindrome
	 * @return
	 * 		TRUE if s is a palindrome
	 * 		<br>
	 * 		FALSE if s is NOT a palindrome
	 */
	public static boolean checkPalindrome(String s) {
		s = s.toLowerCase(); // handling errors due to case sensitivity.

		// REGEX statements to remove extraneous characters

		s = s.replaceAll(" ", ""); // locates and replaces all spaces with null char (i.e., "")
		s = s.replaceAll("\\p{P}", ""); // locates and replaces all punctuation chars w/ null chars

		//if string is blank, cannot be palindrome - this avoids a false positive
		if(s.length() == 0) {
			return false;
		}

		/**
		 * Testing both ArrayStack and ListStack to ensure
		 * both structures have equivalent behaviors as set
		 * forth in the Stack<E> interface.
		 *
		 * The checkPalindrome method has been tested with x2 ListStacks
		 * and with x2 ArrayStacks, and was also verified.
		 */
		Stack<Character> charsFirst = new ListStack<Character>();
		Stack<Character> charsSecond = new ArrayStack<Character>();

		// adding to stack1; last letter of the string at topOfStack
		for (int i = 0; i < s.length(); i++) {
			charsFirst.push(s.charAt(i));
		}

		// popping half (rounded down) of stack one into stack 2
		for (int i = 0; i < (s.length() / 2); i++) {
			charsSecond.push(charsFirst.pop());
		}

		// if odd num of elements in stack 1
		if (s.length() % 2 != 0) {
			charsFirst.pop();
		}

		// testing that each element of stack 1 equals each in stack 2
		while (!charsFirst.empty() && !charsSecond.empty()) {
			if(!charsFirst.pop().equals(charsSecond.pop())) {
				return false;
			}
		}

		// if code is reached, palindrome is confirmed.
		return true;
	}
}
