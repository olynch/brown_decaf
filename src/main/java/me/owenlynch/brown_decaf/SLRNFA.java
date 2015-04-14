package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.HashSet;

class SLRNFA {
	// The underlying representation of the non-deterministic finite automaton
	private HashMap<SLRState, HashMap<SType, HashSet<SLRState>> nfa;

	public SLRFA() {
		fa = new HashMap<>();
	}

	public void put(SLRState k1, SType k2, SLRState v) {
		fa.get(k1).get(k2).add(v);
	}

	public HashSet<SLRState> get(SLRState k1, SType k2) {
		fa.get(k1).get(k2);
	}
}
