package me.owenlynch.brown_decaf;

import java.util.ArrayList;

class Production {
	private SType lhs;
	private SType[] rhs;

	public Production(SType lhs, SType... rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public ArrayList<SLRState> getStates() {
		ArrayList<SLRState> states = new ArrayList<>();
		for (int i = 0; i <= rhs.length; i++) {
			states.push(new SLRState(this, i))
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
