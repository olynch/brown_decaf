package me.owenlynch.brown_decaf;

class KeywordToken extends Token {
	public KeywordToken(String text, int line, int col) {
		super(text.toUpperCase(), text, line, col);
	}
	public String toString() {
		return "Keyword(<" + Text + ">, " + Line + ":" + Column + ")";
	}
}
