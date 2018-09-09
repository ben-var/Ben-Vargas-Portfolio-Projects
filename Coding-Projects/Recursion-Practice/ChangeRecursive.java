import java.util.ArrayList;
import java.util.List;

/**
 * <b><i><u>ChangeRecursive</u></i></b>
 * <p>
 * Primary function of this class is to store a change value (option)
 * and output all unique coin combinations given the input of a double
 * that represents a currency amount in dollars.
 *
 * The List is <b>not</b> stores as an instance variable currently, but
 * can be modified with little effort to do so.
 *
 * @author Ben Vargas
 * @version 1.0
 */
public class ChangeRecursive {

	// INSTANCE VARIABLES

	private double change;

	/**
	 * <b><i>setChange</i></b>
	 * <p>
	 * Setter for change instance variable (in dollars)
	 * @param inChange
	 * 		dollar amount passed to change instance variable
	 */
	public void setChange(double inChange) {
		if (inChange < 0) {
			throw new IllegalArgumentException("For input: " + inChange);
		}
		this.change = inChange;
	}

	/**
	 * <b><i>setAll</i></b>
	 * <p>
	 * Standard setAll method that sets all variables to given input(s)
	 * @param inChange
	 * 		dollar amount passed to change instance variable
	 */
	public void setAll(double inChange) {
		if (inChange < 0) {
			throw new IllegalArgumentException("For input: " + inChange);
		}
		this.change = inChange;
	}

	/**
	 * <b><i>ChangeRecursive (Default Constructor)</i></b>
	 * <p>
	 * Default Constructor for ChangeRecursive object
	 */
	public ChangeRecursive() {
		this.change = .25;
	}

	/**
	 * <b><i>ChangeRecursive (Standard Constructor)</i></b>
	 * <p>
	 * Standard Constructor for ChangeRecursive object. Parameters
	 * include values for each instance variable.
	 *
	 * @param inChange
	 * 		dollar amount passed to change instance variable
	 */
	public ChangeRecursive(double inChange) {
		if (inChange < 0) {
			throw new IllegalArgumentException("For input: " + inChange);
		}
		setAll(inChange);
	}

	/**
	 * <b><i>getChange()</i></b>
	 * <p>
	 * Getter for change instance variable
	 * @return
	 * 		value of change in dollars
	 */
	public double getChange() {
		return this.change;
	}

