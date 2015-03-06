package me.owenlynch.brown_decaf;

import java.util.HashSet;
import org.json.*;
import java.util.Iterator;

public class DFA {
	private int[] dfa;
	private int numStates;
	private HashSet<Integer> acceptingStates;
	private String[] finalStateTokens;

	private int get(int state, char transition) {
		return dfa[state * 128 + (int) transition];
	}

	private void set(int state, char transition, int val) {
		dfa[state * 128 + (int) transition] = val;
	}

	public DFA(String json_desc) {
		JSONObject desc = new JSONObject(json_desc);
		numStates = (Integer) desc.get("num_states");
		dfa = new int[numStates * 128];
		for (int i = 0; i < dfa.length; ++i) {
			dfa[i] = -1;
		}
		finalStateTokens = new String[numStates];
		acceptingStates = new HashSet<Integer>();
		JSONArray dfa_arr = (JSONArray) desc.get("dfa_arr");
		JSONArray accepting = (JSONArray) desc.get("accepting");
		for (int i = 0; i < accepting.length(); i++) {
			JSONArray state = (JSONArray) accepting.get(i);
			acceptingStates.add((Integer) state.get(0));
			finalStateTokens[(Integer) state.get(0)] = (String) state.get(1);
		}
		for (int i = 0; i < dfa_arr.length(); i++) {
			JSONObject curobj = (JSONObject) dfa_arr.get(i);
			Iterator<String> keys = curobj.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				int val = (Integer) curobj.get(key);
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

	public String token(int node) {
		return finalStateTokens[node];
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
