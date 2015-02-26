package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import org.json.*;
import java.util.Iterator;

public class DFA {
	private ArrayList<HashMap<Character, Integer>> dfa;
	private HashSet<Integer> acceptingStates;
	private HashMap<Integer, String> finalStateTokens;

	public DFA(String json_desc) {
		JSONObject desc = new JSONObject(json_desc);
		JSONArray dfa_arr = (JSONArray) desc.get("dfa_arr");
		JSONArray accepting = (JSONArray) desc.get("accepting");
		for (int i = 0; i < accepting.length(); i++) {
			JSONObject state = (JSONObject) accepting.get(i);
			acceptingStates.add((Integer) state.get("state"));
			finalStateTokens.put((Integer) state.get("state"), (String) state.get("token"));
		}
		dfa = new ArrayList<HashMap<Character, Integer>>(dfa_arr.length());
		for (int i = 0; i < dfa_arr.length(); i++) {
			HashMap<Character, Integer> cur = new HashMap<Character, Integer>();
			JSONObject curobj = (JSONObject) dfa_arr.get(i);
			Iterator<String> keys = curobj.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.length() == 1) {
					// if we have a string of the form 'a'
					cur.put(key.charAt(0), (Integer) curobj.get(key));
				}
				else {
					// if we have a string of the form a-z or A-Z or 0-9 etc.
					for (int j = 0; i < key.length(); i += 3) {
						for (char k = key.charAt(j); k < key.charAt(j + 2); k++) {
							cur.put(k, (Integer) curobj.get(key));
						}
					}
				}
			}
		}
	}

	public void setNode(int node, HashMap<Character, Integer> transitions) {
		dfa.set(node, transitions);
	}

	public int transition(int node, char transition) throws ScanException {
		Integer nextNode = dfa.get(node).get(transition);
		if (nextNode == null) {
			throw new ScanException("No transition on symbol " + transition);
		}
		else {
			return nextNode;
		}
	}

	public boolean isAccepting(int node) {
		return acceptingStates.contains(node);
	}

	public String token(int node) {
		return finalStateTokens.get(node);
	}
}
