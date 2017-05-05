package anagram.solver.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Phrase {

	private static String phrase;
	private int phraseLenght;
	private static String phraseSorted;
	private Map<Character, Integer> phrLettApperance;
	
	public Phrase() {
		this.phraseLenght = sortPhr(phrase).length();
		this.phrLettApperance = phraseLetterApperance(sortPhr(phrase).toCharArray());
	}
	
	public Phrase(String inPhrase) {
		Phrase.phrase = inPhrase;
		this.phraseLenght = sortPhr(inPhrase).length();
		Phrase.phraseSorted = new String (sortPhr(inPhrase));
		this.phrLettApperance = phraseLetterApperance(sortPhr(inPhrase).toCharArray());
	}
	
	public static String getPhrase() {
		return phrase;
	}
	
	public int getPhraseLenght() {
		return phraseLenght;
	}

	public static String getPhraseSorted() {
		return phraseSorted;
	}
	
	public Map<Character, Integer> getPhrLettApperance() {
		return phrLettApperance;
	}
	
	public static void setPhrase(String phrase) {
		Phrase.phrase = phrase;
	}
	
	private String sortPhr(String inPhrase) {
		String phr = inPhrase.replaceAll("\\s+", "");
		char[] ch = phr.toCharArray();
		Arrays.sort(ch);
		return new String(ch);
	}

	private Map<Character, Integer> phraseLetterApperance(char[] phrArr) {
		Map<Character, Integer> phLeAp = new HashMap<>();
		for (int i = 0; i < phrArr.length; i++) {
			if (phLeAp.containsKey(phrArr[i])) {
				phLeAp.put(phrArr[i], phLeAp.get(phrArr[i]) + 1);
			}else {
				phLeAp.put(phrArr[i], 1);
			}
		}
		return phLeAp;
	}
	
}
