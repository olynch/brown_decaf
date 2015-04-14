package me.owenlynch.brown_decaf;

import java.util.ArrayList;

class Production<T> {
	private T lhs;
	private T[] rhs;

	public Production(T rhs, T... rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public ArrayList<SLRState<T>> getStates() {
		ArrayList<SLRState<T>> states = new ArrayList<>();
		for (int i = 0; i <= rhs.length; i++) {
			states.push(new SLRState<T>(this, i))
		}
		return states;
	}

	/*
	 *public Symbol[] getRHS() {
	 *    Symbol newRHS = rhs.clone();
	 *    for (int i = 0; i < rhs.length(); i++) {
	 *        newRHS[i] = newRHS[i].clone();
	 *    }
	 *    return newRHS;
	 *}
	 */
}
