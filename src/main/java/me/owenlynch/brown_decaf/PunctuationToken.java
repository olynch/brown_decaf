package me.owenlynch.brown_decaf;

class PunctuationToken extends Token {
	public PunctuationToken(String thepunc, int line, int col) {
		super(PUNCTUATION, line, col);
		switch (thepunc) {
			case ",":
				type = TName.COMMA;
			case ";":
				type = TName.SEMICOLON;
			case ".":
				type = TName.PERIOD;
			case "[":
				type = TName.OPENBRACKET;
			case "]":
				type = TName.CLOSEBRACKET;
			case "(":
				type = TName.OPENPAREN;
			case ")":
				type = TName.CLOSEPAREN;
			case "{":
				type = TName.OPENBRACE;
			case "}":
				type = TName.CLOSEBRACE;
		}
	}

	public String toString() {
		return "Punctuation(\"" + type.toString() + "\", " + line + ":" + column + ")";
	}
}
