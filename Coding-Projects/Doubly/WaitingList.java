//package name redacted

import java.util.List;

/**
 * Simple example use of BVDoublyLinkedList data structure
 * build in this folder. Simulates a waiting list for a
 * course enrollment, for Student objects (Last Name, First Name)
 * <p>
 * Unique Methods:
 * <br>
 * 1. addToBack() - adds Student to end of list
 * <br>
 * 2. letStudentCutToFront() - adds Student to front of list
 * <br>
 * 3. letStudentCutAtIndex() - adds Student to specific index location
 * <br>
 * 4. takeNextStudent() - removes first student in list
 * <br>
 * 5. takeStudentByName() - removes student of specified name from list
 *
 * @author Ben Vargas
 * @version 1.2
 *
 */
public class WaitingList {
	//INITIALIZING/DECLARING VARIABLES

	private List<Student> waitingList = new BVDoublyLinkedList<Student>();
	private String course;

	//STANDARD TOSTRING

	public String toString() {
		String toReturn = course + " as of now "
				+ "(size = " + this.size() + "): \n";
		toReturn += waitingList;

		return toReturn;
	}

	//SETTERS (setAll not necessary)

	public void setCourse(String courseName) {
		this.course = courseName;
	}

	/** Full constructor */
	public WaitingList(String courseName) {
		this.course = courseName;
	}

	/** Default constructor */
	public WaitingList() {
		this.course = "COURSE NAME NOT SET";
	}

	/** Copy constructor */
	public WaitingList(WaitingList other) {
		this.waitingList = new BVDoublyLinkedList<Student>((BVDoublyLinkedList<Student>) other.passList());
		this.course = other.getCourse();
	}

	//GETTERS

	public String getCourse() {
		return this.course;
	}

	//special getter that lets waitingList have convention of a list structure
	public int size() {
		return waitingList.size();
	}

	//passing reference to list if necessary
	private List<Student> passList() {
		return waitingList;
	}

	/** Test for equivalence to another WaitingList object */
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof WaitingList)) {
			return false;
		}

		other = (WaitingList) other;
		return (this.course == ((WaitingList) other).getCourse()
				&& waitingList.equals(((WaitingList) other).passList()));
	}

	/**
	 * Adds a student to the end of the waiting list
	 * @param inStudent
	 * 			Student to be added to the list
	 */
	public void addToBack(Student inStudent) {
		waitingList.add(inStudent);
	}

	/**
	 * Adds student to the beginning of the waiting list
	 * @param inStudent
	 * 			Student to be added to the list
	 */
	public void letStudentCutToFront(Student inStudent) {
		waitingList.add(0, inStudent);
	}

	/**
	 * Adds a student to the list at a specified index
	 * @param index
	 * 			Index location to add the student at
	 * @param inStudent
	 * 			Student to be added to the list
	 */
	public void letStudentCutAtIndex(int index, Student inStudent) {
		waitingList.add(index, inStudent);
	}

	/**
	 * Removes first student in line
	 */
	public void takeNextStudent() {
		waitingList.remove(0);
	}

	/**
	 * Removes student with specified name
	 *
	 * @param inFirst
	 * 		First name of student to be removed
	 * @param inLast
	 * 		Last name of student to be removed
	 * @return
	 * 		TRUE if Student found and removed
	 * 		<br>
	 * 		FALSE if Student not found
	 */
	public boolean takeStudentByName(String inFirst, String inLast) {
		Student temp = new Student(inFirst, inLast);

		boolean studentFound = waitingList.remove(temp);
		if (studentFound) {
			return true;
		}
		return false;
	}
}
