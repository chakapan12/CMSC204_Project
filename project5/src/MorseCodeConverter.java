/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: Utility class that converts Morse code into English text
 *              using the MorseCodeTree structure.
 * Due: 04/12/2026
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
import java.util.Scanner;

/**
 * Utility class that converts Morse code into English text using the
 * MorseCodeTree structure.
 */
public class MorseCodeConverter {

	private static MorseCodeTree tree = new MorseCodeTree();

	/**
	 * Converts a Morse code string into English text. Words in Morse code are
	 * separated by '/' and letters are separated by spaces.
	 *
	 * @param code the Morse code string to be converted
	 * @return the English translation of the Morse code
	 */
	public static String convertToEnglish(String code) {
		StringBuilder sb = new StringBuilder();
		String[] words = code.split("/");

		for (int i = 0; i < words.length; i++) {
			String[] eachLetter = words[i].split(" ");
			for (int j = 0; j < eachLetter.length; j++) {
				if (!eachLetter[j].equals("")) {
					sb.append(tree.fetch(eachLetter[j]));
				}
			}
			sb.append(" ");
		}
		return sb.toString().strip();
	}

	/**
	 * Converts Morse code from a file into English text. The file contains Morse
	 * code where letters are separated by spaces and words are separated by '/'.
	 *
	 * @param file the file containing Morse code
	 * @return the English translation of the Morse code in the file
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public static String convertToEnglish(File file) throws FileNotFoundException {
		try (Scanner sc = new Scanner(file)) {
			StringBuilder sb = new StringBuilder();

			while (sc.hasNext()) {
				String letter = sc.next();
				if (!letter.equals("/")) {
					sb.append(tree.fetch(letter));
				} else {
					sb.append(" ");
				}

			}
			return sb.toString().strip();
		}

	}

	/**
	 * Returns a string representation of the Morse code tree using inorder
	 * traversal.
	 *
	 * @return a string containing all tree elements in inorder sequence
	 */
	public static String printTree() {
		ArrayList<String> list = tree.toArrayList();
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s).append(" ");
		}
		return sb.toString().strip();

	}
}
