package me.owenlynch.brown_decaf;

class NonTerminal extends Symbol {
	private NTName type;
	private Symbol[] children;

	public NonTerminal(NTName name) {
		this.name = name;
		this.children = null;
	}

	public void setChildren(Symbol[] children) {
		this.children = children;
	}

	public NonTerminal clone() {
		return new NonTerminal(name);
	}

	public boolean isTerminal() {
		return false;
	}
}
