package edu.miracosta.cs113;

/**
 * Simple Student model object that stores a first and last name
 *
 * @author Ben Vargas
 * @version 1.0
 *
 */
public class Student {
	//VARIABLE DECLARATIONS
	private String first;
	private String last;

	/** Returns name in 'last, first' format */
	@Override
	public String toString() {
		return last + ", " + first;
	}

	//SETTERS

	public void setFirst(String inFirst) {
		this.first = inFirst;
	}

	public void setLast(String inLast) {
		this.last = inLast;
	}

	public void setAll(String inFirst, String inLast) {
		this.first = inFirst;
		this.last = inLast;
	}

	//CONSTRUCTORS

	/** Full Constructor */
	public Student(String inFirst, String inLast) {
		this.setAll(inFirst, inLast);
	}

	/** Default Constructor */
	public Student() {
		this.first = "Test";
		this.last = "Dummy";
	}

	/** Copy Constructor */
	public Student(Student other) {
		this.first = other.getFirst();
		this.last = other.getLast();
	}

	//GETTERS

	public String getFirst() {
		return this.first;
	}

	public String getLast() {
		return this.last;
	}

	/**
	 * equals() method must type-cast from 'Object' type to
	 * 'Student' so that the Object.equals(Object obj) is
	 * overwritten.
	 * <br>
	 * This MUST be implemented in this way
	 * in order for the "SingleLinkedList" list to be set
	 * to the <Student> type. Otherwise, certain methods
	 * of "SingleLinkedList" will call Object.equals()
	 * instead of this method.
	 */
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Student))	{
			return false;
		}
		Student otherStudent = (Student) other;
		return(this.getFirst().equals(otherStudent.getFirst())
				&& this.getLast().equals(otherStudent.getLast()));
	}
}
