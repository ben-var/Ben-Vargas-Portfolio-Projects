/**
 * <b><i><u>PrintJob</u></i></b>
 * <p>
 * Simple PrintJob class to store a page count value and a title.
 * <p>
 * This object is meant to be simple object that is stored within
 * the more complex PrintQueue and Printer classes.
 *
 * @author Benjamin Vargas
 * @version 1.0
 */
public class PrintJob {

	// DECLARATION OF INSTANCE VARIABLES

	private int pagesLeft;
	private String title;
	private int IDNum;
	private int pagesPrinted = 0;

	/**
	 * <b><i>toString</i></b>
	 * <p>
	 * Standard toString method that prints formatted data in PrintJob.
	 * <p>
	 * Variables included limited to title and total number of pages, because other
	 * values are helper values.
	 */
	public String toString() {
		return "Title: " + title + " \n" + "Number of Pages: " + this.getTotalPages();
	}

	/**
	 * <b><i>setPageCount</i></b>
	 * <p>
	 * Setter for pagesLeft variable
	 *
	 * @param count
	 * 		pages left in PrintJob
	 */
	public void setPagesLeft(int count) {
		this.pagesLeft = count;
	}

	/**
	 * <b><i>setTitle</i></b>
	 * <p>
	 * Setter for String title of document
	 *
	 * @param inTitle
	 * 		title of job
	 */
	public void setTitle(String inTitle) {
		this.title = inTitle;
	}

	/**
	 * <b><i>setIDNum</i></b>
	 * <p>
	 * Setter for ID number of PrintJob
	 *
	 * @param num
	 * 		ID number of job
	 */
	public void setIDNum(int num) {
		this.IDNum = num;
	}

	/**
	 * <b><i>setAll</i></b>
	 * <p>
	 * Standard setAll method for all relevant instance variables
	 * <br>
	 * <i>Note: pagesPrinted should not be modified manually</i>
	 *
	 * @param count
	 * 		pages left in PrintJob
	 * @param inTitle
	 * 		title of job
	 * @param ID
	 * 		ID number of job
	 */
	public void setAll(int count, String inTitle, int ID) {
		this.pagesLeft = count;
		this.title = inTitle;
		this.IDNum = ID;
	}

	/**
	 * <b><i>Default Constructor</i></b>
	 */
	public PrintJob() {
		this.pagesLeft = 1;
		this.title = "Blank Page";
		this.IDNum = 0;
	}

	/**
	 * <b><i>Standard Constructor</i></b>
	 *
	 * @param count
	 * 		pages left in PrintJob
	 * @param inTitle
	 * 		title of job
	 * @param ID
	 * 		ID number of job
	 */
	public PrintJob(int count, String inTitle, int ID) {
		this.setAll(count, inTitle, ID);
	}

	/**
	 * <b><i>getPagesLeft</i></b>
	 * <p>
	 * Getter for pages left to print
	 * @return
	 * 		number of pages left to print
	 */
	public int getPagesLeft() {
		return this.pagesLeft;
	}

	/**
	 * <b><i>getTitle</i></b>
	 * <p>
	 * Getter for title of job
	 * @return
	 * 		title of job
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * <b><i>getIDNum</i></b>
	 * <p>
	 * Getter for ID number of job
	 * @return
	 * 		ID number of job
	 */
	public int getIDNum() {
		return this.IDNum;
	}

	/**
	 * <b><i>getTotalPages</i></b>
	 * <p>
	 * Returns total number of pages (i.e., original amount of pages
	 * @return
	 * 		total pages left to print added to pages already printed
	 */
	public int getTotalPages() {
		return this.pagesLeft + this.pagesPrinted;
	}

	/**
	 * <b><i>printOneCycle</i></b>
	 * <p>
	 * Simulates one minute of printing.
	 * <p>
	 * If job is more than 10 pages, 10 pages are printed, and
	 * pages left will be decremented by 10.
	 * <br>
	 * If job is less than 10 pages, entire job is printed and
	 * pages left will be 0.
	 */
	public void printOneCycle() {
		if (pagesLeft < 10) {
			this.pagesPrinted += this.pagesLeft;
			this.pagesLeft = 0;

		} else {
			this.pagesLeft -= 10;
			this.pagesPrinted += 10;
		}
	}

	/**
	 * <b><i>equals</i></b>
	 * <p>
	 * Standard equals method to override Object.equals()
	 *
	 * @param obj
	 * 		object to compare to this PrintJob
	 *
	 * @return
	 * 		true if passed value is equal to this
	 * 		<br>
	 * 		false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) { return true; }

		if (!(obj instanceof PrintJob)) { return false; }

		PrintJob otherJob = (PrintJob) obj;

		return
				this.getTotalPages() == otherJob.getTotalPages()
				&& this.title.equals(otherJob.getTitle());
	}

}
