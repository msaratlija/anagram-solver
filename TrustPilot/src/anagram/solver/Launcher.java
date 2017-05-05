package anagram.solver;

import java.util.Scanner;

import anagram.solver.data.*;

public class Launcher {

	public static void main(String[] args) {

		String path;
		String phrase;
		String md5Hash;
		int numberOfWords;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your dictionary file system path: ");
		path = input.nextLine();
		System.out.println("Enter anagram phrase: ");
		phrase = input.nextLine();
		System.out.println("Enter md5 hash of phrase: ");
		md5Hash = input.nextLine();
		System.out.println("Enter maximum number of words: ");
		numberOfWords = input.nextInt();
		System.out.println("Entered data:");
		input.close();
		System.out.println("Path: " + path);
		System.out.println("Phrase: " +  phrase);
		System.out.println("MD5 hash: " + md5Hash);
		System.out.println("Number of words: " + numberOfWords);
		System.out.println("Searching...");
		
		Phrase phr = new Phrase(phrase);
		Dictionary dic = new Dictionary(path);
		dic.prepareDictionary(); 
		Find an = new Find(dic, phr, md5Hash, numberOfWords);
	}
}