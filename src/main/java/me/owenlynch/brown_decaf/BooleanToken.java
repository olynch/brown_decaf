package me.owenlynch.brown_decaf;

class BooleanToken extends Token {
	boolean Value;
	public BooleanToken(boolean value, int line, int col) {
		super(BooleanConst, "" + value, line, col);
		Value = value;
	}
	public String toString() {
		return "Boolean(" + Value + ")";
	}
}
