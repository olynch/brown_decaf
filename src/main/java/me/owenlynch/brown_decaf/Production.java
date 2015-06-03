package me.owenlynch.brown_decaf;

import java.util.ArrayList;
import java.util.Arrays;

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

	@Override
	public boolean equals(Object other) {
		if (other instanceof Production) {
			Production that = (Production) other;
			for (int i = 0; i < rhs.length; i++) {
				if (that.rhs[i] != rhs[i]) {
					return false;
				}
			}
			return lhs == that.lhs;
		}
		return false;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{ " + lhs + " →");
		for (int k : rhs) {
			s.append(" " + k + " ");
		}
		s.append("}");
		return s.toString();
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[]{lhs, rhs});
	}
}
