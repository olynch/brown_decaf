package me.owenlynch.brown_decaf;

class SLRState {
	public final Production p;
	public final int place;

	public SLRState(Production p, int place) {
		this.p = p;
		this.place = place;
	}
	
	public boolean epsilonLink(SLRState other) {
		// This is true if
		// A → α⋅Cβ
		// C → ⋅d
		if (other.place == 0) {
			for (int i = 0; i < p.rhs.length; i++) {
				int s = p.rhs[i];
				if (s == other.p.lhs && place == i) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean linkOnX(SLRState other, int X) {
		// This is true if
		// A → α⋅Cβ
		// A → αC⋅β
		return p == other.p && place + 1 == other.place && p.rhs[place] == X;
	}
}
