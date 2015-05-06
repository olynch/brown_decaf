package me.owenlynch.brown_decaf;

import java.util.ArrayList;

class Production {
	public final int lhs;
	public final int[] rhs;

	public Production(int lhs, int... rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public ArrayList<SLRState> getStates() {
		ArrayList<SLRState> states = new ArrayList<>();
		for (int i = 0; i <= rhs.length; i++) {
			states.add(new SLRState(this, i));
		}
		return states;
	}
}
