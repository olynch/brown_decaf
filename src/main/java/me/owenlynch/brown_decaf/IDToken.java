package me.owenlynch.brown_decaf;

class IDToken extends Token {
	String Name;
	public IDToken(String name, int line, int col) {
		super(ID, name, line, col);
		Name = name;
	}
	public String toString() {
		return "ID(" + Name + ", " + Line + ":" + Column + ")";
	}
}
