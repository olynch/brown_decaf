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
				SType s = p.rhs[i];
				if (s == other.p.lhs && place == i) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean linkOnX(SLRState other, SType X) {
		// This is true if
		// A → α⋅Cβ
		// A → αC⋅β
		return p == other.p && place + 1 == other.place && p.rhs[place] == X;
	}

	public SType getLink(SLRState other) {
		if (p == other.p && place + 1 == other.place) {
			// A -> a*Cb
			// A -> aC*b
			// link = C
			return p.rhs[place];
		}
		else if (other.place == 0) {
			// A -> a*Cb
			// C -> *d
			// link = ε
			for (int i = 0; i < p.rhs.length; i++) {
				SType s = p.rhs[i];
				if (s == other.p.lhs && place == i) {
					return SType.EPSILON;
				}
			}
		}
		else {
			return null;
		}
	}
}
