package me.owenlynch.brown_decaf;

class PunctuationToken extends Token {
	public PunctuationToken(char thepunc, int line, int col) {
		super(Token.Punc, String.valueOf(thepunc), line, col);
	}

	public String toString() {
		return "Punctuation(\"" + Text + "\", " + Line + ":" + Column + ")";
	}
}
