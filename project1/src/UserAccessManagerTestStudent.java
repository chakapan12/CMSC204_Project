/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class contains public JUnit 5 tests used to validate a UserAccessManager Class.
 * Due: 02/08/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains public JUnit 5 tests used to validate a UserAccessManager Class.
 */
class UserAccessManagerTestStudent {

	private UserAccessManager uam;

	/**
	 * Creates a fresh UserAccessManager before each test.
	 */
	@BeforeEach
	void setUp() throws Exception {
		uam = new UserAccessManager();
	}

	/**
	 * Cleans up after each test.
	 */
	@AfterEach
	void tearDown() {
		uam = null;
	}

	/**
	 * Verifies the constructor does not throw for normal instantiation.
	 */
	@Test
	void testUserAccessManager() {
		assertDoesNotThrow(() -> new UserAccessManager());
		assertNotNull(uam);
	}

	@Test
	void testLoadAccounts() {
		assertDoesNotThrow(() -> uam.loadAccounts("fake.txt"));
	}

	/**
	 * Verifies addUser(): 
	 * - does not throw for valid username + encrypted password
	 * - throws InvalidCommandException for invalid inputs 
	 * - throws DuplicateUserException if username already exists
	 */
	@Test
	void testAddUser() {

		// Valid add: should not throw
		assertDoesNotThrow(() -> uam.addUser("top", Utilities.encryptPassword("pass")));

		// Empty username: should throw
		assertThrows(InvalidCommandException.class, () -> uam.addUser("", Utilities.encryptPassword("pass")));

		// Empty password: should
		assertThrows(InvalidCommandException.class, () -> uam.addUser("top2", ""));

		// Duplicate username: should throw (since "top" already added above)
		assertThrows(DuplicateUserException.class, () -> uam.addUser("top", Utilities.encryptPassword("pass")));

	}

	/**
	 * Tests removeUser() behavior: 
	 * - Removing invalid username should throw InvalidCommandException. 
	 * - Removing a user that doesn't exist should throw UserNotFoundException.
	 */
	@Test
	void testRemoveUser() {
		
		String encryptedPassword = Utilities.encryptPassword("pass");
		
		// Add a user account before removing.
		assertDoesNotThrow(() -> uam.addUser("top", encryptedPassword));
	
		// Removing a valid username
		assertDoesNotThrow(() -> uam.removeUser("top"));
		
		// Verifying access after removing. Should throw because the user does not exist.
		assertThrows(UserNotFoundException.class, () -> uam.verifyAccess("top", encryptedPassword));

		// Removing an invalid username should throw
		assertThrows(InvalidCommandException.class, () -> uam.removeUser(""));

		// Removing a user that does not exist should throw
		assertThrows(UserNotFoundException.class, () -> uam.removeUser("top"));

	}

	/**
	 * Tests verifyAccess(). 
	 * - returns true for valid username and encrypted password 
	 * - throws InvalidCommandException for invalid input 
	 * - throws UserNotFoundException when user does not exist 
	 * - throws PasswordIncorrectException for incorrect password 
	 * - throws AccountLockedException when account is locked
	 */
	@Test
	void testVerifyAccess() {

		String username = "top";
		String newUsername = "tom";
		String encryptedPassword = Utilities.encryptPassword("pass");
		String wrongEncryptedPassword = Utilities.encryptPassword("wrong");

		// Add a user account before verifying access.
		assertDoesNotThrow(() -> uam.addUser(username, encryptedPassword));

		// Valid username and encrypted password should return true.
		assertDoesNotThrow(() -> assertTrue(uam.verifyAccess(username, encryptedPassword)));

		// Empty username should throw.
		assertThrows(InvalidCommandException.class, () -> uam.verifyAccess("", encryptedPassword));

		// User does not exist should throw.
		assertThrows(UserNotFoundException.class, () -> uam.verifyAccess("bob", encryptedPassword));

		// Valid username and invalid encryptedPassword should throw.
		assertThrows(PasswordIncorrectException.class, () -> uam.verifyAccess(username, wrongEncryptedPassword));
		
		// Locked user should throw even if password is correct
		uam.findUser(username).lock();
		assertThrows(AccountLockedException.class, () -> uam.verifyAccess(username, encryptedPassword));
		
	 
		// Add a new user account before Attempt 3 incorrect passwords (ignore exceptions)
		assertDoesNotThrow(() -> uam.addUser(newUsername, encryptedPassword));	
        for (int i = 0; i < 3; i++) {
            try {
                uam.verifyAccess(newUsername, wrongEncryptedPassword); 
            } catch (Exception ignored) {}
        }
        // 4th attempt must throw AccountLockedException
        assertThrows(AccountLockedException.class, () -> uam.verifyAccess(newUsername, encryptedPassword));

	}

	/**
	 * Tests findUser(): 
	 * - Returns a non-null UserAccount for an existing user 
	 * - Returns null for a user that doesn't exist
	 */
	@Test
	void testFindUser() {
		// Add a user before lookup testing
		assertDoesNotThrow(() -> uam.addUser("top", Utilities.encryptPassword("pass")));

		// Existing user should be found
		assertNotNull(uam.findUser("top"));

		// Non-existing user should return null
		assertNull(uam.findUser("bob"));

	}

}
