package me.owenlynch.brown_decaf;

class BooleanToken extends Token {
	boolean Value;
	public BooleanToken(String value, int line, int col) {
		super(BooleanConst, "" + value, line, col);
		Value = (value.equals("true"));
	}
	public String toString() {
		return "Boolean(" + Value + ")";
	}
}
