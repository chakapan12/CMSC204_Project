import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTestStudent {

	private User user;
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User("chakapan", "1234");
		user.addPlaylist(new Playlist("playlist1")); // playlist count = 1
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
	}

	@Test
	void testUser() {
		assertDoesNotThrow(() -> new User("chakapan", "1234"));
	}

	@Test
	void testAddPlaylistAndGetCount() {
		user.addPlaylist(new Playlist("playlist2")); // playlist count = 2
		assertEquals(2, user.getPlaylistCount());
	}

	@Test
	void testGetPlaylists() {
		assertEquals("playlist1", user.getPlaylists().getFirst().getName());
	}

	@Test
	void testGetUsername() {
		assertEquals("chakapan", user.getUsername());
	}

	@Test
	void testGetPassword() {
		assertEquals("1234", user.getPassword());
	}

}
