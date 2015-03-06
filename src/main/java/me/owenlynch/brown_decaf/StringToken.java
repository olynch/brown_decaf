package me.owenlynch.brown_decaf;

class StringToken extends Token {
	String Value;
	public StringToken(String value, int line, int col) {
		super(StringConst, value, line, col);
		Value = value.substring(1, value.length() - 1);
	}
	public String toString() {
		return "String(" + Value + ")";
	}
}
