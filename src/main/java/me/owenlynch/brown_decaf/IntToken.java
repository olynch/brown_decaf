package me.owenlynch.brown_decaf;

class IntToken extends Token {
	int Value;
	public IntToken(String value, int line, int col) {
		super(IntConst, "" + value, line, col);
		Value = Integer.parseInt(value);
	}

	public String toString() {
		return "Int(" + Value + ", " + Line + ":" + Column + ")";
	}
}
