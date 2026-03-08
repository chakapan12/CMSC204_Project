import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpotifyManagerTestStudent {
	
    private SpotifyManager manager;
    private File tempFile;
    private File tempFile2;
    private String testData;
    private String wrongFormatTestData;
    
	@BeforeEach
	void setUp() throws Exception {
		manager = new SpotifyManager();

        testData = 
            "# USER\n" +
            "username: demo\n" +
            "password: DM\n" +
            "playlist: Favorites\n" +
            "song: Imagine - John Lennon\n" +
            "song: Hello - Adele\n" +
            "playlist: Workout\n" +
            "song: Stronger - Kanye West\n" +
            "# USER\n" +
            "username: alice\n" +
            "password: 1234\n" +
            "playlist: Chill\n" +
            "song: Yellow - Coldplay\n" +
            "song: Someone Like You - Adele\n";
        
        wrongFormatTestData = 
	        "# USER\n" +
	        "playlist: Chill\n" +
	        "username: alice\n" +
	        "password: 1234\n" +
	        "song: Yellow - Coldplay\n" +
	        "song: Someone Like You - Adele\n";
 
        tempFile = File.createTempFile("test_users", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(testData.replace("\n", System.lineSeparator()));
        }
        
        tempFile2 = File.createTempFile("test_users1", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile2))) {
            writer.write(wrongFormatTestData.replace("\n", System.lineSeparator()));
        }
	}

	@AfterEach
	void tearDown() throws Exception {
		manager = null;
	}

	@Test
	void testLoadUsersFromFileAndFindUser() throws Exception {
		// Load users and their playlists from the temp file
        manager.loadUsersFromFile(tempFile.getAbsolutePath());
        
        // Find user demo
    	assertEquals("demo", manager.findUser("demo", "DM").getUsername());
        // Find user alice
    	assertEquals("alice", manager.findUser("alice", "1234").getUsername());
	}
	
	@Test
	void testLoadUsersFromWrongFormatFile() throws Exception {
		// Load users and their playlists from the temp file
		assertThrows(InvalidUserFormatException.class, 
				() -> manager.loadUsersFromFile(tempFile2.getAbsolutePath()));
        
	}

	@Test
	void testGetUsers() throws Exception {
		// Load users and their playlists from the temp file
        manager.loadUsersFromFile(tempFile.getAbsolutePath());
        
        assertEquals("demo", manager.getUsers().get(0).getUsername());
        assertEquals("alice", manager.getUsers().getLast().getUsername());
        
	}

}
