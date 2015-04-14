package me.owenlynch.brown_decaf;

class BooleanToken extends Token {
	boolean value;

	public BooleanToken(String value, int line, int col) {
		super(TName.BOOLEANLIT, line, col);
		this.value = (value.equals("true"));
	}

	public void setVal(Token other) {
		value = other.value;
	}

	public String toString() {
		return "Boolean(" + value + ")";
	}
}
