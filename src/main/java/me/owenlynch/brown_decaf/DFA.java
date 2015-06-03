package me.owenlynch.brown_decaf;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import static me.owenlynch.brown_decaf.DataDSL.*;

public class DFA {
	private int[] dfa;
	private int eof;
	private HashSet<Integer> acceptingStates;
	private int[] finalStateTokens;

	public static final DFA JavaDFA = new DFA(
			17,
			TokenType.EOF,
			new int[]{
				-1,
				TokenType.IDENTIFIER, //1
				-1, -1,
				TokenType.STRINGLIT, //4
				TokenType.INTLIT, //5
				-1,
				TokenType.DOUBLELIT, //7
				-1, -1, -1,
				TokenType.CHARLIT, //11
				TokenType.PUNCTUATION, //12
				-1,
				TokenType.OPERATOR, //14
				TokenType.OPERATOR, //15
				-1,
			},
			makelist(list -> {
				list.add(makemap(map1 -> {
					map1.put("a-zA-Z", 1);
					map1.put("\"", 2);
					map1.put("0-9", 5);
					map1.put("'", 8);
					map1.put(";,.{}[]()", 12);
					map1.put("&", 13);
					map1.put("+*-/", 14);
					map1.put("!=<>", 15);
					map1.put("|", 16);
					// Start
				}));
				list.add(makemap(map1 -> {
					map1.put("a-zA-Z0-9", 1);
					// 1 (ID)
				}));
				list.add(makemap(map1 -> {
					map1.put("\\", 3);
					map1.put("^'\\", 2);
					map1.put("\"", 4);
					// 2 (String)
				}));
				list.add(makemap(map1 -> {
					map1.put(" -~", 2);
					// 3 (String)
				}));
				list.add(makemap(map1 -> {
					// 4 (String F)
				}));
				list.add(makemap(map1 -> {
					map1.put("0-9", 5);
					map1.put(".", 6);
					// 5 (Int)
				}));
				list.add(makemap(map1 -> {
					map1.put("0-9", 7);
					// 6 (Decimal Point)
				}));
				list.add(makemap(map1 -> {
					map1.put("0-9", 7);
					// 7 (Float)
				}));
				list.add(makemap(map1 -> {
					map1.put("\\", 9);
					map1.put("^'\\", 10);
					// 8 (Char),
				}));
				list.add(makemap(map1 -> {
					map1.put("!-~", 10);
					// 9 (Char \\),
				}));
				list.add(makemap(map1 -> {
					map1.put("'", 11);
					// 10 (Char after \\),
				}));
				list.add(makemap(map1 -> {
					// 11 (Char F),
				}));
				list.add(makemap(map1 -> {
					// 12 (Punctuation),
				}));
				list.add(makemap(map1 -> {
					map1.put("&", 14);
					// 13 (Operator &),
				}));
				list.add(makemap(map1 -> {
					// 14 (Operator F),
				}));
				list.add(makemap(map1 -> {
					map1.put("=", 14);
					// 15 (Operator =),
				}));
				list.add(makemap(map1 -> {
					map1.put("|", 14);
					// 16 (Operator |)
				}));
			})
	);


	private int get(int state, char transition) {
		return dfa[state * 128 + (int) transition];
	}

	private void set(int state, char transition, int val) {
		dfa[state * 128 + (int) transition] = val;
	}

	public DFA(int numStates, int eof, int[] finalStateTokens, ArrayList<HashMap<String, Integer>> dfaArray) {
		this.eof = eof;
		dfa = new int[numStates * 128];
		for (int i = 0; i < dfa.length; i++) {
			dfa[i] = -1;
		}
		this.finalStateTokens = finalStateTokens;
		acceptingStates = new HashSet<Integer>();
		for (int i = 0; i < numStates; i++) {
			if (finalStateTokens[i] != -1) {
				acceptingStates.add(i);
			}
		}
		for (int i = 0; i < dfaArray.size(); i++) {
			HashMap<String, Integer> curobj = dfaArray.get(i);
			for (Map.Entry<String, Integer> entry : curobj.entrySet()) {
				String key = entry.getKey();
				Integer val = (Integer) entry.getValue();
				if (key.length() == 1) {
					set(i, key.charAt(0), val);
				}
				else if (key.charAt(0) == '^'){
					// everything but the stuff in the key
					for (char c = 32; c < 128; c++) {
						boolean inKey = false;
						for (char k : key.toCharArray()) {
							if (i == k) {
								inKey = true;
								break;
							}
						}
						if (!inKey) {
							set(i, c, val);
						}
					}
				}
				else if (key.charAt(1) == '-') {
					// if we have a string of the form a-z or A-Z or 0-9 etc.
					for (int j = 0; j < key.length(); j += 3) {
						for (char k = key.charAt(j); k <= key.charAt(j + 2); k++) {
							set(i, k, val);
						}
					}
				}
				else {
					//just add every character in the string to the dfa
					for (char c : key.toCharArray()) {
						set(i, c, val);
					}
				}
			}
		}
	}

	public int transition(int node, char transition) {
		return get(node, transition);
	}

	public boolean isAccepting(int node) {
		return acceptingStates.contains(node);
	}

	public int token(int node) {
		return finalStateTokens[node];
	}

	public int getEOFType() {
		return eof;
	}

	public void printDFA() {
		for (int i = 0; i < dfa.length; i++) {
			if (i % 128 == 0) {
				System.out.println("State: " + (i / 128));
			}
			System.out.print(dfa[i] + ", ");
			if (i % 128 == 127) {
				System.out.println();
			}
		}
	}
}
