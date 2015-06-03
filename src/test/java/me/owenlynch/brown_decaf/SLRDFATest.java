package me.owenlynch.brown_decaf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static me.owenlynch.brown_decaf.DataDSL.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class SLRDFATest extends TestCase {
	//
	// Tests using a simplified version of JSON
	// S' -> S $
	// S -> Obj
	// Obj -> IDENT | INT | { KeyListStart }
	// KeyListStart -> Key KeyList | (nil)
	// KeyList -> , Key KeyList | (nil)
	// Key -> IDENT : Obj
	private static int 
		IDENT = 0,
		OPENBRACE = 1,
		CLOSEBRACE = 2,
		INT = 3,
		COLON = 4,
		EOF = 5,
		COMMA = 6,
		SPRIME = 7,
		START = 8,
		OBJ = 9,
		KEYLIST = 10,
		KEYLISTSTART = 11,
		KEY = 12;

	private static SymbolUniverse symbols = new SymbolUniverse(
			makebitset(set -> {
				set.set(IDENT);
				set.set(OPENBRACE);
				set.set(CLOSEBRACE);
				set.set(INT);
				set.set(COLON);
				set.set(EOF);
				set.set(COMMA);
			}),
			makebitset(set -> {
				set.set(SPRIME);
				set.set(START);
				set.set(OBJ);
				set.set(KEYLISTSTART);
				set.set(KEYLIST);
				set.set(KEY);
			}));

	public static ArrayList<Production> toyProductions = makelist(list -> {
		list.add(new Production(SPRIME, START, EOF));
		list.add(new Production(START, OBJ));
		list.add(new Production(OBJ, IDENT));
		list.add(new Production(OBJ, INT));
		list.add(new Production(OBJ, OPENBRACE, KEYLISTSTART, CLOSEBRACE));
		list.add(new Production(KEYLISTSTART, KEY, KEYLIST));
		list.add(new Production(KEYLISTSTART));
		list.add(new Production(KEYLIST, COMMA, KEY, KEYLIST));
		list.add(new Production(KEYLIST));
		list.add(new Production(KEY, IDENT, COLON, OBJ));
	});

	public SLRDFATest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(SLRDFATest.class);
	}


	public void testAugmentedProductions() {
		// First test an individual production
		Production test = toyProductions.get(0);
		ArrayList<SLRState> comp = makelist(list -> {
			list.add(new SLRState(test, 0));
			list.add(new SLRState(test, 1));
			list.add(new SLRState(test, 2));
		});
		ArrayList<SLRState> augmentedProductions1 = test.getStates();

		for (int i = 0; i < augmentedProductions1.size(); i++) {
			assertTrue(comp.get(i).equals(augmentedProductions1.get(i)));
		}

		ArrayList<SLRState> augmentedProductions2 = SLRDFA.makeAugmentedProductions(toyProductions);
		assertTrue(augmentedProductions2.size() == 3 + 2 + 2 + 2 + 4 + 3 + 1 + 4 + 1 + 4);
		// Too lazy to manually generate all these augmented productions
	}

	public void testClosure() {
		HashSet<SLRState> comp = new HashSet<>();
		ArrayList<SLRState> augmentedProductions = SLRDFA.makeAugmentedProductions(toyProductions);
		// Tests using a simplified version of JSON
		// This is the set of states equivalent to the start state
		// S' -> * S $
		// S -> * Obj
		// Obj -> * IDENT 
		// Obj -> * INT 
		// Obj -> * { KeyList }
		comp.add(new SLRState(toyProductions.get(0), 0));
		comp.add(new SLRState(toyProductions.get(1), 0));
		comp.add(new SLRState(toyProductions.get(2), 0));
		comp.add(new SLRState(toyProductions.get(3), 0));
		comp.add(new SLRState(toyProductions.get(4), 0));
		HashSet<SLRState> SPRIMEClosure = SLRDFA.closure(new SLRState(toyProductions.get(0), 0), augmentedProductions);
		assertTrue(comp.equals(SPRIMEClosure));
	}

	public void testSlrGoto() {
		ArrayList<SLRState> augmentedProductions = SLRDFA.makeAugmentedProductions(toyProductions);
		HashSet<SLRState> SPRIMEClosure = SLRDFA.closure(new SLRState(toyProductions.get(0), 0), augmentedProductions);
		HashSet<SLRState> comp = makeset(set -> {
			set.add(new SLRState(toyProductions.get(2), 1)); // Obj -> IDENT *
		});
		HashSet<SLRState> SPRIMEAfterIDENT = SLRDFA.slrGoto(SPRIMEClosure, IDENT, augmentedProductions);
		assertTrue(comp.equals(SPRIMEAfterIDENT));
	}

	public void testGetNullableSet() {
		BitSetIterable comp = makebitset(set -> {
			set.set(KEYLIST);
			set.set(KEYLISTSTART);
		});
		ArrayList<HashSet<int[]>> productionsMap = SLRDFA.getProductionsMap(toyProductions, symbols);
		BitSetIterable nullableSet = SLRDFA.getNullableSet(symbols, productionsMap);
		assertTrue(nullableSet.equals(comp));
	}

	public void testGetFirstSets() {
		// Tests using a simplified version of JSON
		// S' -> S $
		// S -> Obj
		// Obj -> IDENT | INT | { KeyListStart }
		// KeyListStart -> Key KeyList | (nil)
		// KeyList -> , Key KeyList | (nil)
		// Key -> IDENT : Obj
		// FIRST(S) = { IDENT, INT, OPENBRACE }
		BitSetIterable comp = makebitset(set -> {
			set.set(IDENT);
			set.set(INT);
			set.set(OPENBRACE);
		});
		ArrayList<HashSet<int[]>> productionsMap = SLRDFA.getProductionsMap(toyProductions, symbols);
		BitSetIterable nullableSet = SLRDFA.getNullableSet(symbols, productionsMap);
		ArrayList<BitSetIterable> firstSets = SLRDFA.getFirstSets(nullableSet, productionsMap, symbols);
		assertTrue(comp.equals(firstSets.get(START)));
	}

	public void testGetFollowSets() {
		// FOLLOW(Obj) = { EOF, COMMA, CLOSEBRACE }
		BitSetIterable comp = makebitset(set -> {
			set.set(EOF);
			set.set(COMMA);
			set.set(CLOSEBRACE);
		});
		ArrayList<HashSet<int[]>> productionsMap = SLRDFA.getProductionsMap(toyProductions, symbols);
		BitSetIterable nullableSet = SLRDFA.getNullableSet(symbols, productionsMap);
		ArrayList<BitSetIterable> firstSets = SLRDFA.getFirstSets(nullableSet, productionsMap, symbols);
		ArrayList<BitSetIterable> followSets = SLRDFA.getFollowSets(firstSets, nullableSet, toyProductions, symbols);
		assertTrue(comp.equals(followSets.get(OBJ)));
	}
	
	public void testGetCanonical() {
		ArrayList<SLRState> augmentedProductions = SLRDFA.makeAugmentedProductions(toyProductions);
		ArrayList<HashSet<SLRState>> canonical = SLRDFA.getCanonical(augmentedProductions, symbols);
		//HashMap<HashSet<SLRState>, Integer> reverseCanonical = SLRDFA.getReverseCanonical(canonical);
		//HashSet<HashSet<SLRState>> canonicalSet = new HashSet<>(canonical);
		//HashSet<HashSet<SLRState>> comp = new HashSet<>();
		for (HashSet<SLRState> state : canonical) {
			System.out.println(state.toString());
		}
		//assertTrue(false); // for debugging
	}

	public void testSLRDFA() {
		// S' -> S $
		// S -> Obj
		// Obj -> IDENT | INT | { KeyListStart }
		// KeyListStart -> Key KeyList | (nil)
		// KeyList -> , Key KeyList | (nil)
		// Key -> IDENT : Obj
		ArrayList<SLRState> augmentedProductions = SLRDFA.makeAugmentedProductions(toyProductions);
		ArrayList<HashSet<SLRState>> canonical = SLRDFA.getCanonical(augmentedProductions, symbols);
		HashMap<HashSet<SLRState>, Integer> reverseCanonical = SLRDFA.getReverseCanonical(canonical);
		SLRDFA toyDFA = new SLRDFA(toyProductions, symbols);
		HashSet<SLRState> curStateSet = SLRDFA.closure(new SLRState(toyProductions.get(0), 0), augmentedProductions);
		int curState = reverseCanonical.get(curStateSet);
		HashSet<SLRState> nextStateSet = SLRDFA.slrGoto(curStateSet, OPENBRACE, augmentedProductions);
		int nextState = reverseCanonical.get(nextStateSet);
		SLRAction action = toyDFA.get(curState, OPENBRACE);
		assertTrue(action.getType() == SLRAction.SHIFT);
		System.out.printf("curState: %s\nexpectedState = %s\ngotState = %s\n",
				curStateSet.toString(),
				nextStateSet.toString(),
				canonical.get(((SLRActionShift) action).state).toString());
		assertTrue(((SLRActionShift) action).state == nextState);
	}
}
