/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class contains public JUnit 5 tests used to validate a UserAccount Class
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains public JUnit 5 tests used to validate a UserAccount Class.
 */
class UserAccountTestStudent {

	UserAccount user;
	private String username;
	private String plainPassword;
	private String encryptedPassword;

	/**
	 * Creates a fresh UserAccount before each test.
	 */
	@BeforeEach
	void setUp() throws Exception {
		username = "bob";
		plainPassword = "abc123";
		encryptedPassword = Utilities.encryptPassword(plainPassword);
		user = new UserAccount(username, encryptedPassword);
	}

	/**
	 * Cleans up references after each test.
	 */
	@AfterEach
	void tearDown() throws Exception {
		user = null;
		username = null;
		plainPassword = null;
		encryptedPassword = null;

	}

	/**
	 * Verifies that the constructor does not throw exceptions for valid inputs.
	 */
	@Test
	void testUserAccount() {
		assertDoesNotThrow(() -> new UserAccount("bob", "pass123"));
	}

	/**
	 * Verifies getUsername() returns the username that was set in setUp().
	 */
	@Test
	void testGetUsername() {
		assertEquals("bob", user.getUsername());
	}

	/**
	 * Verifies getEncryptedPassword() returns the encrypted password stored in the
	 * object.
	 */
	@Test
	void testGetEncryptedPassword() {
		assertEquals(encryptedPassword, user.getEncryptedPassword());
	}

	/**
	 * Verifies failure count starts at 0 and increments by 1 after
	 * incrementFailureCount().
	 */
	@Test
	void testGetFailureCountAndIncrementFailureCount() {
		assertEquals(0, user.getFailureCount());
		user.incrementFailureCount();
		assertEquals(1, user.getFailureCount());
	}

	/**
	 * Verifies resetFailureCount() sets the failure count back to 0.
	 */
	@Test
	void testResetFailureCount() {
		user.incrementFailureCount();
		user.resetFailureCount();
		assertEquals(0, user.getFailureCount());
	}

	/**
	 * Verifies a new account is not locked initially.
	 */
	@Test
	void testIsLocked() {
		// A user account initially is not locked. Method should return false.
		assertFalse(user.isLocked());
	}

	/**
	 * Verifies lock() changes the account state to locked.
	 */
	@Test
	void testLock() {
		user.lock();
		assertTrue(user.isLocked());
	}

	/**
	 * Tests checkPassword behavior:
	 */
	@Test
	void testCheckPassword() {

		// Correct password: should return true and not throw
		try {
			assertTrue(user.checkPassword("pass123"));
		} catch (Exception ignores) {

		}

		// Incorrect password: should throw PasswordIncorrectException
		assertThrows(PasswordIncorrectException.class, () -> user.checkPassword("123"));

		// Locked account: should throw AccountLockedException even if password is
		// correct
		user.lock();
		assertThrows(AccountLockedException.class, () -> user.checkPassword("pass123"));

	}

	/**
	 * Verifies equals() returns true for two accounts with the same username.
	 */
	@Test
	void testEqualsObject() {
		UserAccount sameUser = new UserAccount("bob", "pass123");
		assertTrue(user.equals(sameUser));
	}

	/**
	 * Verifies toString() returns the username.
	 */
	@Test
	void testToString() {
		assertEquals("bob", user.toString());
	}

}
