/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a DictionaryEntry class that stores a word and its frequency count..
 * Due: 03/29/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * The DictionaryEntry class stores a word and its frequency. It implements
 * Comparable to allow sorting alphabetically by word.
 * 
 * @author Chakapan kanchana
 */
public class DictionaryEntry implements Comparable<DictionaryEntry> {

	/** The word stores in this entry */
	private String word;

	/** The number of timesthe word appears */
	private int frequency;

	/**
	 * Constructs a DictionaryEntry with a given word. 
	 * Initial frequency is set to 1.
	 * 
	 * @param word word the word to store
	 */
	public DictionaryEntry(String word) {
		if (word == null || word.isEmpty()) {
			throw new IllegalArgumentException("Word cannot be null or empty");
		}
		this.word = word;
		this.frequency = 1;
	}

	/**
	 * Increments the frequency count by 1.
	 */
	public void incrementFrequency() {
		frequency++;
	}

	/**
	 * Returns the word.
	 * 
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Returns the frequency of the word.
	 * 
	 * @return the frequency count
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Returns a string representation of the entry. Format: word + ": " + frequency
	 * 
	 * @return formatted string
	 */
	@Override
	public String toString() {
		return word + ": " + frequency;
	}

	/**
	 * Compares this object with another for equality. Two DictionaryEntry objects
	 * are equal if their words are equal.
	 * 
	 * @param obj the obj to compare
	 * @return true if words are equals, false otherwise
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		DictionaryEntry other = (DictionaryEntry) obj;
		return word.equals(other.word);
	}

	/**
	 * Compares this entry with another entry alphabeltically by word.
	 * 
	 * @param other the other DictionaryEntry to compare
	 * @return negative, zero, or positive based on alphabetical order
	 */
	@Override
	public int compareTo(DictionaryEntry other) {
		return word.compareTo(other.word);
	}

}
