// package name redacted

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Polynomial object stores Terms in a ArrayList data structure.
 * <p>
 * Sorting is automatically applied upon adding a new term to the
 * polynomial, as well as addition of like terms.
 *
 * Class Invariant: The polynomial sorting algorithms
 * below treat negative exponents as smaller than constant
 * terms.
 *
 * @author Ben Vargas
 * @version 1.0
 */
public class Polynomial {
	private ArrayList<Term> poly;

	/**
	 * Automatic String formatting for Polynomial objects
	 * <p>
	 * Runs through entire ArrayList and
	 * adds each term to return String.
	 */
	public String toString() {
		String returnString = "";
		for(int i = 0; i < poly.size(); i++) {
			// last term
			if(i == (poly.size() - 1)) {
				returnString += poly.get(i);
			}
			else {
				returnString += poly.get(i) + " + ";
			}
		}
		return returnString;
	}

	/** Calls ArrayList.size() for private ArrayList poly */
	public int getSize() {
		return poly.size();
	}

	/** Calls ArrayList.get() for private ArrayList poly */
	public Term getTerm(int index) {
		return poly.get(index);
	}

	/** Constructor for fixed number of terms pre-determined */
	public Polynomial(int numOfTerms) {
		if(numOfTerms < 0) {
			System.out.println("A polynomial can't have a negative # of terms!");
			System.out.println("Instead of boring quitting, i'll make your number positive :-)");
			numOfTerms = Math.abs(numOfTerms);
		}
		if(numOfTerms == 0)	{
			System.out.println("Sorry, you must have at least 1 term!");
			numOfTerms++;
		}
		for(int i = 0; i < numOfTerms; i++)	{
			Term termToAdd = new Term();
			addTerm(termToAdd);
		}
	}

	/** Default constructor has dynamic polynomial size, set by user input */
	public Polynomial() {
		poly = new ArrayList<Term>();
		promptUserInput();
	}

	/**
	 * Deep copy constructor for a Polynomial object
	 * <p>
	 * Size of other polynomial passed to ArrayList
	 * constructor for efficient copying.
	 */
	public Polynomial(Polynomial other) {
		poly = new ArrayList<Term>(other.getSize());
		for(int i = 0; i < other.getSize(); i++) {
			poly.add(new Term(other.getTerm(i)));
		}
	}

	/**
	 * Returns degree of polynomial, based of degree of term
	 * with the highest exponent
	 * @return
	 * 			the degree of the polynomial
	 */
	public int getDegree() {
		if(poly.size() == 0) {
			return 0;
		}
		return poly.get(0).getExponent();
	}

	/**
	 * Prompts user to enter in data for a polynomial object, then creates
	 * a polynomial object with attributes as entered by the user.
	 * <p>
	 * Note - In future iterations, would like to have the program
	 * handle errors more gracefully than simply exiting the program.
	 *
	 * @return
	 * 			returns TRUE if input was successful
	 * 			returns FALSE if input was NOT successful
	 */
	public boolean promptUserInput() {
		@SuppressWarnings("resource") // Scanner not closed to avoid System.in bugs
		Scanner in = new Scanner(System.in);

		try	{
			int numOfTerms;

			System.out.print("Please enter the number of terms "
					+ "that will be in this polynomial: ");

			numOfTerms = in.nextInt(); // auto-throws InputMismatchEx

			// catching invalid # of terms
			if(numOfTerms <= 0) {
				throw new InputMismatchException("# of terms");
			}

			// adding terms one by one, input by user
			for(int i = 0; i < numOfTerms; i++)	{
				System.out.println("------Input for Term #"+(i+1)+"------");
				Term termToAdd = new Term();
				addTerm(termToAdd);
				System.out.println("Current Polynomial: " + this);
			}
			return true;
		}

		catch(InputMismatchException e)	{
			in.nextLine();
			System.out.println("Error regarding "+ e.getMessage() +". To avoid catastrophic"
					+ " meltdown, I will now shut down the program. Try again!");
			System.exit(0);
		}
		return false;
	}

	/**
	 * Adds a term in the correct ordering automatically
	 * using the compareTo method from the Comparable object
	 * Term.
	 * <p>
	 * If a Term is a like term with an existing term, its
	 * coefficient will be added to the existing terms
	 * coefficient instead of adding another term
	 * to the polynomial.
	 *
	 * @param newTerm
	 * 			new term to be added or inserted
	 */
	public void addTerm(Term newTerm) {
		 // first term requires no comparison
		if(poly.size() == 0) {
			poly.add(newTerm);
		}
		else {
			int indexToAdd = 0; // tracks location to insert the new Term
			for(int i = 0; i < poly.size(); i++) {
				// checking for like terms
				if(newTerm.isLikeTerm(poly.get(indexToAdd))) {
					// adding like terms if found
					poly.get(indexToAdd).addLikeTerm(newTerm.getCoefficient());
					/*
					 * the return statement MUST be kept here because
					 * once a like term is found and coefficients are
					 * summed, the term should NOT be added to the
					 * ArrayList 'poly' - it has been accounted for
					 */
					return;
				}
				// if newTerm is smaller, point to next term in polynomial
				if(newTerm.compareTo(poly.get(indexToAdd)) < 1)	{
					indexToAdd++;
				}
			}
			poly.add(indexToAdd, newTerm);
		}
	}
	/**
	 * Sorts an unsorted Polynomial
	 * <p>
	 * This method is not essential because addTerm auto-sorts
	 * however, the method could be used in certain instances where
	 * a polynomial is passed that is unsorted.
	 *
	 * @param unsortedPoly
	 * 			Polynomial passed that is unsorted and needs to be sorted
	 */
	public void sortPolynomial(Polynomial unsortedPoly) {
		for(int i = 0; i < poly.size() && poly.size() > 0; i++)	{
			int compareResult;
			for(int j = i+1; j < poly.size(); j++) {
				compareResult = poly.get(i).compareTo(poly.get(j));
				if(compareResult == 0) {
					continue; // no sorting required
				} else if(compareResult == 1) {
					continue; // no sorting required
					// compare result is -1
				} else {
					// removing bigger polynomial
					Term temp = poly.remove(j);
					// re-adding bigger polynomial before smaller
					poly.add(i, temp);
					/*
					 * re-initialize j to ensure term from outer loop is
					 * not superseded by any other terms in the polynomial
					 */
					j = i + 1;
				}
			}
		}
	}

	/**
	 * Method adds another polynomial to an existing polynomial
	 * and saves the result of the addition to the polynomial that
	 * calls the method.
	 * <p>
	 * 1. Each term is checked for a like term
	 * <br>
	 * 2. If like term found, coefficients are combined
	 * <br>
	 * 3. If not found, the new term is added to the polynomial
	 * 	  (sorting is automatic per the addTerm() method
	 * @param other
	 * 			other polynomial to be added to object calling the method
	 */
	public void addAPolynomial(Polynomial other) {

		Term thisTerm;
		Term thatTerm;
		boolean accountedFor;

		for(int i = 0; i < other.getSize(); i++) {
			thatTerm = other.getTerm(i);

			accountedFor = false;

			for(int j = 0; j < this.getSize(); j++)	{
				thisTerm = this.getTerm(j);

				if(thisTerm.isLikeTerm(thatTerm))	{
					thisTerm.addLikeTerm(thatTerm.getCoefficient());
					accountedFor = true;
					break;
				}
			}
			if(!accountedFor)	{
				this.addTerm(thatTerm);
			}
		}
	}
}
