import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SongTestStudent {

	Song song;

	@BeforeEach
	void setUp() throws Exception {
		song = new Song("Hello", "Chakapan");
	}

	@AfterEach
	void tearDown() throws Exception {
		song = null;
	}

	@Test
	void testSong() {
		assertDoesNotThrow(() -> new Song("Hello", "chakapan"));
	}

	@Test
	void testGetTitle() {
		assertEquals("Hello", song.getTitle());
	}

	@Test
	void testGetArtist() {
		assertEquals("Chakapan", song.getArtist());
	}

	@Test
	void testToString() {
		assertEquals("Hello by Chakapan", song.toString());
	}
	
	@Test
	void testToEquals() {
		Song song1 = new Song("Bye", "Chakapan");
		Song song2 = new Song("Bye", "Chakapan");
		assertTrue(song1.equals(song2));
	}
	
	

}
