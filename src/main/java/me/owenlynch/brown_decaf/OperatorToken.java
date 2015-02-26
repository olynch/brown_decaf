package me.owenlynch.brown_decaf;

class OperatorToken extends Token {
	String Value;

	public OperatorToken(String op, int line, int col) {
		super(Token.OP, "" + op, line, col);
		Value = op;
	}
	public String toString() {
		return "Operator(" + Text + ", " + Line + ":" + Column + ")";
	}
}
