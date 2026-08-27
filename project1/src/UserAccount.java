/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a user account in the User Access Manager system.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.Objects;

/**
 * Represents a user account in the User Access Manager system.
 */
public class UserAccount {

	private String username;
	private String encryptedPassword;
	private int failureCount;
	private boolean locked;
	public static final int MAX_FAILURES = 3;

	/**
	 * Constructs a UserAccount with the specified username and encrypted password.
	 * 
	 * @param username          the username for the account
	 * @param encryptedPassword the encrypted password associated with the account
	 */
	public UserAccount(String username, String encryptedPassword) {
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		this.failureCount = 0;
		this.locked = false;

	}

	/**
	 * Returns the username for this account.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the encrypted password for this account.
	 *
	 * @return the encrypted password
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * Returns the number of failed login attempts.
	 * 
	 * @return the failure count
	 */
	public int getFailureCount() {
		return failureCount;
	}

	/**
	 * Increments the failure count by one.
	 */
	public void incrementFailureCount() {
		failureCount++;
	}

	/**
	 * Resets the failure count to zero.
	 */
	public void resetFailureCount() {
		failureCount = 0;
	}

	/**
	 * Indicates whether the account is locked.
	 * 
	 * @return true if the account is locked, false otherwise
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Locks the user account, preventing further access attempts.
	 */
	public void lock() {
		locked = true;
	}

	/**
	 * Verifies that the provided plaintext password matches the stored encrypted
	 * password.
	 * 
	 * @param password the plaintext password
	 * @return true if the password is correct and the account is not locked
	 * @throws AccountLockedException     if the account is locked
	 * @throws PasswordIncorrectException if the password does not match
	 */
	public boolean checkPassword(String password) throws AccountLockedException, PasswordIncorrectException {
		if (isLocked()) {
			throw new AccountLockedException("User '" + username + "' account is locked.");
		}

		String encryptedInput = Utilities.encryptPassword(password);
		if (!encryptedPassword.equals(encryptedInput)) {
			throw new PasswordIncorrectException("Incorrect Password");
		}
		return true;

	}

	/**
	 * Compares this userAccout to another object for equality.
	 * 
	 * @param obj the object to compare with
	 * @return true if the objects represent the same user, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		return Objects.equals(username, other.username);
	}

	/**
	 * Returns the string representation of this UserAccount.
	 *
	 * @return the username
	 */
	@Override
	public String toString() {
		return username;
	}

}
