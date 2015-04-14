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

	private static boolean nullable(SType lhs, SType[] rhs, HashSet<SType> alreadyNullable) {
			for (SType s : p.rhs) {
				if (! alreadyNullable.contains(s)) {
					return false;
				}
			}
		return true;
	}

	private static HashSet<SType> getNullableSet(HashSet<SType> possible, HashMap<SType, HashSet<SType[]>> productions) {
		HashSet<SType> nullableSet = new HashSet<>();
		HashSet<SType> newSTypes = new HashSet<>();
		HashSet<SType> nonNullable = new HashSet<>();
		for (SType s : productions.keys()) {
			nonNullable.add(s);
		}
		for (SType s : nonNullable) {
			if (productions.get(s).length = 0) {
				newSTypes.add(s);
			}
		}
		while (!newSTypes.isEmpty()) {
			for (SType s : newSTypes) {
				nullableSet.add(s);
				newSTypes.remove(s);
			}
			for (SType s : nonNullable) {
				if (nullable(s, productions.get(s), nullableSet)) {
					newSTypes.add(s);
					nonNullable.remove(s);
				}
			}
		}
	}


	private static HashMap<SType, HashSet<SType>> getFirstSets(HashSet<SType> nullableSet, HashMap<SType, HashSet<SType[]>> productions) {
		HashMap<SType, HashSet<SType>> firstSets = new HashMap<>();
		for (SType s : productions.keys()) {
			firstSets.put(s, new HashSet<SType>());
		}
		boolean addednew = true;
		HashSet<SType> cur;
		HashSet<SType> firstOfRHS;
		while (addednew) {
			// Fixed point iteration
			addednew = false;
			for (SType s : firstSets.keys()) {
				cur = firstSets.get(s);
				for (SType[] rhs : productions.get(s)) {
					if (Token.STypes.contains(rhs[0])) {
						cur.add(rhs[0]);
						addednew = true;
					}
					int i = 0;
					do {
						firstOfRHS = firstSets.get(rhs[i]);
						if (! firstOfRHS.isEmpty()) {
							cur.addAll(firstOfRHS);
							addednew = true;
						}
						i++;
					} while (nullableSet.contains(rhs[i]));
				}
			}
		}
		return firstSets;
	}

	private static HashMap<SType, HashSet<SType>> getFollowSets(HashMap<SType, HashSet<SType>> firstSets,
			HashSet<SType> nullableSet, ArrayList<Production> productions) {
		HashMap<SType, HashSet<SType>> followSets = new HashMap<>();

		for (Production p : productions) {
			followSets.put(s.lhs, new HashSet<>());
		}
		for (Production p : productions) {
			for (int i = 0; i < p.rhs.length - 1; i++) {
				int j = 0;
				do {
					followSets.get(rhs[i]).addAll(firstSets.get(rhs[i+1]));
					j++;
				} while (nullableSet.contains(rhs[j]));
			}
		}
		boolean addednew = true;
		HashSet<SType> lhsFollow;
		while (addednew) {
			addednew = false;
			for (Production p : productions) {
				lhsFollow = followSets.get(p.lhs);
				if (lhsFollow.isEmpty()) {
					continue;
				}
				int i = p.rhs.length - 1;
				do {
					followSets.get(rhs[i]).addAll(lhsFollow);
					i--;
				} while (nullbleSet.contains(rhs[i]));
			}
		}
		return followSets;
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

		HashMap<SType, HashSet<SType>> productionsMap = new HashMap<>();
		for (Production p : productions) {
			if (! productionsMap.keys().contains(p.lhs)) {
				productionsMap.put(p.lhs, new HashSet<SType[]>);
			}
			productionsMap.get(p.lhs).add(p.rhs);
		}
		HashSet<SType> nullableSet = getNullableSet(symUniverse, productionsMap);
		HashMap<SType, HashSet<SType>> firstSets = getFirstSets(nullableSet, productionsMap);
		HashMap<SType, HashSet<SType>> followSets = getFollowSets(firstSets, nullableSet, productions);

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
				j = slrGoto(k, a, productions);
				if (!j.isEmpty) {
					// then we shift to j on symbol a
					jState = reverseCanonical.get(j);
					set(state, a, new SLRActionShift(jState));
				} else {
					for (SLRState s : k) {
						if (s.p.rhs.length == s.place && followSets.get(s.p.lhs).contains(a)) {
							// if s is of the form B → α⋅
							if (s == startState) {
								set(state, a, new SLRActionAccept());
								break;
							} else {
								set(state, a, new SLRActionReduce(s.p));
								break;
							}
						}
					}
					if (get(state, a) == null) {
						set(state, a, new SLRActionError());
					}
				}
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
