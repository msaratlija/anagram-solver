package angaram.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrepareDictionary {
	
	// K - word sorted alphabetically L - list of words which matches letters
	private Map<String, List<String>> sortedWordsMap;  
	// K - length of word V - all words with same length
	private Map<Integer, List<String>> sortedLenghtMap;
	
	public PrepareDictionary() {
		removeWords();
		sortAlphabeticallyAndByLenght();
		
		
	}
	
	
	public void removeWords() {
		List<String> wordList = Dictionary.getWordList();
		List<String> wL = new ArrayList<>();
		Phrase phr = new Phrase();
		Map<Character, Integer> letApp = phr.getPhrLettApperance();
		for (int i = 0; i < wordList.size(); i++) {
			if(wordControl(wordList.get(i), letApp, phr.getPhraseLenght())) {
				wL.add(wordList.get(i));
			}
		}
		Dictionary.getWordList().clear();
		Dictionary.setWordList(wL);
	}
	
	
	private boolean wordControl(String word, Map<Character, Integer> letApp, int phLen) {
		char[] c = word.toCharArray();
		Map<Character, Integer> charApp = new HashMap<>();
		//checks if contain equal letters and maps the word
		for (int i = 0; i < c.length; i++) {
			if(!(letApp.containsKey(c[i]) || c[i] == '\'')) return false;
			if(charApp.containsKey(c[i])) {
				charApp.put(c[i], charApp.get(c[i]) + 1);
			}else {
				charApp.put(c[i], 1);
			}
		}
		charApp.remove('\'');
		//check if contains equal or less characters
		for (Map.Entry<Character, Integer> ch : charApp.entrySet()) {
			if(!(letApp.get(ch.getKey()) >= ch.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	private void sortAlphabeticallyAndByLenght() {
		sortedWordsMap = new HashMap<>();
		sortedLenghtMap = new HashMap<>();
		for (String w : Dictionary.getWordList()) {
			List<String> tmpList = new ArrayList<>();
			String word = sortWordAlphabetically(w);
			int length = word.length();
			if(sortedLenghtMap.containsKey(length)) {
				tmpList = sortedLenghtMap.get(length);
				tmpList.add(word); //word sorted alphabetically
				sortedLenghtMap.put(length, tmpList);
			}else {
				tmpList.add(word);
				sortedLenghtMap.put(length, tmpList);
			}
			tmpList = new ArrayList<>();
			if(sortedWordsMap.containsKey(word)) {
				tmpList = sortedWordsMap.get(word);
				tmpList.add(w);
				sortedWordsMap.put(word, tmpList);
			}else {
				tmpList.add(w);
				sortedWordsMap.put(word, tmpList);
			}
		}
	}
	
	public String sortWordAlphabetically(String word) {
		word = word.replace("'", "");
		char[] ch = word.toCharArray();
		Arrays.sort(ch);
		return String.copyValueOf(ch);
	}
	

}
