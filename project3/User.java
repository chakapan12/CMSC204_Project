
public class User {

	private String username;
	private String password;
	private GenericLinkedList<Playlist> playlists;

	public User(String username, String password) {
		if (username == null || username.isBlank() || password == null || password.isBlank()) {
			throw new IllegalArgumentException();
		}

		this.username = username;
		this.password = password;
		this.playlists = new GenericLinkedList<>();
	}

	public void addPlaylist(Playlist playlist) {
		playlists.add(playlist);
	}

	public int getPlaylistCount() {
		return playlists.size();
	}

	public GenericLinkedList<Playlist> getPlaylists() {
		return playlists;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
