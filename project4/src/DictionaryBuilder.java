/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class builds and manages a dictionary using a hash table 
 *              with separate chaining. It stores words from a file, tracks 
 *              total and unique words, and supports word searching, removal, 
 *              and listing.
 * Due: 03/29/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The DictionaryBuilder class stores words in a hash table using linked lists
 * for collision handling. It can load words from a file, add words, remove
 * words, retrieve frequencies, and return all stored words in sorted order.
 * 
 * @author Chakapan Kanchana
 */
public class DictionaryBuilder {

	/** Hash table of linked lists storing dictionary entries */
	private MyLinkedList<DictionaryEntry>[] dictionary;

	/** Total number of words added, including duplicates */
	private int totalWords;

	/** Total number of unique words in the dictionary */
	private int uniqueWords;

	/** Desired maximum load factor used when estimating table size */
	public static final double LOAD_FACTOR = 0.6;

	/** 
	 * Constructs a DictionaryBuilder with an estimated number of entries. The table
	 * size is adjusted to the next prime number of the form 4k + 3.
	 * 
	 * @param estimateEntries estimated number of unique entries
	 */
	@SuppressWarnings("unchecked")
	public DictionaryBuilder(int estimateEntries) {

		// Ensure minimum size to reduce excessive collisions for very small files.
		if (estimateEntries < 10) {
			estimateEntries = 10;
		}

		int recommendedSize = (int) Math.ceil(estimateEntries / LOAD_FACTOR);
		int tableSize = nextPrime4kPlus3(recommendedSize);

		dictionary = (MyLinkedList<DictionaryEntry>[]) new MyLinkedList[tableSize];
		totalWords = 0;
		uniqueWords = 0;
	}

