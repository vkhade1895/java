package testers;

import java.util.*;

public class Q {

	public static void main(String[] args) {

		String str1 = "I live in A B";
		String str2 = "I live in ";

		List<String> str1Words = new ArrayList<>(Arrays.asList(str1.split(" ")));
		List<String> str2Words = new ArrayList<>(Arrays.asList(str2.split(" ")));
		List<String> str3Words = new ArrayList<>(str1Words);
		System.out.println("str1:" + str1Words);
		// Remove common words from str1Words
		str1Words.removeAll(str2Words); // removed str2 words from list

		// Remove common words from str2Words
		str2Words.removeAll(str3Words);

		// Concatenate the remaining words from str1Words and str2Words
		List<String> uniqueWords = new ArrayList<>();
		uniqueWords.addAll(str1Words);
		uniqueWords.addAll(str2Words);

		if (uniqueWords.isEmpty()) {
			System.out.println("List is empty.....");
		}
		System.out.println("unique worrds:"+uniqueWords);
		anagram();
		panagram();
	}

	public static void anagram() {

		String str1 = "Race";
		String str2 = "Care";

		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();

		// check if length is same
		if (str1.length() == str2.length()) {

			// convert strings to char array
			char[] charArray1 = str1.toCharArray();
			char[] charArray2 = str2.toCharArray();
			System.out.println(charArray1);
			// sort the char array
			Arrays.sort(charArray1);
			Arrays.sort(charArray2);

			// if sorted char arrays are same
			// then the string is anagram
			boolean result = Arrays.equals(charArray1, charArray2);

			if (result) {
				System.out.println(str1 + " and " + str2 + " are anagram.");
			} else {
				System.out.println(str1 + " and " + str2 + " are not anagram.");
			}
		} else {
			System.out.println(str1 + " and " + str2 + " are not anagram.");
		}
	}

	public static void panagram() {
		String pangram1 = "The quick brown fox jumps over the lazy dog"; // A classic pangram
		String pangram2 = "Pack my box with five dozen liquor jugs"; // Another pangram
		String notPangram = "Hello world"; // Not a pangram
		testPanagram(notPangram);
	}

	private static void testPanagram(String str) {
// Convert the string to lowercase to handle case-insensitivity
		str = str.toLowerCase();

		// Use a HashSet to store unique characters found in the string
		Set<Character> uniqueLetters = new HashSet<>();

		// Iterate through each character of the string
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			// Check if the character is a lowercase letter
			if (ch >= 'a' && ch <= 'z') {
				uniqueLetters.add(ch);
			}
		}
//		System.out.println("'" + str + "' is a pangram: " + isPangram(pangram1));
//		System.out.println("'" + str + "' is a pangram: " + isPangram(pangram2));
//		System.out.println("'" + str + "' is a pangram: " + isPangram(notPangram));
		// A string is a pangram if the set of unique letters contains all 26 English alphabets
		System.out.println("--" + uniqueLetters.size());

	}


}
