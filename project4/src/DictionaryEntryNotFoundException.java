/*
 * Class: CMSC204
 * Instructor: Farnaz Eivazi
 * Description: This class represents an DictionaryEntryNotFoundException.
 * Due: 03/08/2026
 * Platform/compiler: macOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
 */

/**
 * This class represents a DictionaryEntryNotFoundException. It is thrown when a
 * word cannot be found in the dictionary.
 * 
 * @author Chakapan Kanchana
 */
public class DictionaryEntryNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a UserNotFoundException with no message.
	 */
	public DictionaryEntryNotFoundException() {
		super();
	}

	/**
	 * Constructs a UserNotFoundException with the specified message.
	 * 
	 * @param message the detail message explaining the exception
	 */
	public DictionaryEntryNotFoundException(String message) {
		super(message);
	}
}
