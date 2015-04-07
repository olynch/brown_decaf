package me.owenlynch.brown_decaf;

class CharacterToken extends Token {
	char value;
	public CharacterToken(String inchar, int line, int col) {
		super(TName.CHARLIT, line, col);
		String thechar = inchar.substring(1, inchar.length() - 1);
		switch (thechar) {
			case "\\n":
				value = '\n';
				break;
			case "\\t":
				value = '\t';
				break;
			default:
				if (thechar.length() == 1) {
					value = thechar.charAt(0);
				}
				else if (thechar.charAt(0) == '\\') {
					value = thechar.charAt(1);
				}
				//one of these should be true; guaranteed by the dfa
				break;
		}
	}
}
