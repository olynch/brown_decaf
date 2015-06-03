package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

class SLRDFA {
	private final SLRAction[] dfa;
	private final SymbolUniverse universe;
	private final int startStateInt;

	public static ArrayList<SLRState> makeAugmentedProductions(ArrayList<Production> productions) {
		ArrayList<SLRState> augmentedProductions = new ArrayList<>();
		for (Production p : productions) {
			augmentedProductions.addAll(p.getStates());
		}
		return augmentedProductions;
	}

	public static HashSet<SLRState> closure(SLRState s, ArrayList<SLRState> universe) {
		HashSet<SLRState> I = new HashSet<>();
		I.add(s);
		return closure(I, universe);
	}

	public static HashSet<SLRState> closure(HashSet<SLRState> I, ArrayList<SLRState> universe) {
		/*
		 * Define closure(I) by the following rules:
		 * 1. closure(I) ⊇ I
		 * 2. If A → α⋅Bβ ∈ closure(I), and ∃(B → ⋅α), then B → ⋅α ∈ closure(I)
		 */
		HashSet<SLRState> newStates = new HashSet<>();
		HashSet<SLRState> nextNewStates = new HashSet<>();
		HashSet<SLRState> closureOfI = new HashSet<>();
		newStates.addAll(I);
		while (!newStates.isEmpty()) {
			for (SLRState s : newStates) {
				for (SLRState t : universe) {
					if (s.epsilonLink(t)) {
						nextNewStates.add(t);
					}
				}
			}
			closureOfI.addAll(newStates);
			newStates.clear();
			newStates.addAll(nextNewStates);
			nextNewStates.clear();
		}
		return closureOfI;
	}

	public static HashSet<SLRState> slrGoto(HashSet<SLRState> I, int X, ArrayList<SLRState> universe) {
		/*
		 * Define goto(I, X) where I is a set of augmented productions and X is a symbol
		 * as the closure of all items A → αX⋅β such that A → α⋅Xβ ∈ I
		 */
		HashSet<SLRState> gotoOfI = new HashSet<>();
		for (SLRState s : I) {
			for (SLRState t : universe) {
				if (s.linkOnX(t, X)) {
					gotoOfI.add(t);
				}
			}
		}
		return closure(gotoOfI, universe);
	}

	public static ArrayList<HashSet<int[]>> getProductionsMap(ArrayList<Production> productions, SymbolUniverse universe) {
		ArrayList<HashSet<int[]>> productionsMap = new ArrayList<>(universe.size());
		for (int i = 0; i < universe.size(); i++) {
			productionsMap.add(new HashSet<>());
		}
		for (Production p : productions) {
			productionsMap.get(p.lhs).add(p.rhs);
		}
		return productionsMap;
	}

	public static boolean nullable(int lhs, HashSet<int[]> rhsides, BitSetIterable alreadyNullable) {
		if (rhsides.size() == 0) return false;
		for (int[] rhs : rhsides) {
			// Checks every production to make sure that all the symbols on the rhs are nullable according
			// to what we've already found to be nullable
			for (int s : rhs) {
				if (! alreadyNullable.get(s)) {
					return false;
				}
			}
		}
		return true;
	}

	public static BitSetIterable getNullableSet(SymbolUniverse universe, ArrayList<HashSet<int[]>> productions) {
		BitSetIterable nullableSet = new BitSetIterable();
		BitSetIterable newSymbols = new BitSetIterable();
		BitSetIterable nonNullable = new BitSetIterable();
		for (Integer s : universe.symbols()) {
			nonNullable.set(s);
		}
		for (Integer s : nonNullable) {
			/*
			 * If s is currently in nonNullable, and it has a production going to an empty lhs
			 */
			if (productions.get(s) != null) {
				for (int[] lhs : productions.get(s)) {
					if (lhs.length == 0) {
						newSymbols.set(s);
						nonNullable.set(s, false);
						break;
					}
				}
			}
		}
		while (!newSymbols.isEmpty()) {
			for (Integer s : newSymbols) {
				nullableSet.set(s);
				newSymbols.set(s, false);
			}
			for (Integer s : nonNullable) {
				if (nullable(s, productions.get(s), nullableSet)) {
					newSymbols.set(s);
					nonNullable.set(s, false);
				}
			}
		}
		return nullableSet;
	}