	/**
	 * Constructs a DictionaryBuilder and loads words from given file.
	 * 
	 * @param filename the file containing words
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public DictionaryBuilder(String filename) throws FileNotFoundException {
		this(estimatedUniqueWordsFromFile(filename));
		loadWordsFromFile(filename);
	}

	/**
	 * Estemates the number of unique words based on file size.
	 * 
	 * @param filename the file to estimate from
	 * @return estimated number of unique words
	 * @throws FileNotFoundException if the file does not exist
	 */
	private static int estimatedUniqueWordsFromFile(String filename) throws FileNotFoundException {
		File file = new File(filename);

		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + filename);
		}
		long fileSize = file.length();
		
		// Rough estimate: estimated unique word = file size in byte / 100
		int estimatedUniqueWords = (int) Math.ceil(fileSize / 100.0);

		return estimatedUniqueWords;
	}

	/**
	 * Reads words from the file and adds them to the dictinary.
	 * 
	 * @param filename the file to read
	 * @throws FileNotFoundException if the file cannot be found
	 */
	private void loadWordsFromFile(String filename) throws FileNotFoundException {
		Scanner input = new Scanner(new File(filename));

		while (input.hasNext()) {
			String eachWord = input.next();
			addWord(eachWord);
		}
		input.close();
	}

	/**
	 * Adds a word to the dictionary. If the word already exists, its frequency is
	 * incrementd. Otherwise, a new DictionaryEntry is created.
	 * 
	 * @param word the word to add
	 */
	public void addWord(String word) {
		String sanitizedWord = sanitizeWord(word);

		if (sanitizedWord.isEmpty())
			return;

		int index = getBucketIndex(sanitizedWord);

		totalWords++;

		if (dictionary[index] == null)
			dictionary[index] = new MyLinkedList<>();

		DictionaryEntry currentEntry = dictionary[index].findWord(sanitizedWord);
		if (currentEntry != null) {
			currentEntry.incrementFrequency();
		} else {
			dictionary[index].add(new DictionaryEntry(sanitizedWord));
			uniqueWords++;
		}

	}

	/**
	 * Returns the frequency of a word in the sictionary.
	 * 
	 * @param word the word to search for
	 * @return the frequency of the word, or 0 if not found
	 */
	public int getFrequency(String word) {
		String sanitizedWord = sanitizeWord(word);

		int index = getBucketIndex(sanitizedWord);

		if (dictionary[index] == null) {
			return 0;
		}

		DictionaryEntry currentEntry = dictionary[index].findWord(sanitizedWord);

		if (currentEntry == null) {
			return 0;
		}

		return currentEntry.getFrequency();

	}

	/**
	 * Removes a word from the dictionary.
	 * 
	 * @param word the word to remove
	 * @throws DictionaryEntryNotFoundException if the word is not found
	 */
	public void removeWord(String word) throws DictionaryEntryNotFoundException {
		String sanitizedWord = sanitizeWord(word);

		if (sanitizedWord.isEmpty())
			throw new DictionaryEntryNotFoundException("Word not found.");

		int index = getBucketIndex(sanitizedWord);

		if (dictionary[index] == null) {
			throw new DictionaryEntryNotFoundException("\"" + sanitizedWord + "\"" + " not found.");
		}
		DictionaryEntry currentEntry = dictionary[index].findWord(sanitizedWord);

		if (currentEntry == null)
			throw new DictionaryEntryNotFoundException("\"" + sanitizedWord + "\"" + " not found.");

		boolean isRemoved = dictionary[index].removeWord(sanitizedWord);

		if (isRemoved) {
			uniqueWords--;
			totalWords -= currentEntry.getFrequency();
		}
	}

	/**
	 * Returns all unique words in the dictionary in sorted order.
	 * 
	 * @return a sorted list of all words
	 */
	public List<String> getAllWords() {

		List<String> words = new ArrayList<>();

		// Traverse each bucket in the hash table
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i] != null && !dictionary[i].isEmpty()) {
				String[] bucketWords = dictionary[i].toWordArray();

				for (int j = 0; j < bucketWords.length; j++) {
					words.add(bucketWords[j]);
				}
			}
		}
		Collections.sort(words);
		return words;
	}

	/**
	 * Returns the total of words added, including duplicates.
	 * 
	 * @return thtal number of words
	 */
	public int getTotalWords() {
		return totalWords;
	}

	/**
	 * Return the total of unique words in the dictionary.
	 * 
	 * @return number of unique words
	 */
	public int getTotalUniqueWords() {
		return uniqueWords;
	}

	/**
	 * Return the current load factor of the hash table.
	 * 
	 * @return load factor
	 */
	public double getLoadFactor() {
		return (double) uniqueWords / dictionary.length;
	}

	/**
	 * Rreturns the size of the hash table
	 * 
	 * @return table size
	 */
	public int getTableSize() {
		return dictionary.length;
	}

	/**
	 * Computes the bucket index for a given word.
	 * 
	 * @param word the word to hash
	 * @return the bucket index
	 */
	private int getBucketIndex(String word) {
		return Math.abs(word.hashCode()) % dictionary.length;
	}

	/**
	 * Sanitizes a word by converting it to lowercase and removing all
	 * non-alphbetical characters.
	 * 
	 * @param word the word to sanitize
	 * @return sanitized word, or empty if input is null
	 */
	public String sanitizeWord(String word) {
		if (word == null)
			return "";
		return word.toLowerCase().replaceAll("[^a-z]", "");
	}

	/**
	 * Finds the next prime number greater than or equal to n that is also of the
	 * form of 4k + 3.
	 * 
	 * @param n starting number
	 * @return the next prime number of form 4k + 3
	 */
	private int nextPrime4kPlus3(int n) {
		int num = n;

		// Movefirst number that satisfies 4k + 3
		while (num % 4 != 3) {
			num++;
		}

		// Keep checking only numbers of the form 4k + 3
		while (!isPrime(num)) {
			num += 4;
		}
		
		return num;
	}

	/**
	 * Checks whether a number is prime.
	 * 
	 * @param n the number to check
	 * @return true if n is prime, false otherwise
	 */
	private boolean isPrime(int n) {

		// Corner case
		if (n <= 1)
			return false;

		// 2 and 3 are prime
		if (n == 2 || n == 3)
			return true;

		// Multiple of 2 or 3 are not prime
		if (n % 2 == 0 || n % 3 == 0)
			return false;

		// Check for remaining possible factors
		for (int i = 5; i <= Math.sqrt(n); i = i + 6)
			if (n % i == 0 || n % (i + 2) == 0)
				return false;

		return true;
	}

}
