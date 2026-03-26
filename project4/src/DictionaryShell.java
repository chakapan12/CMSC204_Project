/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class provides a command-line interface for interacting 
 *              with the DictionaryBuilder. Users can add, delete, search, 
 *              list words, view statistics, and exit the program.
 * Due: 03/29/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * The DictionaryShell class provides a simple command-line interface for
 * managing a dictionary built with DictionaryBuilder.
 * 
 * @author Chakapan Kanchana
 */
public class DictionaryShell {

	/**
	 * Starts the dictionary shell program. If a filename is provided as a
	 * command-line argument, the dictionary is loaded from that file. Otherwise, an
	 * empty dictionary is created.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		DictionaryBuilder db = null;

		if (args.length == 0) {
			System.out.println("No command-line arguments provided.");
			db = new DictionaryBuilder(10);
		} else {
			System.out.println("Command-line arguments:");
			System.out.println("args[0]: " + args[0]);

			try {
				// Load dictionary from file
				db = new DictionaryBuilder(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				System.out.println("Using empty dictionary instead.");
			}
		}

		// If file was not found, create an empty dictionary.
		if (db == null) {
			db = new DictionaryBuilder(10);
		}

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("\nWelcome to Dictionary Builder CLI.");
			System.out.println("Available commands: add <word>, delete <word>, search <word>, list, stats, exit");

			boolean exit = false;

			while (!exit) {

				System.out.print("> ");

				// Read and trimp user input
				String line = sc.nextLine().trim();

				if (line.isEmpty()) {
					continue;
				}

				// Split into command and argument
				String[] parts = line.split("\\s");
				String command = parts[0];

				try {
					switch (command) {

					case "add": {
						if (parts.length != 2) {
							throw new IllegalArgumentException("Invalid command arguments.");
						}
						String word = parts[1];
						db.addWord(word);
						System.out.println("\"" + db.sanitizeWord(word) + "\" added.\n");
						break;
					}

					case "delete": {
						if (parts.length != 2) {
							throw new IllegalArgumentException("Invalid command arguments.");
						}
						String deletedWord = parts[1];
						db.removeWord(deletedWord);
						System.out.println("\"" + db.sanitizeWord(deletedWord) + "\" deleted.\n");
						break;
					}
					case "search": {
						if (parts.length != 2) {
							throw new IllegalArgumentException("Invalid command arguments.");
						}
						String searchWord = parts[1];
						int frequency = db.getFrequency(searchWord);
						if (frequency == 0) {
							System.out.println("\"" + db.sanitizeWord(searchWord) + "\" not found.\n");
						} else {
							System.out.println(
									frequency + " instance(s) of \"" + db.sanitizeWord(searchWord) + "\" found.\n");
						}
						break;
					}
					case "list": {
						List<String> words = db.getAllWords();
						for (String eachWord : words) {
							System.out.println(eachWord);
						}
						System.out.println();
						break;
					}

					case "stats": {
						System.out.println("Total words: " + db.getTotalWords());
						System.out.println("Total unique words: " + db.getTotalUniqueWords());
						System.out.printf("Estimated load factor: %.2f\n\n", db.getLoadFactor());
						break;
					}

					case "exit": {
						System.out.println("Quitting...");
						exit = true;
						break;
					}

					default:
						throw new IllegalArgumentException("Invalid command.");
					}

				} catch (IllegalArgumentException | DictionaryEntryNotFoundException e) {
					System.out.println(e.getMessage() + "\n");
				}
			}
		}
	}
}
