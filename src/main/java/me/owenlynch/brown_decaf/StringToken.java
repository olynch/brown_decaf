package me.owenlynch.brown_decaf;

class StringToken extends Token {
	String value;
	public StringToken(String value, int line, int col) {
		super(TName.STRINGLIT, line, col);
		value = value.substring(1, value.length() - 1);
	}

	public void setVal(Token other) {
		value = other.value;
	}

	public String toString() {
		return "String(" + value + ")";
	}
}
