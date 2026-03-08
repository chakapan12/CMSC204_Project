import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaylistTestStudent {

	private Playlist playlist;

	@BeforeEach
	void setUp() throws Exception {
		playlist = new Playlist("My playlist");
		playlist.addSong(new Song("Song A", "Artist 1"));
		playlist.addSong(new Song("Song B", "Artist 2"));
	}

	@AfterEach
	void tearDown() throws Exception {
		playlist = null;
	}

	@Test
	void testPlaylist() {
		assertDoesNotThrow(() -> new Playlist("My playlist"));
	}

	@Test
	void testGetName() {
		assertEquals("My playlist", playlist.getName());
	}

	@Test
	void testAddSongAndGetSize() {
		playlist.addSong(new Song("Song C", "Artist 3")); // size = 3
		assertEquals(3, playlist.getSize());
	}

	@Test
	void testGetCurrentSong() {
		assertEquals("Song A", playlist.getCurrentSong().getTitle());
	}


	@Test
	void testGetSongs() {
		assertEquals("Song A", playlist.getSongs().getFirst().getTitle());
		assertEquals("Song B", playlist.getSongs().getLast().getTitle());
	}

	@Test
	void testIsEmpty() {
		assertFalse(playlist.isEmpty());
	}

	@Test
	void testNextSong() {
		assertEquals("Song A",  playlist.nextSong().getTitle());
    	assertEquals("Song B",  playlist.nextSong().getTitle());
	}

	@Test
	void testPreviousSong() {
		
		playlist.nextSong(); // return Song A (A ^ B )
		playlist.nextSong(); // return Song B (A B ^ )
		assertEquals("Song B", playlist.previousSong().getTitle()); // should return Song B (A ^ B)
	} 

	@Test
	void testRemoveSong() {
		assertTrue(playlist.removeSong(new Song("Song A", "Artist 1")));
		
	}

}
