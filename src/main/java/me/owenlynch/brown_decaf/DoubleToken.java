package me.owenlynch.brown_decaf;

class DoubleToken extends Token {
	double Value;
	public DoubleToken(double value, int line, int col) {
		super(DoubleConst, "" + value, line, col);
		Value = value;
	}
	public String toString() {
		return "Double(" + Value + ")";
	}
}
