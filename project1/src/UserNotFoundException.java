/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a UserNotFoundException exception.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * This class represents an UserNotFoundException. It is thrown when a user
 * is no found.
 * 
 * @author Chakapan Kanchana
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception without a detail message.
	 */
	public UserNotFoundException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

}
