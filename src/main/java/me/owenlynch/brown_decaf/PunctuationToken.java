package me.owenlynch.brown_decaf;

class PunctuationToken extends Token {
	char val;
	public PunctuationToken(String thepunc, int line, int col) {
		super(Token.Punc, thepunc, line, col);
		val = thepunc.charAt(0);
	}

	public String toString() {
		return "Punctuation(\"" + Text + "\", " + Line + ":" + Column + ")";
	}
}