	/**
	 * <b><i>giveCombinationsRecursive</i></b>
	 * <p>
	 * Primary (helper) method of the ChangeRecursive object.
	 * <br>
	 * This method will take as input a change amount (in dollars) and prints
	 * out every possible coin combination of the provided input amount.
	 * <p>
	 * <i><b>Pre-Condition:</b> instance variables for quarters, dimes, nickels and pennies quantity (ints q, p, n, d)
	 * and empty 2-dimensional List (List within a List), with each inner list representing a unique combination of coins
	 * initialized at method call and passed to each recursive call for updating.</i>
	 * <p>
	 * <b><u>Algorithm:</b></u>
	 * <br>
	 * 1. Checks for initial conditions with IF statement
	 * <br> &emsp;
	 *
	 * 1a. IF initial conditions (first run), multiply change by 100 to get change amount in cents,
	 * 		and set p to change (now in amount of cents)
	 * <br>
	 * <b>RECURSIVE CASES</b>
	 * <br>
	 * 2. If p >= 25 cents, recursively call method, adding 1 to q and subtract 25 p's
	 * <br>
	 * 3. If p >= 10 cents, recursively  call method, adding 1 to d and subtract 10 p's
	 * <br>
	 * 4. If p >= 5 cents, recursively  call method, adding 1 to n and subtract 5 p's
	 * <br>
	 * <b>END RECURSIVE CASES</b>
	 * <br>
	 * 6. Create new ArrayList of ints and add q, d, n, and p respectively (size is now 4)
	 * <br>
	 * 7. IF outer ArrayList does not contain new ArrayList, add new ArrayList as a combination
	 * <br>
	 * 8. <b>BASE CASE</b> - if p == change (i.e., very first method call is back in control)
	 * <br> &emsp;
	 * 8a. print final output confirmation message and # of combinations found
	 *
	 * @param change
	 * 		input in dollar(s) that will be broken into coin combinations
	 * @param q
	 * 		quantity of quarters in the current combination (starts at 0)
	 * @param d
	 * 		quantity of dimes in the current combination (starts at 0)
	 * @param n
	 * 		quantity of quarters in the current combination (starts at 0)
	 * @param p
	 * 		quantity of quarters in the current combination (starts at 0)
	 * @param combinations
	 * 		outer List that stores all unique combinations
	 */
	private void giveCombinationsRecursive(double change, int q, int d,
							int n, int p, List<ArrayList<Integer>> combinations) {
		if(q == 0 && d == 0 && n == 0 && p == 0) {
			change = change*100;
			p = (int) change;
		}

		// recursive cases (if P can be represented as a combination of larger coins)
		if (p >= 25) {
			giveCombinationsRecursive(change, q+1, d, n, p-25, combinations);
		}
		if (p >= 10) {
			giveCombinationsRecursive(change, q, d+1, n, p-10, combinations);
		}
		if (p >= 5) {
			giveCombinationsRecursive(change, q, d, n+1, p-5, combinations);
		}

		// creating new subset of integers (each subset represents a combination)
		ArrayList<Integer> combination = new ArrayList<Integer>();
		combination.add(q);
		combination.add(d);
		combination.add(n);
		combination.add(p);

		// adding subset integers to List if combination is unique entry
		if(!(combinations.contains(combination))) {
			System.out.println("~ Combination #" + (combinations.size()+1) + " ~");
			System.out.print(" [Q:" + q);
			System.out.print(" D:" + d);
			System.out.print(" N:" + n);
			System.out.println(" P:" + p + "]\n");

			combinations.add(combination);
		}

		// base case (after all recursive cases have ended the first method call is sent here)
		if(p == change) {
			change = change * .01;
			System.out.println("FINISHED! All combinations have been printed.\n");
			System.out.printf(combinations.size() + " combination(s) were found for INPUT='$%.2f'\n", change);
		}
	}

	/**
	 * <b><i>giveCombinationsRecursive</i></b>
	 * <p>
	 * Wrapper class for helper method. This method will use the change instance
	 * variable stored in a ChangeRecursive object and pass if to the giveCombinationsRecursive
	 * recursive helper method.
	 * <p>
	 * <b>q, d, n,</b> and <b>p</b> represent initial quantity of quarters, dimes, nickels and pennies respectively.
	 * <p>
	 * <b>combinations</b> is an empty 3 dimensional List (ArrayList is the inner List) that will store
	 * all combinations found in the helper method (giveCombinationsRecursive).
	 */
	public void getCombinations() {
		if (this.change < 0) {
			throw new IllegalArgumentException("For input: " + this.change);
		}
		// q, d, n, and p are initially empty or 0
		int q = 0, d = 0, n = 0, p = 0;
		List<ArrayList<Integer>> combinations = new ArrayList<ArrayList<Integer>>();
		// empty parameters are hidden for user-friendliness
		giveCombinationsRecursive(this.change, q, d, n, p, combinations);
	}

	/**
	 * <b><i>giveCombinationsRecursive(double inChange)</i></b>
	 * <p>
	 * Wrapper class for helper method. This method will use pass the inChange double
 	 * to the giveCombinationsRecursive recursive helper method.
	 * <p>
	 * <b>q, d, n,</b> and <b>p</b> represent initial quantity of quarters, dimes, nickels and pennies respectively.
	 * <p>
	 * <b>combinations</b> is an empty 3 dimensional List (ArrayList is the inner List) that will store
	 * all combinations found in the helper method (giveCombinationsRecursive).
	 *
	 * @param inChange
	 * 		amount in dollars to be passed to recursive method
	 */
	public void getCombinations(double inChange) {
		if (inChange < 0) {
			throw new IllegalArgumentException("For input: " + inChange);
		}
		// q, d, n, and p are initially empty or 0
		int q = 0, d = 0, n = 0, p = 0;
		List<ArrayList<Integer>> combinations = new ArrayList<ArrayList<Integer>>();
		// empty parameters are hidden for user-friendliness
		giveCombinationsRecursive(inChange, q, d, n, p, combinations);
	}
}
