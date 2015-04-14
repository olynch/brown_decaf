class SLRState<T> {
	public final Production<T> p;
	public final int place;

	public SLRState(Production<T> p, int place) {
		this.p = p;
		this.place = place;
	}
	
	public boolean epsilonLink(SLRState<T> other) {
		// This is true if
		// A → α⋅Cβ
		// C → ⋅d
		if (other.place == 0) {
			for (int i = 0; i < p.rhs.length; i++) {
				T s = p.rhs[i];
				if (s == other.p.lhs && place == i) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean linkOnX(SLRState<T> other, SType X) {
		// This is true if
		// A → α⋅Cβ
		// A → αC⋅β
		return p == other.p && place + 1 == other.place && p.rhs[place] == X;
	}
}
