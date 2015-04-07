package me.owenlynch.brown_decaf;

class Production {
	private Symbol[] rhs;

	public Production(Symbol[] rhs) {
		this.rhs = rhs;
	}

	public Symbol[] getRHS() {
		Symbol newRHS = rhs.clone();
		for (int i = 0; i < rhs.length(); i++) {
			newRHS[i] = newRHS[i].clone();
		}
		return newRHS;
	}

	public void setChildren(NonTerminal parent, Symbol[] children) {
		switch lhs {
			"Start":
			"ClassList":
			"Class":
			"Super":
			"PrimitiveType":
			"Type":
			"Modifier":
			"ModifierList":

		}
	}
}
