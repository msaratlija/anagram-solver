package anagram.solver;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import anagram.solver.data.*;

public class Find {

	private List<List<Integer>> perms;
	private String md5;

	public Find(Dictionary dic, Phrase ph, String md5, int numOfWords) {
		this.md5 = md5;
		// only big set of data, so the size represent 
		// the word with maximum length in dictionary,
		// otherwise find the max length 
		int maxWrLen = dic.getSortedLenghtMap().size();
		int phrLen = ph.getPhraseLenght();
		Trie dicTrie = new Trie();
		
		permutations(maxWrLen, numOfWords, phrLen);
		createDictionaryTrie(dic, dicTrie);

		try {
			findAnagram(dic, dicTrie);
		} catch (Exception e) {
			System.out.println("Give me the job :)");
		}
	}

	private void createDictionaryTrie(Dictionary dic, Trie dicTrie) {
		Map<String, List<String>> dicList = dic.getSortedWordsMap();
		for (Map.Entry<String, List<String>> list : dicList.entrySet()) {
			dicTrie.insert(list.getKey());
		}
	}

	private void permutations(int maxWrLen, int numOfWr, int phrLen) {
		this.perms = new ArrayList<>();
		for (int i = 1; i <= numOfWr; i++) {
			permute(maxWrLen, i, phrLen, new ArrayList<>(), 0);
		}
	}

	private void permute(int maxWrLen, int numOfWr, int phrLen, List<Integer> perm, int sum) {
		if (numOfWr == 0) {
			if (sum == phrLen) {
				perms.add(perm);
			}
			sum = 0;
			return;
		}
		for (int i = 1; i <= maxWrLen; i++) {
			List<Integer> tmpPerm = new ArrayList<>();
			tmpPerm.addAll(perm);
			tmpPerm.add(i);
			int tmpSum = sum + i;
			permute(maxWrLen, numOfWr - 1, phrLen, tmpPerm, tmpSum);
		}
	}

	private void findAnagram(Dictionary dic, Trie t) {
		for (List<Integer> list : perms) {
			int length = list.get(0);
			List<String> wL = new ArrayList<>(dic.getSortedLenghtMap().get(length));
			for (String s : wL) {
				Anagram a = new Anagram(list);
				a.addWord(s);
				findAnagramRecusion(t, a, dic);
			}
		}
	}

	private void findAnagramRecusion(Trie t, Anagram a, Dictionary dic) {
		if (a.isAnagram()) {
			makeAnagram(a, dic);
			return;
		}else if(a.isEndButNotAnagram()) {
			return;
		}
		List<String> remLis = t.findNextAnagramWord(a.getRemainingLetters(), a.getNextLenght());
		if (remLis.size() != 0) {
			for (String s : remLis) {
				Anagram aNew = a.copyAnagram();
				aNew.addWord(s);
				findAnagramRecusion(t, aNew, dic);
			}
		} else {
			return;
		}
	}

	private void makeAnagram(Anagram anagram, Dictionary dic) {
		List<List<String>> anList = new ArrayList<>();
		List<String> anagrams = new ArrayList<>();
		for (String s : anagram.getListAnagram()) {
			anList.add(dic.getSortedWordsMap().get(s));
		}
		makeAnagramRecursion(anList, anagrams, "", 0);
		for (String string : anagrams) {
			if (md5Check(string, md5)) {
				System.out.println("Anagram: " + string);
				throw new RuntimeException();
			}
		}
	}

	private void makeAnagramRecursion(List<List<String>> anList, List<String> ans, String an,
			int start) {
		if (anList.size() == start) {
			ans.add(an);
			return;
		}
		for (int i = start; i < start + 1; i++) {
			for (int j = 0; j < anList.get(i).size(); j++) {
				String tmp = an;
				if (tmp.length() != 0) {
					tmp = tmp + " " + anList.get(i).get(j);
				} else {
					tmp = anList.get(i).get(j);
				}
				makeAnagramRecursion(anList, ans, tmp, start + 1);
			}
		}
	}

	private boolean md5Check(String word, String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(word.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			if (sb.toString().equals(md5)) {
				return true;
			}
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return false;
	}

}