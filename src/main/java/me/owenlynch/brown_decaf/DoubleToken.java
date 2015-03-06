package me.owenlynch.brown_decaf;

class DoubleToken extends Token {
	double Value;
	public DoubleToken(String value, int line, int col) {
		super(DoubleConst, "" + value, line, col);
		Value = Double.parseDouble(value);
	}
	public String toString() {
		return "Double(" + Value + ")";
	}
}
