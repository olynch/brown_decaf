package me.owenlynch.brown_decaf;

class StringToken extends Token {
	String value;
	public StringToken(String value, int line, int col) {
		super(TName.STRINGLIT, line, col);
		value = value.substring(1, value.length() - 1);
	}
	public String toString() {
		return "String(" + value + ")";
	}
}
