import java.util.LinkedList;
import java.util.Queue;

/**
 * <b><i><u>PrintQueue</u></i></b>
 * <p>
 * Implementation of a Queue of PrintJobs meant to act as a subprinter.
 * <p>
 * The PrintQueue object will have a specific range of page counts it is able
 * to contain, and also tracks how much time the printer has spent printing.
 * <p>
 * <i>Class Invariant: The PrintQueue object does not track whether a job
 * held inside is valid based on the range of the PrintQueue. This tracking is
 * currently done inside the Printer class. If this object is used in other
 * applications, it may be necessary to add this exception handling.</i>
 *
 * @author Benjamin Vargas
 *
 */
public class PrintQueue {

	// DECLARATION OF INSTANCE VARIABLES

	private int printerID;
	private Queue<PrintJob> jobs = new LinkedList<PrintJob>();
	private int[] range = new int[2]; // should only have lower and upper range value (inclusive)
	private int timerInMinutes = 0; // time = 0 by default

	/**
	 * <b><i>setID</i></b>
	 * <p>
	 * Setter for PrintQueue ID number.
	 *
	 * @param id
	 * 		printer ID number
	 */
	public void setID(int id) {
		this.printerID = id;
	}

	/**
	 * <b><i>setRange</i></b>
	 * <p>
	 * Setter for PrintQueue page count range.
	 *
	 * @param lower
	 * 		lower bound of allowed page counts
	 * @param upper
	 * 		upper bound of allowed page counts
	 */
	public void setRange(int lower, int upper) {
		this.range[0] = lower;
		this.range[1] = upper;
	}

	/**
	 * <b><i>setLower</i></b>
	 * <p>
	 * Setter for lower bound of allowed page counts
	 *
	 * @param lower
	 * 		lower bound of allowed page counts
	 */
	public void setLower(int lower) {
		this.range[0] = lower;
	}

	/**
	 * <b><i>setUpper</i></b>
	 * <p>
	 * Setter for upper bound of allowed page counts
	 *
	 * @param upper
	 * 		upper bound of allowed page counts
	 */
	public void setUpper(int upper) {
		this.range[1] = upper;
	}

	/**
	 * <b><i>setAll</i></b>
	 * <p>
	 * Full setter for PrintQueue object
	 *
	 * @param id
	 * 		id number of PrintQueue
	 * @param lower
	 * 		lower bound of allowed page counts
	 * @param upper
	 * 		upper bound of allowed page counts
	 */
	public void setAll(int id, int lower, int upper) {
		this.printerID = id;
		this.range[0] = lower;
		this.range[1] = upper;
	}

	/**
	 * <b><i>Default Constructor</i></b>
	 */
	public PrintQueue() {
		this.setRange(1, 10);
	}

	/**
	 * <b><i>Standard Constructor</i></b>
	 * <p>
	 * @param lower
	 * 		lower bound of allowed page counts
	 * @param upper
	 * 		upper bound of allowed page counts
	 */
	public PrintQueue(int lower, int upper) {
		this.setRange(lower, upper);
	}

	/**
	 * <b><i>Full Constructor</i></b>
	 * <p>
	 * @param id
	 * 		id number of PrintQueue
	 * @param lower
	 * 		lower bound of allowed page counts
	 * @param upper
	 * 		upper bound of allowed page counts
	 */
	public PrintQueue(int id, int lower, int upper) {
		this.setAll(id, lower, upper);
	}

	/**
	 * <b><i>getID</i></b>
	 * <p>
	 * Getter for id number of PrintQueue
	 *
	 * @return
	 * 		id number of PrintQueue
	 */
	public int getID() {
		return this.printerID;
	}

	/**
	 * <b><i>getLower</i></b>
	 * <p>
	 * Getter for lower bound of allowed page counts
	 *
	 * @return
	 * 		lower bound of allowed page counts
	 */
	public int getLower() {
		return range[0];
	}

	/**
	 * <b><i>getUpper</i></b>
	 * <p>
	 * Getter for upper bound of allowed page counts
	 *
	 * @return
	 * 		upper bound of allowed page counts
	 */
	public int getUpper() {
		return range[1];
	}

	/**
	 * <b><i>getTimerInMinutes</i></b>
	 * <p>
	 * Getter for timer (in minutes) that printer has run.
	 * <p>
	 * This value has no setter because it should only be mutated
	 * by simulating minutes spent printing.
	 *
	 * @return
	 * 		total time PrintQueue has spent printing
	 */
	public int getTimerInMinutes() {
		return this.timerInMinutes;
	}

	/**
	 * <b><i>getQueue</i></b>
	 * <p>
	 * Getter for Queue of PrintJobs
	 * @return
	 * 		Queue of PrintJobs
	 */
	public Queue<PrintJob> getQueue() {
		return jobs;
	}

	/**
	 * <b><i>equals</i></b>
	 * <p>
	 * Standard equals method to override Object.equals()
	 *
	 * @param obj
	 * 		object to compare to this PrintQueue
	 *
	 * @return
	 * 		true if passed value is equal to this
	 * 		<br>
	 * 		false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) { return true; }

		if(!(obj instanceof PrintQueue)) { return false; }

		PrintQueue other = (PrintQueue) obj;

		return
				this.jobs.equals(other.getQueue()) &&
				this.range[0] == other.getLower() &&
				this.range[1] == other.getUpper();
	}

	/**
	 * <b><i>offerJob</i></b>
	 * <p>
	 * A safe way to offer from the PrintQueue
	 * (no access to the Queue structure)
	 *
	 * @param job
	 * 		job to be offered to the Queue instance variable
	 */
	public void offerJob(PrintJob job) {
		jobs.offer(job);
	}

	/**
	 * <b><i>pollJob</i></b>
	 * <p>
	 * A safe way to poll from the PrintQueue
	 * (no access to the Queue structure)
	 *
	 * @return
	 * 		job to be polled from the Queue instance variable
	 */
	public PrintJob pollJob() {
		return jobs.poll();
	}

	/**
	 * <b><i>printJob</i></b>
	 * <p>
	 * Simulates one minute of printing on any job at the front of the PrintQueue.
	 * <p>
	 * If a job has more than 10 pages, it is not removed from the queue. Otherwise,
	 * the job is polled from the queue.
	 *
	 * @return
	 * 		job that underwent one print cycle (see PrintJob.printOneCycle)
	 */
	public PrintJob printJob() {
		PrintJob job = jobs.peek();

		if (job.getPagesLeft() > 10) {
			job.printOneCycle();

		} else {
			job = jobs.poll();
			job.printOneCycle();

			System.out.println(job.getTitle() + " with page count of: \"" + job.getTotalPages() + "\" has been fully printed."
							+ "\nJobID by order received: " + job.getIDNum());
		}
		timerInMinutes++;
		return job;
	}

	/**
	 * <b><i>empty</i></b>
	 * <p>
	 * Checks if the PrintQueue is empty
	 * @return
	 * 		true if PrintQueue is empty
	 * 		<br>
	 * 		false otherwise
	 */
	public boolean empty() {
		return jobs.isEmpty();
	}
}
