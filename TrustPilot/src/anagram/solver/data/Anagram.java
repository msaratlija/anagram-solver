package anagram.solver.data;

import java.util.ArrayList;
import java.util.List;

public class Anagram {

	private List<Integer> permutation;
	private List<String> listAnagram;
	private String anagram;
	private String remainingLetters;
	private int permutationIndex;
	private int nextLenght;
	private boolean isAnagram;
	private boolean isEndButNotAnagram;
	private static final String phraseSortedLetters = Phrase.getPhraseSorted();

	public List<Integer> getPermutation() {
		return permutation;
	}
	
	public List<String> getListAnagram() {
		return listAnagram;
	}

	public String getAnagram() {
		return anagram;
	}

	public String getRemainingLetters() {
		return remainingLetters;
	}

	public int getPermutationIndex() {
		return permutationIndex;
	}

	public int getNextLenght() {
		return nextLenght;
	}

	public boolean isAnagram() {
		return isAnagram;
	}

	public boolean isEndButNotAnagram() {
		return isEndButNotAnagram;
	}

	public void setPermutation(List<Integer> permutation) {
		this.permutation = permutation;
	}

	public void setListAnagram(List<String> listAnagram) {
		this.listAnagram = new ArrayList<>(listAnagram);
	}
	
	public void setAnagram(String anagram) {
		this.anagram = anagram;
	}

	public void setRemainingLetters(String remainingLetters) {
		this.remainingLetters = remainingLetters;
	}

	public void setPermutationIndex(int permutationIndex) {
		this.permutationIndex = permutationIndex;
	}

	public void setNextLenght(int nextLenght) {
		this.nextLenght = nextLenght;
	}

	public void setIsAnagram(boolean isAnagram) {
		this.isAnagram = isAnagram;
	}

	public void setIsEndButNotAnagram(boolean isEndButNotAnagram) {
		this.isEndButNotAnagram = isEndButNotAnagram;
	}

	public Anagram(List<Integer> perms) {
		this.permutation = perms;
		this.listAnagram = new ArrayList<>();
		this.anagram = "";
		this.remainingLetters = "";
		this.permutationIndex = 0;
		this.isAnagram = false;
		this.isEndButNotAnagram = false;
		this.nextLenght = permutation.get(permutationIndex);
	}

	public void addWord(String wr) {
		if (permutationIndex == 0) {
			anagram = wr;
			update(wr);
		} else if (permutation.size() > permutationIndex) {
			anagram = anagram + " " + wr;
			update(wr);
		}
	}

	private void update(String word) {
		listAnagram.add(word);
		permutationIndex++;
		remainingLetters = updateRemainingLetters();
		if (permutation.size() == permutationIndex) {
			if (remainingLetters.equals("")) {
				isAnagram = true;
			} else {
				isEndButNotAnagram = true;
			}
		} else {
			nextLenght = permutation.get(permutationIndex);
		}
	}

	private String updateRemainingLetters() {
		String anaWr = anagram.replaceAll("\\s+", "");
		StringBuilder remLett = new StringBuilder(phraseSortedLetters);
		for (int i = 0; i < anaWr.length(); i++) {
			for (int j = 0; j < remLett.length(); j++) {
				if (remLett.charAt(j) == anaWr.charAt(i)) {
					remLett.deleteCharAt(j);
					break;
				}
			}
		}
		return remLett.toString();
	}

	public Anagram copyAnagram() {
		Anagram an = new Anagram(permutation);
		an.setListAnagram(listAnagram);
		an.setAnagram(anagram);
		an.setRemainingLetters(remainingLetters);
		an.setPermutationIndex(permutationIndex);
		an.setNextLenght(nextLenght);
		an.setIsAnagram(isAnagram);
		an.setIsEndButNotAnagram(isEndButNotAnagram);
		return an;
	}
}