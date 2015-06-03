package me.owenlynch.brown_decaf;

class NonTerminal implements Symbol {
	public int type;
	public Symbol[] children;

	public NonTerminal(int type) {
		this.type = type;
		this.children = null;
	}

	public void setChildren(Symbol[] children) {
		this.children = children;
	}

	public NonTerminal clone() {
		return new NonTerminal(type);
	}

	public boolean isTerminal() {
		return false;
	}

	public int type() {
		return type;
	}
}
