package anagram.solver.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Trie {

	private final TrieNode root;

	private class TrieNode {
		Map<Character, TrieNode> children;
		boolean endOfWord;

		public TrieNode() {
			children = new HashMap<>();
			endOfWord = false;
		}
	}

	public Trie() {
		root = new TrieNode();
	}

	public void insert(String word) {
		TrieNode current = root;
		insertRecursively(current, word, 0);
	}

	public void insertRecursively(TrieNode current, String word, int start) {
		if (word.length() == start) {
			current.endOfWord = true;
			return;
		}
		char c = word.charAt(start);
		TrieNode node = current.children.get(c);
		if (node == null) {
			node = new TrieNode();
			current.children.put(c, node);
		}
		insertRecursively(node, word, start + 1);
	}

	public List<String> findNextAnagramWord(String remLett, int permLen) {
		Set<String> list = new HashSet<>();
		TrieNode current = root;
		findAnagramsRecursion(current, remLett, permLen, list, "");
		return new ArrayList<>(list);
	}

	private void findAnagramsRecursion(TrieNode current, String input, int depth, Set<String> list,
			String word) {
		if (depth == 0) {
			if (current.endOfWord)
				list.add(word);
			return;
		}
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			TrieNode node = current.children.get(c);
			if (!(node == null)) {
				String w = word + c;
				StringBuilder tmp = new StringBuilder(input);
				tmp.deleteCharAt(i);
				String temp = tmp.toString();
				findAnagramsRecursion(node, temp, depth - 1, list, w);
			}
		}
	}

}
