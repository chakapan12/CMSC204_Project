/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This program provides a command-line interface for the
 * User Access Manager system. It processes user commands, validates input,
 * and interacts with the UserAccessManager class to add users, remove users,
 * verify access, and load accounts from a file.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This Main class serves as the entry point for the User Access Manager
 * program.
 * 
 * @author Chakapan Kanchana
 */
public class Main {

	public static void main(String[] args) {

		UserAccessManager manager = new UserAccessManager();
		try (Scanner sc = new Scanner(System.in)) {

			System.out.println("User access manager ready.");

			while (true) {
				try {
					System.out.print("User Access Manager> ");
					String line = sc.nextLine().trim();
					if (line.isEmpty())
						continue;

					String[] parts = line.split("\\s+", 2);
					String command = parts[0];

					switch (command) {
					case "exit":
						return;

					case "load": {
						String filename = checkArgumentForFilename(parts);
						manager.loadAccounts(filename);
						break;
					}
					case "add": {
						String username = checkArgumentForUsername(parts);
						System.out.print("Password: ");
						String password = sc.nextLine();
						if (password == null || password.isBlank()) {
							throw new InvalidCommandException("Password cannot be empty.");
						}
						String encryptedPassword = Utilities.encryptPassword(password);
						manager.addUser(username, encryptedPassword);
						break;
					}

					case "verify": {
						String username = checkArgumentForUsername(parts);
						System.out.print("Password: ");
						String password = sc.nextLine();
						if (password == null || password.isBlank()) {
							throw new InvalidCommandException("Password cannot be empty.");
						}
						String encryptedPassword = Utilities.encryptPassword(password);
						if (manager.verifyAccess(username, encryptedPassword)) {
							System.out.println("Access verified");
						}
						break;
					}

					case "remove": {
						String username = checkArgumentForUsername(parts);
						manager.removeUser(username);
						break;
					}
					default:
						throw new InvalidCommandException("Invalid command arguments.");
					}

				} catch (FileNotFoundException | InvalidCommandException | DuplicateUserException
						| UserNotFoundException | AccountLockedException | PasswordIncorrectException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}

	/**
	 * This method ensures that the command contains a non-empty argument following
	 * the command keyword. This version allows spaces in the argument 
	 * (used for filenames).
	 * 
	 * @param parts the array of command tokens
	 * @return the validated argument (e.g., username or filename)
	 * @throws InvalidCommandException if the argument is missing or blank
	 */
	private static String checkArgumentForFilename(String[] parts) throws InvalidCommandException {
		if (parts.length < 2 || parts[1].isBlank()) {
			throw new InvalidCommandException("Invalid command arguments.");
		}
		return parts[1];
	}

	/**
	 * This method ensures that the command contains a non-empty argument following
	 * the command keyword. his version does NOT allow spaces in the argument 
	 * (used for usernames).
	 * 
	 * @param parts the array of command tokens
	 * @return the validated argument (e.g., username or filename)
	 * @throws InvalidCommandException if the argument is missing or blank
	 */
	private static String checkArgumentForUsername(String[] parts) throws InvalidCommandException {
		if (parts.length < 2 || parts[1].isBlank() || parts[1].contains(" ")) {
			throw new InvalidCommandException("Invalid command arguments.");
		}
		return parts[1];
	}
}
