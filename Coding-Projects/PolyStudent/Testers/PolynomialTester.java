package testers;

//import statement used when source code was in package - no longer needed.
import edu.miracosta.cs113.*;

/**
 * Tester class to test various Polynomial, Term, and FirstArrayList methods
 * <p>
 * Simple Algorithm to test most methods
 * <br>
 * 1. Enter in # of terms for polynomial 1
 * <br>
 * 2. Enter coefficient for term 1
 * <br>
 * 3. Enter exponent for term 1
 * <br>
 * 4. Repeat until # of terms satisfied for polynomial 1
 * <br>
 * 5. Repeat steps 1 -> 4 for polynomial 2
 * <br>
 * 6. Call static method addTwoPolynomials() in order to
 *    test sorting and addition of like terms (as well as
 *    terms of different powers) between two polynomials.
 *
 * <p>
 * Class Invariant: Unit testing may be added should time permit to thoroughly test
 * every single method in every class used. All methods stated in problem description
 * can be tested within this tester.
 * <p>
 * Tests performed in this tester (and cleared) but not limited to:
 * <br>
 * 1. Entering < 1 terms for a new polynomial
 * <br>
 * 2. Entering non-int into any fields prompted
 * <br>
 * 3. Entering negative integers into coefficients/exponents
 * <br>
 * 4. Inputting values out of order to test sorting
 * <br>
 * 5. Adding two polynomials of different exponent values to ensure addition is also sorted
 * <br>
 * 6. Testing addition of like terms for new terms input into a Polynomial object
 * <br>
 * 7. Testing addition of like terms for addition of two polynomial methods
 * <br>
 * 8. Testing string formatting for negative coefficient/exponent values
 * <br>
 * 9. Testing string formatting for exponents of 0 and exponents of 1
 *
 * @author Ben Vargas
 * @version 1.0
 *
 */

public class PolynomialTester {
	public static void main(String[] args) {
		//two polynomial objects automatically call input methods from class
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();

		//outputting two polynomials in addition format
		System.out.println("\n----------Demonstration of Adding Polynomials-------------------");
		System.out.println("          (" + p1 + ")");
		System.out.println("        + (" + p2 + ")");
		System.out.println("----------------------------------------------------------------");

		//creating new polynomial as a sum of p1 and p2
		Polynomial p3 = addTwoPolynomials(p1, p2);

		//printing final results
		System.out.println("        =  " + p3);

	}

	/**
	 * Method takes as input two polynomials and adds them together.
	 * <p>
	 * If the polynomials have like terms, the terms' coefficients
	 * will be added together into one term.
	 * <p>
	 * Refer to the Polynomial.addAPolynomial() method for another
	 * implementation of this method.
	 *
	 * This method can be easily adapted to be within the Polynomial class
	 * @param p1
	 * 			first polynomial for addition
	 * @param p2
	 * 			second polynomial for addition
	 * @return
	 * 			new Polynomial object with
	 */
	public static Polynomial addTwoPolynomials(Polynomial p1, Polynomial p2) {
		//new polynomial as deep copy of p1
		Polynomial p3 = new Polynomial(p1);

		p3.addAPolynomial(p2);

		return p3;
	}
	
}
