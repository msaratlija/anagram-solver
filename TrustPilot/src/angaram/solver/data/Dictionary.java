package angaram.solver.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dictionary {
	
	private List<String> wordList;
	private Set<String> wordListSet;
	// K - word sorted alphabetically L - list of words which matches letters
	private Map<String, List<String>> sortedWordsMap;  
	// K - length of word V - all words with same length
	private Map<Integer, List<String>> sortedLenghtMap;
	
	public Dictionary(String path) {
		try {
			readFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getWordList() {
		return wordList;
	}

	public Map<String, List<String>> getSortedWordsMap() {
		return sortedWordsMap;
	}

	public Map<Integer, List<String>> getSortedLenghtMap() {
		return sortedLenghtMap;
	}

	
	private void readFile(String path) throws IOException {
		String line;
		boolean endOfList = false;
		this.wordListSet = new HashSet<>();
		FileReader fileRead = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fileRead);
		while (!endOfList) {
			line = textReader.readLine();
			if (line != null) {
				this.wordListSet.add(line);
			}else {
				textReader.close();
				break;
			}
		}
		wordList = new ArrayList<>(wordListSet);
	}
	
	public void prepareDictionary() {
		removeWords();
		sortAlphabeticallyAndByLenght();
	}
	
	private void removeWords() {
		List<String> wL = new ArrayList<>();
		Phrase phr = new Phrase();
		Map<Character, Integer> letApp = phr.getPhrLettApperance();
		for (int i = 0; i < wordList.size(); i++) {
			if(wordControl(wordList.get(i), letApp, phr.getPhraseLenght())) {
				wL.add(wordList.get(i));
			}
		}
		wordList.clear();
		wordList.addAll(wL);
	}
	
	private boolean wordControl(String word, Map<Character, Integer> letApp, int phLen) {
		char[] c = word.toCharArray();
		Map<Character, Integer> charApp = new HashMap<>();
		for (int i = 0; i < c.length; i++) {
			if(!(letApp.containsKey(c[i]) || c[i] == '\'')) return false;
			if(charApp.containsKey(c[i])) {
				charApp.put(c[i], charApp.get(c[i]) + 1);
			}else {
				charApp.put(c[i], 1);
			}
		}
		charApp.remove('\'');
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
		for (String w : wordList) {
			List<String> tmpList = new ArrayList<>();
			String word = sortWordAlphabetically(w);
			int length = word.length();
			if(sortedLenghtMap.containsKey(length)) {
				tmpList = sortedLenghtMap.get(length);
				tmpList.add(word); 
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
	
	private String sortWordAlphabetically(String word) {
		word = word.replace("'", "");
		char[] ch = word.toCharArray();
		Arrays.sort(ch);
		return String.copyValueOf(ch);
	}
	
	

}
