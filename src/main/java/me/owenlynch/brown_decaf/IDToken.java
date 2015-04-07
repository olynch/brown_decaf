package me.owenlynch.brown_decaf;

class IDToken extends Token {
	String name;
	public IDToken(String name, int line, int col) {
		super(TName.IDENTIFIER, line, col);
		this.name = name;
	}
	public String toString() {
		return "ID(" + name + ", " + Line + ":" + Column + ")";
	}
}
