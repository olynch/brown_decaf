package me.owenlynch.brown_decaf;

class IntToken extends Token {
	int value;
	public IntToken(String value, int line, int col) {
		super(TName.INTLIT, line, col);
		this.value = Integer.parseInt(value);
	}

	public String toString() {
		return "Int(" + value + ", " + Line + ":" + Column + ")";
	}
}
