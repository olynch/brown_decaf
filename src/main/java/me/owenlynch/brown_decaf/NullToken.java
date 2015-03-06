package me.owenlynch.brown_decaf;

class NullToken extends Token {
	public NullToken(String str, int line, int col) {
		super(Token.NULL, str, line, col);
	}

	public String toString() {
		return "Null";
	}
}