	public static ArrayList<BitSetIterable> getFirstSets(BitSetIterable nullableSet, ArrayList<HashSet<int[]>> productions, SymbolUniverse universe) {
		/*
		 * Returns an ArrayList of BitSetIterables, where firstSet[n][m] is true if and only if m is terminal and can appear
		 * as the first element of a derivation from n
		 */
		ArrayList<BitSetIterable> firstSets = new ArrayList<>(universe.size());
		for (int i = 0; i < universe.size(); i++) {
			firstSets.add(new BitSetIterable());
		}
		for (Integer i : universe.terminals()) {
			firstSets.get(i).set(i);
		}
		boolean addednew = true;
		BitSetIterable cur;
		BitSetIterable firstOfRHS;
		while (addednew) {
			// Fixed point iteration
			addednew = false;
			for (Integer s : universe.nonTerminals()) {
				cur = firstSets.get(s);
				for (int[] rhs : productions.get(s)) {
					if (rhs.length == 0) continue;
					for (int i = 0; i < rhs.length; i++) {
						firstOfRHS = firstSets.get(rhs[i]);
						BitSetIterable comp = (BitSetIterable) firstOfRHS.clone();
						comp.and(cur);
						if (! comp.equals(firstOfRHS)) { // if there is stuff in firstOfRHS that isn't in cur
							cur.or(firstOfRHS); // cur = cur U firstOfRHS
							addednew = true;
							// keep iterating
						}
						if (! nullableSet.get(rhs[i])) {
							break;
						}
							// keep going until we hit something that isn't nullable
					}
				}
			}
		}
		return firstSets;
	}

	public static ArrayList<BitSetIterable> getFollowSets(ArrayList<BitSetIterable> firstSets,
			BitSetIterable nullableSet, ArrayList<Production> productions, SymbolUniverse universe) {
		ArrayList<BitSetIterable> followSets = new ArrayList<>(universe.size());
		for (int i = 0; i < universe.size(); i++) {
			followSets.add(new BitSetIterable());
		}
		for (Production p : productions) {
			for (int i = p.rhs.length - 1; i >= 1; i--) {
				BitSetIterable curFirst = firstSets.get(p.rhs[i]);
				for (int j = i - 1; j >= 0; j--) {
					followSets.get(p.rhs[j]).or(curFirst);
					if (! nullableSet.get(p.rhs[j])) {
						// only continue on if the symbol was nullable
						break;
					}
				}
			}
		}
		boolean addednew = true;
		BitSetIterable lhsFollow;
		while (addednew) {
			addednew = false;
			for (Production p : productions) {
				lhsFollow = followSets.get(p.lhs);
				if (lhsFollow.isEmpty()) {
					continue; // Nothing to do here! Carry on!
				}
				for (int i = p.rhs.length - 1; i >= 0; i--){
					BitSetIterable comp = (BitSetIterable) lhsFollow.clone();
					BitSetIterable rhsFollow = followSets.get(p.rhs[i]);
					comp.and(rhsFollow);
					if (! comp.equals(lhsFollow)) { // if there is stuff in lhsFollow not in rhsFollow
						rhsFollow.or(lhsFollow);
						addednew = true;
					}
					if (! nullableSet.get(p.rhs[i])) {
						// only continue on if the symbol was nullable
						break;
					}
				}
			}
		}
		return followSets;
	}

