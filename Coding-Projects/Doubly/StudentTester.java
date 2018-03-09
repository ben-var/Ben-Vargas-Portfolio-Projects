//package name redacted

/**
 * Tester class that tests the following Classes:
 * <br>
 * 1. BVDoublyLinkedList (not all methods tested, that would require thorough unit testing)
 * <br>
 * 2. Student
 * <br>
 * 3. WaitingList (key methods tested)
 * <p>
 *
 * Algorithm for testing:
 * <br>
 * 1. Instantiate new WaitingList object
 * <br>
 * 2. Add several students to WaitingList
 * <br>
 * 3. Print WaitingList to test toString
 * <br>
 * 4. Test method to add Student to beginning of list
 * <br>
 * 5. Test method to add Student to end of list
 * <br>
 * 6. Test method to add Student at an index location
 * <br>
 * 7. Test method to remove Student from beginning of list
 * <br>
 * 8. Test method to remove Student with specified name
 * <br>
 * 9. Test various methods on an empty list
 *
 * @author Ben Vargas
 * @version 1.1
 *
 */
public class StudentTester {
	public static void main(String[] args) {
		//cs113 holds student objects, empty is testing empty BVDoublyLinkedList constructor
		WaitingList cs113 = new WaitingList("CS113 Waiting List");
		WaitingList empty = new WaitingList("Blank Test List");

		//initializing student objects to be used for WaitingList methods tested below
		Student s1 = new Student("Jason", "Bourne");
		Student s2 = new Student("James", "Bond");
		Student s3 = new Student("Elon", "Musk");
		Student s4 = new Student("Oprah", "Winfrey");
		Student s5 = new Student("Jessica", "Alba");
		Student s6 = new Student("Joe", "Shmoe");
		Student s7 = new Student("Barack", "Obama");
		Student s8 = new Student("Ash", "Katch'em");
		Student s9 = new Student("Ben", "Vargas");
		Student s10 = new Student("Kim", "Jung-Un");

		//adding several students initially to SimpleLinkedList of WaitingList
		cs113.addToBack(s6);
		cs113.addToBack(s7);
		cs113.addToBack(s1);
		cs113.addToBack(s3);
		cs113.addToBack(s2);
		cs113.addToBack(s4);
		cs113.addToBack(s8);

		//TESTING BEGIN
		System.out.println("-----Testing Phase 1 Begin-----");

		//testing toString and adding to back of list
		System.out.println("\n-----Testing toString() and add() to empty list-----");
		System.out.println(cs113);

		//testing adding to beginning of list (Requirement 2)
		System.out.println("\n-----Testing add(Student) to front of list-----");
		cs113.letStudentCutToFront(s10);
		System.out.println("Added Kim Jung-Un to the front of the line.");
		System.out.println(cs113);

		//testing adding to end of list (Requirement 1)
		System.out.println("\n-----Testing add(size(), Student)-----");
		cs113.addToBack(s5);
		System.out.println("Added Jessica Alba to back of the line.");
		System.out.println(cs113);

		//testing add to middle of list
		System.out.println("\n-----Testing letStudentCutAtIndex()-----");
		cs113.letStudentCutAtIndex(6, s9);
		System.out.println("Added Ben Vargas to index location 6 (starting at 0) of line");
		System.out.println(cs113);

		//testing removeFirst (Requirement 3)
		System.out.println("\n-----Testing removal of first element from CS113 List------");
		System.out.println("Calling 'takeNextStudent()' to remove Kim Jung-Un (good riddens!)");
		cs113.takeNextStudent();
		System.out.println(cs113);

		//testing removebyName (Requirement 4)
		System.out.println("\n-----Testing removal of element at specified index from CS113 List------");
		System.out.println("Calling 'takeStudentByName(First, Last)'"
							+ " to remove Elon Musk by name.");
		cs113.takeStudentByName("Elon", "Musk");
		System.out.println(cs113);

		//testing for cases with empty list

		//test print and constructor for empty list
		System.out.println("\n-----Testing print of empty list-----");
		System.out.println(empty);

		//test add to empty list
		System.out.println("\n-----Testing add to empty list-----");
		System.out.println("Adding \"Jonny Bravo\" to empty list and then printing list again");
		empty.addToBack(new Student("Jonny", "Bravo"));
		System.out.println(empty);

		//testing remove of list of size 1
		System.out.println("\n-----Testing remove of list with 1 element (by name)-----");
		System.out.println("Calling 'takeStudentByName(\"Jonny\",\"Bravo\"), then re-printing list");
		empty.takeStudentByName("Jonny", "Bravo");
		System.out.println(empty);

		//testing remove of first element in list of size 1
		System.out.println("\n-----Testing remove from list with 1 element using"
				+ " takeNextStudent (remove(0))-----");
		System.out.println("Adding Jonny Bravo back to list");
		empty.addToBack(new Student("Jonny", "Bravo"));
		System.out.println(empty);
		System.out.println("Calling takeNextStudent()");
		empty.takeNextStudent();
		System.out.println(empty);

		//TESTING END
		System.out.println("\n-----Testing Phase 1 Complete-----");
	}
}
