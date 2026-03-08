import java.util.Objects;

public class Song {

	private String title;
	private String artistName;

	public Song(String title, String artistName) {
		this.title = title;
		this.artistName = artistName;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artistName;
	}

	@Override
	public String toString() {
		return title + " by " + artistName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		return Objects.equals(artistName, other.artistName) && Objects.equals(title, other.title);
	}

}
