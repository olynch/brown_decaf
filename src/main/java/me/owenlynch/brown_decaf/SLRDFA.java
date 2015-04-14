package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.HashSet;

class SLRDFA {
	private HashMap<Integer, HashMap<SType, SLRAction>> dfa;

	private static HashSet<SLRState> closure(HashSet<SLRState> I, ArrayList<SLRState> universe) {
		/*
		 * Define closure(I) by the following rules:
		 * 1. closure(I) ⊇ I
		 * 2. If A → α⋅Bβ ∈ closure(I), and ∃(B → ⋅α), then B → ⋅α ∈ closure(I)
		 */
		HashSet<SLRState> newStates = new HashSet<>();
		HashSet<SLRState> closureOfI = new HashSet<>();
		newStates.addAll(I);
		while (!newStates.isEmpty()) {
			for (SLRState s : newStates) {
				for (SLState t : universe) {
					if (s.epsilonPath(t)) {
						newStates.add(t);
					}
				}
				closureOfI.add(s);
				newStates.remove(s);
			}
		}
		return closureOfI;
	}

	private static HashSet<SLRState> slrGoto(HashSet<SLRState> I, SType X, ArrayList<SLRState> universe) {
		/*
		 * Define goto(I, X) where I is a set of augmented productions and X is a symbol
		 * as the closure of all items A → αX⋅β such that A → α⋅Xβ ∈ I
		 */
		HashSet<SLRState> gotoOfI = new HashSet<>();
		for (SLRState s : I) {
			for (SLRState t : universe) {
				if (s.linkOnX(t, X)) {
					gotoOfI.add(X)
				}
			}
		}
		return closure(gotoOfI);
	}

	private static HashSet<SType> follow(SType t) {
		
	}

	public SLRDFA(ArrayList<Production> productions) {
		/* 
		 * Algorithm for constructing the LR(0) parse table:
		 *
		 * Step 1: Create the augmented production list.
		 *
		 * We assume that the input list has a start production at index 0
		 * Now, for every production A → αβ we create several augmented productions
		 * by placing a dot between each symbol to create:
		 * A → ⋅αβ
		 * A → α⋅β
		 * A → αβ⋅
		 */
		ArrayList<SLRState> augmentedProductions = new ArrayList<>();
		for (Production p : production) {
			augmentedProductions.addAll(p.getStates());
		}
		// Note that what we want to be our first production, ie. S' → ⋅S, is still at index 0

		/*
		 * Intermission: closure and goto
		 *
		 * Define closure(I) by the following rules:
		 * 1. closure(I) ⊇ I
		 * 2. If A → α⋅Bβ ∈ closure(I), and ∃(B → ⋅α), then B → ⋅α ∈ closure(I)
		 *
		 * Define goto(I, X) where I is a set of augmented productions and X is a symbol
		 * as the closure of all items A → αX⋅β such that A → α⋅Xβ ∈ I
		 */

		 /*
		 * Step 2: Create the canonical collection of sets of augmented productions (AKA LR(0) items)
		 * 
		 * Procedure:
		 * Define C :: a collection of sets of LR(0) items
		 * Initialize C = { closure({S' → S}) }
		 * repeat:
		 * 		For every set I ∈ C, add goto(I, X) to C for every symbol X
		 * 		as long as goto(I, X) is non-empty and not already in C
		 * 	end when no more sets can be added in this manner
		 * 
		 * Assign arbitrary numbers to each set of LR(0) items in the canonical collection, these are the "states"
		 * Finally, if k = closure({S' → S}), then k is the start state of the parser
		 */
		HashSet<HashSet<SLRState>> canonicalSet = new HashSet<>();
		HashSet<HashSet<SLRState>> newSets = new HashSet<>();
		HashSet<SLRState> startState = closure(augmentedProductions.get(0));
		newSets.add(startState);
		SType[] symUniverse = SType.getEnumConstants();
		while(!newSets.isEmpty()) {
			for (HashSet<SLRState> I : newSets) {
				for (SType X : symUniverse) {
					HashSet<SLRState> k = slrGoto(I, X);
					if (! (k.isEmpty || newSets.contains(k) || canonicalSet.contains(k))) {
						newSets.add(k);
					}
				}
				canonicalSet.add(I);
				newSets.remove(I);
			}
		}
		ArrayList<HashSet<SLRState>> canonical = new ArrayList<>();
		HashMap<HashSet<SLRState>, Integer> reverseCanonical = new HashMap<>();
		int startIndice;
		int i = 0;
		for (HashSet<SLRState> s : canonicalSet) {
			// make the sets ordered so that we can use ints as states instead of sets
			if (s == startState) {
				startIndice = i;
			}
			canonical.push(s);
			reverseCanonical.put(s, i);
			i++;
		}

		/*
		 * Intermission #2: FOLLOW Sets
		 *
		 * Define FOLLOW(B) as the set of all a ∈ Terminals such that there exists a derivation
		 * with a left hand side of αBaβ.
		 */

		HashMap<SType, HashSet<SType>> followSets;
		for (SType s : NonTerminal.STypes) {
			followSets.put(follow(s));
		}

		 /*
		 * Step 3: Create the parse table
		 * 
		 * For every pair (k, a) with k ∈ C and a an input symbol, assign the parsing action as follows:
		 * If goto(k, a) = j, then the parsing action is SHIFT to state j
		 * If B → α⋅ ∈ k and a ∈ FOLLOW(B) and B ≠ S', then the parsing action is REDUCE by production B → α
		 * If S' → S ∈ k and a = $, then the parsing action is ACCEPT
		 * Otherwise, the parsing action is ERROR
		 *
		 */
		HashMap<SLRState> k;
		HashMap<SLRState> j;
		int jState;
		dfa = new HashMap<>();
		for (int state = 0; state < canonical.size(); state++) {
			k = canonical.get(state);
			for (SType a : symUniverse) {
				j = slrGoto(k);
				if (!j.isEmpty) {
					// then we shift to j on symbol a
					jState = reverseCanonical.get(j);
					set(state, a, new SLRActionShift(jState));
				} else if ()
			}
		}
	}

	public SLRAction get(int state, SType type) {
		return dfa.get(state).get(type);
	}
	
	private void set(int state, SType type, SLRAction action) {
		dfa.get(state).put(type, action);
	}
}
