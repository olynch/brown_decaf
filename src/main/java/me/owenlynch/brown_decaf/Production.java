package me.owenlynch.brown_decaf;

import java.util.ArrayList;

class Production {
	private int lhs;
	private int[] rhs;

	public Production(int rhs, int... rhs) {
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
}
