import java.util.ListIterator;

public class Playlist {

	private String name;
	private GenericLinkedList<Song> songs;
	private ListIterator<Song> iterator;
	private Song currentSong;

	public Playlist(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		this.songs = new GenericLinkedList<>();
		iterator = songs.iterator();
		currentSong = null;
	}

	public String getName() {
		return name;
	}

	public boolean addSong(Song song) {
		if (song == null) {
			return false;
		}
		songs.add(song);

		if (songs.size() == 1) {
			currentSong = songs.getFirst();
		}

		// reset iterator
		iterator = songs.iterator();
		return true;

	}

	public Song getCurrentSong() {
		return currentSong;
	}

	public int getSize() {
		return songs.size();
	}

	public GenericLinkedList<Song> getSongs() {
		GenericLinkedList<Song> copy = new GenericLinkedList<>();
		ListIterator<Song> it = songs.iterator();

		while (it.hasNext()) {
			copy.add(it.next());
		}
		return copy;

	}

	public boolean isEmpty() {
		return songs.isEmpty();
	}

	public Song nextSong() {
		if (songs.isEmpty()) {
			return null;
		}

		if (iterator.hasNext()) {
			currentSong = iterator.next();
			return currentSong;
		}
		return null;

	}

	public Song previousSong() {
		if (songs.isEmpty()) {
			return null;
		}

		if (iterator.hasPrevious()) {
			currentSong = iterator.previous();
			return currentSong;
		}
		return null;

	}

	public boolean removeSong(Song song) {
		if (song == null || songs.isEmpty())
			return false;

		boolean isRemoved = songs.remove(song);
		if (isRemoved) {
			iterator = songs.iterator();
			
	        if (!songs.isEmpty()) {
	            currentSong = songs.getFirst();
	        } else {
	            currentSong = null;
	        }
		}
		return isRemoved;
	}

}
