package me.owenlynch.brown_decaf;

class KeywordToken extends Token {
	private static HashSet<String> primitiveTypes = makeset(set -> {
		set.add("int");
		set.add("double");
		set.add("char");
		set.add("string");
	});
	private static HashSet<String> modifiers = makeset(set -> {
		set.add("public");
		set.add("private");
		set.add("protected");
		set.add("static");
	});

	String value;

	public KeywordToken(String text, int line, int col) {
		super(TName.KEYWORD, line, col);
		value = text;
		if (primitiveTypes.contains(text)) {
			type = TName.PRIMITIVETYPE;
		}
		else if (modifiers.contains(text)) {
			type = TName.MODIFIER;
		}
		else {
			type = TName.valueOf(text.toUpperCase());
		}
	}

	public void setVal(Token other) {
		value = other.value;
	}

	public String toString() {
		return "Keyword(<" + value + ">, " + Line + ":" + Column + ")";
	}
}