	public static ArrayList<HashSet<SLRState>> getCanonical(ArrayList<SLRState> augmentedProductions, SymbolUniverse universe) {
		HashSet<HashSet<SLRState>> canonicalSet = new HashSet<>();
		HashSet<HashSet<SLRState>> curNewSets = new HashSet<>();
		HashSet<HashSet<SLRState>> nextNewSets = new HashSet<>();
		HashSet<HashSet<SLRState>> temp;
		SLRState startState = augmentedProductions.get(0);
		HashSet<SLRState> startStateSet = closure(startState, augmentedProductions);
		nextNewSets.add(startStateSet);
		while(!nextNewSets.isEmpty()) {
			temp = curNewSets;
			curNewSets = nextNewSets;
			nextNewSets = temp;
			nextNewSets.clear();
			for (HashSet<SLRState> I : curNewSets) {
				for (Integer i : universe.symbols()) {
					HashSet<SLRState> k = slrGoto(I, i, augmentedProductions);
					if (! (k.isEmpty() || curNewSets.contains(k) || canonicalSet.contains(k))) {
						nextNewSets.add(k);
					}
				}
				canonicalSet.add(I);
			}
		}
		ArrayList<HashSet<SLRState>> canonical = new ArrayList<>();
		for (HashSet<SLRState> s : canonicalSet) {
			// make the sets ordered so that we can use ints as states instead of sets
			canonical.add(s);
		}
		return canonical;
	}

	public static HashMap<HashSet<SLRState>, Integer> getReverseCanonical(ArrayList<HashSet<SLRState>> canonical) {
		HashMap<HashSet<SLRState>, Integer> reverseCanonical = new HashMap<>();
		for (int i = 0; i < canonical.size(); i++) {
			reverseCanonical.put(canonical.get(i), i);
		}
		return reverseCanonical;
	}

	public SLRDFA(ArrayList<Production> productions, SymbolUniverse universe) {
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
		this.universe = universe;
		ArrayList<SLRState> augmentedProductions = makeAugmentedProductions(productions);
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
		SLRState startState = augmentedProductions.get(0);
		ArrayList<HashSet<SLRState>> canonical = getCanonical(augmentedProductions, universe);
		HashMap<HashSet<SLRState>, Integer> reverseCanonical = getReverseCanonical(canonical);
		startStateInt = reverseCanonical.get(closure(startState, augmentedProductions));
		/*
		 * Intermission #2: FOLLOW Sets
		 *
		 * Define FOLLOW(B) as the set of all a ∈ Terminals such that there exists a derivation
		 * with a left hand side of αBaβ.
		 */
		ArrayList<HashSet<int[]>> productionsMap = getProductionsMap(productions, universe);
		BitSetIterable nullableSet = getNullableSet(universe, productionsMap);
		ArrayList<BitSetIterable> firstSets = getFirstSets(nullableSet, productionsMap, universe);
		ArrayList<BitSetIterable> followSets = getFollowSets(firstSets, nullableSet, productions, universe);

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
		HashSet<SLRState> k;
		HashSet<SLRState> j;
		int jState;
		dfa = new SLRAction[canonical.size() * universe.size()];
		for (int state = 0; state < canonical.size(); state++) {
			k = canonical.get(state);
			for (int a : universe.terminals()) {
				j = slrGoto(k, a, augmentedProductions);
				if (!j.isEmpty()) {
					// then we shift to j on symbol a
					jState = reverseCanonical.get(j);
					set(state, a, new SLRActionShift(jState));
				} else {
					for (SLRState s : k) {
						if (s.p.rhs.length == s.place && followSets.get(s.p.lhs).get(a)) {
						// if s is of the form B → α⋅
							if (startState.equals(s)) {
								set(state, a, new SLRActionAccept());
								break;
							} else {
								set(state, a, new SLRActionReduce(s.p)); // s.p is the production by which to reduce
								break;
							}
						}
					}
					if (get(state, a) == null) {
						// the "else" to the if inside the for loop
						set(state, a, new SLRActionError());
					}
				}
			}
			for (int a : universe.nonTerminals()) {
				j = slrGoto(k, a, augmentedProductions);
				if (!j.isEmpty()) {
					// then j is what we go to after reducing by a
					jState = reverseCanonical.get(j);
					set(state, a, new SLRActionGo(jState));
				}
				else {
					set(state, a, new SLRActionError());
				}
			}
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < universe.size(); i++) {
			s.append("" + i + ", ");
		}
		return s.toString();
	}

	public SLRAction get(int state, int symbol) {
		return dfa[universe.size() * state + symbol];
	}
	
	private void set(int state, int symbol, SLRAction action) {
		dfa[universe.size() * state + symbol] = action;
	}

	public int startState() {
		return startStateInt;
	}
}
