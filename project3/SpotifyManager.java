import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ListIterator;

public class SpotifyManager {

	private GenericLinkedList<User> users;

	public SpotifyManager() {
		users = new GenericLinkedList<User>();
	}

	public void loadUsersFromFile(String filename) throws IOException, InvalidUserFormatException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

			User currentUser = null;
			Playlist currentPlaylist = null;
			String username = null;
			String password = null;
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}
				// Prepares for new user
				if (line.equals("# USER")) {
					currentUser = null;
					currentPlaylist = null;
					username = null;
					password = null;

				} else if (line.startsWith("username:")) {
					username = line.substring(line.indexOf(':') + 1).trim();
				} else if (line.startsWith("password:")) {
					password = line.substring(line.indexOf(':') + 1).trim();

					// Validates username and password
					if (username == null || username.isBlank() || password == null || password.isBlank()) {
						br.close();
						throw new InvalidUserFormatException("Invalid user format");
					}

					currentUser = new User(username, password);
					users.add(currentUser);

				} else if (line.startsWith("playlist:")) {
					if (currentUser == null) {
						throw new InvalidUserFormatException("Playlist found before user");
					}

					String playlistName = line.substring(line.indexOf(':') + 1).trim();
					currentPlaylist = new Playlist(playlistName);
					currentUser.addPlaylist(currentPlaylist);

				} else if (line.startsWith("song:")) {
					if (currentPlaylist == null) {
						throw new InvalidUserFormatException("Song found before playlist");
					}

					String[] parts = line.substring(line.indexOf(':') + 1).trim().split("-", 2);
					if (parts.length != 2) {
						throw new InvalidUserFormatException("Invalid song format");
					}
					
					String title = parts[0].trim();
					String artist = parts[1].trim();
					
					currentPlaylist.addSong(new Song(title, artist));
					
				} else {
					throw new InvalidUserFormatException("Unknown line format" + line);
				}
			}
		}

	}

	public User findUser(String username, String password) throws UserNotFoundException, InvalidPasswordException {

		ListIterator<User> it = users.iterator();

		while (it.hasNext()) {
			User currentUser = it.next();

			if (currentUser.getUsername().equals(username)) {
				if (currentUser.getPassword().equals(password)) {
					return currentUser;
				} else {
					throw new InvalidPasswordException("Invalid password");
				}
			}
		}
		throw new UserNotFoundException("User not found");
	}

	public GenericLinkedList<User> getUsers() {
		return users;
	}

}
