package edu.miracosta.cs113;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Term object stores two types of variables:
 * <p>
 * 1. exponent = stores the exponent value of the term
 * <br>
 * 2. coefficient = stores the coefficient value of the term
 *
 * Class Invariant: The term is only with respect to x
 *
 * @author Ben Vargas
 * @version 1.0
 */
public class Term implements Comparable<Term> {
	//instantiating variables for Term object
	private int exponent;
	private int coefficient;

	/**
	 * toString for Term class accounts for different string formatting:
	 * <p>
	 * 1. Case where exponent is zero, term reduced to constant
	 * <br>
	 * 2. Case where exponent is one, the power caret symbol is removed
	 * <br>
	 * 3. Case where coefficient/exponent is zero, parenthesis are added
	 * <br>
	 * 4. Case where coefficient is one, the coefficient is removed
	 */
	public String toString() {
		String coefficientString = Integer.toString(coefficient);
		String exponentString = Integer.toString(exponent);

		/*
		 * check for a negative coefficient must be placed before
		 * check for exponent 0 so coefficient is formatted prior
		 * to being returned
		 */
		if(coefficient < 0)	{
			coefficientString = "(" + coefficientString + ")";
		}
		if(coefficient == 1 && exponent != 0)	{
			coefficientString = "";
		}
		if(exponent == 0)	{
			return coefficientString;
		}
		if(exponent == 1)	{
			return coefficientString + "x";
		}
		if(exponent < 0) {
			exponentString = "(" + exponentString + ")";
		}
		return coefficientString + "x^" + exponentString;
	}

	//standard setters/mutators
	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public void setAll(int exponent, int coefficient) {
		this.exponent = exponent;
		this.coefficient = coefficient;
	}

	//standard constructor, default constructor, and copy constructor
	public Term(int exponent, int coefficient) {
		this.setAll(exponent, coefficient);
	}

	public Term()	{
		getTermInput();
	}

	public Term(Term other)	{
		this.exponent = other.getExponent();
		this.coefficient = other.getCoefficient();
	}

	//standard getters
	public int getExponent() {
		return this.exponent;
	}

	public int getCoefficient()	{
		return this.coefficient;
	}

	//standard equals
	public boolean equals(Term other)	{
		return (this.exponent == other.getExponent() &&
				this.coefficient == other.getCoefficient());
	}

	//exponents take precedence over coefficients in terms of ordering

	/**
	 * This method will compare two terms and determine which
	 * is higher.
	 * <p>
	 * Currently, the method treats exponent < 0 less than constants
	 * because constants have a power of 0.
	 * <p>
	 * This can be modified later to treat exponents with respect
	 * to their absolute value.
	 *
	 * @param other
	 * 			another Term to compare the Term calling the method to
	 * @return
	 * 			0 if both Terms are equal
	 * 			1 if Term calling method is larger
	 * 		   -1 if Term calling method is smaller
	 */
	public int compareTo(Term other) {
		if(this.equals(other)) {
			return 0;
		}	else if(this.exponent > other.getExponent()) {
			return 1;
		}	else if(this.exponent < other.getExponent()) {
			return -1;
			//if exponents equal
		}	else {
			if(this.coefficient > other.getCoefficient())	{
				return 1; //if exponents equal, but this coeff > other coeff
			}
		}
		//last possible case
		return -1; //if exponents equal, but this coeff < other coeff
	}

	/**
	 * This method takes as input a Scanner object and asks the user to
	 * enter in values for the term object.
	 *
	 * Note - In future iterations, would like to have the program
	 * handle errors more gracefully than exiting immediately.
	 * @return
	 * 			- IF successful, returns TRUE
	 * 			<br>
	 * 			- ELSE returns FALSE
	 */
	public boolean getTermInput()	{
		@SuppressWarnings("resource") //Scanner not closed to prevent errors with System.in
		Scanner in = new Scanner(System.in);
		try	{
			System.out.print("Please enter the coefficient of this term: ");
			this.setCoefficient(in.nextInt());

			System.out.print("Please enter the degree exponent of this term: ");
			this.setExponent(in.nextInt());

			return true;
			//non-int immediately triggers a mismatch
		}	catch(InputMismatchException e) {
			in.nextLine();

			System.out.println("You entered an invalid integer. To avoid catastrophic"
					+ " meltdown, I will now shut down the program. Try again!");
			System.exit(0);
		}
		return false;
	}

	/**
	 * Method checks to see if a term is a like term with another term.
	 *
	 * @param other
	 * 			Other term to be compared with
	 * @return
	 * 			- TRUE if exponents are equal (like terms)
	 * 			<br>
	 * 			- FALSE if exponents NOT equal (not like terms)
	 */
	public boolean isLikeTerm(Term other)	{
		if(this.exponent == other.getExponent()) {
			return true;
		}
		return false;
	}

	/**
	 * Adds like terms instead of simply creating a new Term object.
	 *
	 * To be used in tandem with isLikeTerm()
	 *
	 * @param coeffToAdd
	 * 			Coefficient to be added between like terms
	 */
	public void addLikeTerm(int coeffToAdd)	{
		this.coefficient += coeffToAdd;
	}
}
