import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * <b><i><u>Printer</u></i></b>
 * <p>
 * The Printer class is a class that stores PrintQueue objects that act
 * as subprinters within the master Printer object. The Printer is user-facing
 * and is meant to serve as an abstraction of multiple PrintQueues and PrintJobs.
 * <p>
 * <i><b>Class Invariant:</b> This printer assumes the maximum page count of a
 * given PrintJob is 50. Any changes to this assumption will require a
 * modification of this code.</i>
 * <p>
 * <i><b>Class Invariant:</b> The maximum number of subprinters
 * allowed is currently set to 3. Any changes to this assumption will
 * require a modification of this code.</i>
 *
 * @author Benjamin Vargas
 * @version 1.0
 *
 */
public class Printer {

	// INSTANCE VARIABLES

	private List<PrintQueue> subPrinters = new ArrayList<PrintQueue>();
	private int minutesPassed;
	private int placeInMasterQueue = 1; // starts at one for user-readability

	/**
	 * <b><i>Default Constructor</i></b>
	 * <p>
	 * Default Constructor that uses a JOptionPane dialog box to prompt the user to enter
	 * the number of desired PrintQueues they would like in their printer.
	 * <p>
	 * Refer to the Class Invariants for restrictions regarding the amount of
	 * subprinters allowed. This constructor uses the addSubPrinters helper
	 * method to initialize the PrintQueue subprinters.
	 *
	 * @throws NumberFormatException
	 * 		if user enters a non-integer into the JOptionPane provided
	 * @throws IllegalArgumentException
	 * 		if user enters a null string or closes the JOptionPane w/ no input
	 */
	public Printer() {
		int numOfSubPrinters;
		try {
			 String input = JOptionPane.showInputDialog(null, "Enter desired number of sub-printers. (Must be 1, 2, or 3)",
									"SUBPRINTERS", JOptionPane.QUESTION_MESSAGE);
			 if (input == null) {
				 throw new IllegalArgumentException();
			 }
			 numOfSubPrinters = Integer.parseInt(input);

			if(numOfSubPrinters < 1 || numOfSubPrinters > 3) {
				throw new NumberFormatException("for input string: " + Integer.toString(numOfSubPrinters));
			}

			this.addSubPrinters(numOfSubPrinters);

		} catch (NumberFormatException e) {
			System.out.println("Your input of \"" + e.getMessage().substring(18) +
										"\" was invalid. You must enter a 1, 2, or 3.");
			System.exit(0);
		} catch (IllegalArgumentException e) {
			System.out.println("You entered a null string, which is invalid. You must enter a 1, 2, or 3.");
			System.exit(0);
		}
	}

	/**
	 * <b><i>Standard Constructor</i></b>
	 * <p>
	 * Standard Constructor with provided number of PrintQueues to be added
	 * to the Printer object. Utilizes helper method addSubPrinters
	 *
	 * @param numOfSubPrinters
	 * 		number of PrintQueues (i.e., subprinters) to be added to List instance variable
	 */
	public Printer(int numOfSubPrinters) {
		addSubPrinters(numOfSubPrinters);
	}

	/**
	 * <b><i>getMinutesSpent</i></b>
	 * <p>
	 * Getter for total minutes passed (modified by the various printJob methods)
	 *
	 * @return
	 * 		number of PrintQueues in List instance variable (i.e., subprinters)
	 */
	public int getMinutesSpent() {
		return this.minutesPassed;
	}

	/**
	 * <b><i>getNumOfSubPrinters</i></b>
	 * <p>
	 * Getter for number of PrintQueues in List instance variable (i.e., subprinters)
	 *
	 * @return
	 * 		number of PrintQueues in List instance variable (i.e., subprinters)
	 */
	public int getNumOfSubPrinters() {
		return subPrinters.size();
	}

	/**
	 * <b><i>addSubPrinters</i></b>
	 * <p>
	 * Helper method that adds PrintQueues to a List stored in the Printer object.
	 * <p>
	 * Class Invariant noted in the <b><i><u>Printer</u></i></b> javadoc provides
	 * the constraints for subprinters and possible range of page number values allowed.
	 *
	 * @param numOfSubPrinters
	 * 		number of printers to be added to subPrinters list of printers
	 * @throws IllegalArgumentException
	 * 		if number of printers is not 1, not 2, or not 3.
	 */
	private void addSubPrinters(int numOfSubPrinters) {
		subPrinters.clear();

		switch (numOfSubPrinters) {
		case 1:
			subPrinters.add(new PrintQueue(1, 1, 50));
			break;
		case 2:
			subPrinters.add(new PrintQueue(1, 1, 25));
			subPrinters.add(new PrintQueue(2, 26, 50));
			break;
		case 3:
			subPrinters.add(new PrintQueue(1, 1, 9));
			subPrinters.add(new PrintQueue(2, 10, 19));
			subPrinters.add(new PrintQueue(3, 20, 50));
			break;
		default:
			throw new IllegalArgumentException ("Exceeded max number of sub-printers");
		}
	}

