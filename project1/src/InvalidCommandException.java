/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a InvalidCommandException exception.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * This class represents an InvalidCommandException. It is thrown when a
 * user enters invalid commands.
 * 
 * @author Chakapan Kanchana
 */
public class InvalidCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception without a detail message.
	 */
	public InvalidCommandException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public InvalidCommandException(String message) {
		super(message);
	}
}
