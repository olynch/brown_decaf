package me.owenlynch.brown_decaf;

class DoubleToken extends Token {
	double value;

	public DoubleToken(String value, int line, int col) {
		super(TName.DOUBLELIT, line, col);
		this.value = Double.parseDouble(value);
	}

	public void setVal(Token other) {
		value = other.value;
	}

	public String toString() {
		return "Double(" + value + ")";
	}
}
