class StringToken extends Token {
	String Value;
	public StringToken(String value, int line, int col) {
		super(StringConst, value, line, col);
		Value = value;
	}
	public String toString() {
		return "String(" + Value + ")";
	}
}