	/**
	 * <b><i>addJob</i></b>
	 * <p>
	 * Adds a single job with specific attributes to the Printer object.
	 * <p>
	 * A PrintJob will be placed in a particular subprinter based on the number of pages
	 * in the job. Subprinter ranges are determined by the Constructor of the Printer class
	 *
	 * @param pageCount
	 * 		number of pages of job to be added to the queue
	 * @param title
	 * 		string name of PrintJob object being added to queue
	 */
	public void addJob(int pageCount, String title) {
		for (PrintQueue q : subPrinters) {
			// adding the job to the correct printer. throws exception if not in any printer's range
			if (pageCount >= q.getLower() && pageCount <= q.getUpper()) {
				q.offerJob(new PrintJob(pageCount, title, placeInMasterQueue));
				placeInMasterQueue++;
				System.out.println(title + " with " + pageCount + " pages has been added to Printer #" + q.getID());

				return; // no longer need to continue method
			}
		}
		// if reached, means there was not a bucket (i.e., range of page counts) for a particular # of pages
		JOptionPane.showMessageDialog(null, "Invalid page count printed! Job not added.", "ERROR", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	/**
	 * <b><i>printOneMinute</i></b>
	 * <p>
	 * This method examines the current jobs inside each of its sub-printers
	 * and simulates one minute of printing activity.
	 * <p>
	 * A minute consists of checking for non-empty subprinters, and calling the
	 * PrintJob.printJob() method in order to process a 1 minute printing activity
	 * on a PrintJob if it is at the front of the non-empty PrintQueue subprinters
	 * object(s). Minutes passed is tracked in an instance variable of the Printer
	 * class.
	 */
	public void printOneMinute() {
		if (this.hasJobs()) {
			System.out.println("------Begin Minute: " + minutesPassed + "------\n");

			// checking for any non-empty PrintQueues
			for (int i = 0; i < subPrinters.size(); i++) {
					if (!subPrinters.get(i).empty()) {

					System.out.println("~~ Status Message of Printer #" +
											subPrinters.get(i).getID() + " ~~");

					// see the PrintJob.printJob() Javadoc for more detail.
					PrintJob job = subPrinters.get(i).printJob();

					if (job.getPagesLeft() > 0) {
						System.out.println(job.getTitle() + " with ID #" + job.getIDNum() + " has " +
											job.getPagesLeft() + " pages left to print.");
					}
					System.out.println();
				}
			}
			minutesPassed++;
		} else {
			System.out.println("No jobs were printed, because the Printer did not have any queued up.");
		}
	}

	/**
	 * <b><i>printAllJobs</i></b>
	 * <p>
	 * Continuously calls the printOneMinute method until all PrintJobs have been printed.
	 * <br>
	 * Final minutesPassed stored as instance variable in Printer object is output upon
	 * completion of this method.
	 */
	public void printAllJobs() {
		while (this.hasJobs()) {
			printOneMinute();
		}

		if (minutesPassed == 0) {
			System.out.println("\nYou didn't print any jobs! No jobs were queued up.");
		} else {
			System.out.println("\nTotal minutes that master printer has been run : " + minutesPassed);
		}
	}

	/**
	 * <b><i>getLongestTimer</i></b>
	 * <p>
	 * Iterates through all PrintQueues inside the master Printer, and searches
	 * for the longest amount of time any of the given printers has been running.
	 *
	 * @return
	 * 		PrintQueue timer with the highest value in the list
	 */
	public int getLongestTimer() {
		List<Integer> timers = new ArrayList<Integer>();
		for(PrintQueue q : subPrinters) {
			timers.add(q.getTimerInMinutes());
		}
		return Collections.max(timers);
	}

	/**
	 * <b><i>hasJobs</i></b>
	 * <p>
	 * Simply determines whether any PrintQueue in the master printer has any
	 * jobs stored in their queue at a specific point in time (when the method is called)
	 *
	 * @return
	 * 		true if one of queues contains a job
	 * 		<br>
	 * 		false otherwise
	 */
	public boolean hasJobs() {
		for (PrintQueue q : subPrinters) {
			if (!q.empty()) {
				return true;
			}
		}
		return false;
	}
}
