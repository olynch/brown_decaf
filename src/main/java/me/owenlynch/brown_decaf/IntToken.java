package me.owenlynch.brown_decaf;

class IntToken extends Token {
	int Value;
	public IntToken(int value, int line, int col) {
		super(IntConst, "" + value, line, col);
		Value = value;
	}

	public String toString() {
		return "Int(" + Value + ")";
	}
}
