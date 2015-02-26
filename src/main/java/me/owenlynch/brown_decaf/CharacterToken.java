package me.owenlynch.brown_decaf;

class CharacterToken extends Token {
	char val;
	public CharacterToken(String thechar, int line, int col) {
		super(Token.CharConst, thechar, line, col);
		switch (thechar) {
			case "\\n":
				val = '\n';
				break;
			case "\\t":
				val = '\t';
				break;
			default:
				if (thechar.length() == 1) {
					val = thechar.charAt(0);
				}
				else if (thechar.charAt(0) == '/') {
					val = thechar.charAt(1);
				}
				//one of these should be true; guaranteed by the dfa
				break;
		}
	}
}
