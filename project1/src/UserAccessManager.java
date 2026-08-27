/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class stores a list of UserAccount objects and provides methods to add users, 
 * remove users, verify access credentials, and load accounts from a file. It also handles 
 * account lockout after multiple failed login attempts.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores a list of UserAccount objects and provides methods to add
 * users, remove users, verify access credentials, and load accounts from a
 * file. It also handles account lockout after multiple failed login attempts.
 * 
 * @author Chakapan Kanchana
 */
public class UserAccessManager {

	/**
	 * List of user accounts.
	 */
	private List<UserAccount> accounts;

	/**
	 * Constructs a UserAccessManager with an empty list of user accounts.
	 */
	public UserAccessManager() {
		accounts = new ArrayList<>();

	}

	/**
	 * Loads user accounts from a file.
	 * 
	 * @param filename the name of the file
	 * @throws FileNotFoundException if the file cannot be found or opened
	 */
	public void loadAccounts(String filename) throws FileNotFoundException {
		Utilities.readAccountFile(filename, this);
	}

	/**
	 * Adds a new user account to the system.
	 * 
	 * @param username          the username of the new account
	 * @param encryptedPassword the username of the new account
	 * @throws DuplicateUserException  if an account with the same username already
	 *                                 exists
	 * @throws InvalidCommandException if the username or password is null or blank
	 */
	public void addUser(String username, String encryptedPassword)
			throws DuplicateUserException, InvalidCommandException {

		if (username == null || username.isBlank() || encryptedPassword == null || encryptedPassword.isBlank()) {
			throw new InvalidCommandException("Invalid command arguments.");
		}

		if (findUser(username) != null) {
			throw new DuplicateUserException("User '" + username + "' account already exists.");
		}
		accounts.add(new UserAccount(username, encryptedPassword));
	}

	/**
	 * Removes an existing user account from the system.
	 * 
	 * @param username the username of the account to remove
	 * @throws InvalidCommandException if the username is null or blank
	 * @throws UserNotFoundException   if the user does not exist
	 */
	public void removeUser(String username) throws InvalidCommandException, UserNotFoundException {
		if (username == null || username.isBlank()) {
			throw new InvalidCommandException("Invalid command arguments.");
		}
		UserAccount user = findUser(username);
		if (user == null) {
			throw new UserNotFoundException("User '" + username + "' not found.");
		}
		accounts.remove(user);
	}

	/**
	 * This method checks whether the user exists, whether the account is locked,
	 * and whether the provided encrypted password matches the stored password.
	 * Failed attempts increment the failure count and may result in account
	 * lockout.
	 * 
	 * @param username          the username
	 * @param encryptedPassword the encrypted password
	 * @return true if access is successfully verified
	 * @throws InvalidCommandException    if the username is null or blank
	 * @throws UserNotFoundException      if the user does not exist
	 * @throws AccountLockedException     if the account is locked
	 * @throws PasswordIncorrectException if the password is incorrect
	 */
	public boolean verifyAccess(String username, String encryptedPassword)
			throws InvalidCommandException, UserNotFoundException, AccountLockedException, PasswordIncorrectException {

		if (username == null || username.isBlank()) {
			throw new InvalidCommandException("Invalid command arguments.");
		}
		UserAccount user = findUser(username);

		if (user == null) {
			throw new UserNotFoundException("User '" + username + "' not found.");
		}
		if (user.isLocked()) {
			throw new AccountLockedException("User '" + username + "' account is locked.");
		}
		// Check password
		if (!user.getEncryptedPassword().equals(encryptedPassword)) {
			user.incrementFailureCount();
			if (user.getFailureCount() >= UserAccount.MAX_FAILURES) {
				user.lock();
				throw new AccountLockedException("User '" + username + "' account is locked.");
			}
			throw new PasswordIncorrectException("Incorrect Password");
		}

		// Password match
		user.resetFailureCount();
		return true;
	}

	/**
	 * Searches for a user account by username.
	 * 
	 * @param username the username to search for
	 * @return the matching UserAccount if found, or null if not found
	 */
	public UserAccount findUser(String username) {
		for (UserAccount ua : accounts) {
			if (ua.getUsername().equals(username))
				return ua;
		}
		return null;
	}

}
