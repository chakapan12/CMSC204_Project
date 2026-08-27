/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a PasswordIncorrectException exception.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * This class represents an PasswordIncorrectException. It is thrown when a
 * user enters incorrect password.
 * 
 * @author Chakapan Kanchana
 */
public class PasswordIncorrectException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception without a detail message.
	 */
	public PasswordIncorrectException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public PasswordIncorrectException(String message) {
		super(message);
	}

}
