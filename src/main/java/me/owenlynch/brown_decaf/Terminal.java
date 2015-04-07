package me.owenlynch.brown_decaf;

class Terminal extends Symbol {
	private TName type;
	private static HashMap<type, String> expectedTokenType = createExpectedTokenTypeHash();
	private String expectedTokenText;
	private Token val;

	public Terminal(TName type, String expectedTokenText) {
		this.type = type;
		this.expectedTokenText = expectedTokenText;
		this.val = null;
	}

	public boolean isTerminal() {
		return true;
	}

	public void setVal(Token val) {
		this.val = val;
	}

	public boolean matches(Token match) {
		boolean matchType = match.Type.equals(expectedTokenType.get(type));
		boolean matchText = false;
		if (expectedTokenText == null) {
			matchText = true;
		}
		else {
			matchText = match.Text.equals(expectedTokenText);
		}
		return matchType && matchText;
	}

	public Terminal clone() {
		return new Terminal(type, expectedTokenText);
	}
}
