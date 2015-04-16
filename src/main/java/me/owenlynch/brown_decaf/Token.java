//CS4
//2014-2015
//Token.java

package me.owenlynch.brown_decaf;

class Token implements Symbol {
	public final SType type;
	public final String text;
	public final int line;
	public final int column;

	private static HashSet<String> keywords = makeset(set -> {
        set.add("new");
        set.add("print");
        set.add("readint");
        set.add("readline");
        set.add("bool");
        set.add("break");
        set.add("class");
        set.add("double");
        set.add("else");
        set.add("extends");
        set.add("for");
        set.add("if");
        set.add("implements");
        set.add("int");
        set.add("interface");
        set.add("null");
        set.add("return");
        set.add("string");
        set.add("this");
        set.add("void");
        set.add("while");
	});

	public final static HashSet<SType> STypes = makeset(set -> {
		set.add(SType.NULL);
		set.add(SType.BOOLEANLIT);
		set.add(SType.INTLIT);
		set.add(SType.CHARLIT);
		set.add(SType.STRINGLIT);
		set.add(SType.DOUBLELIT);
		set.add(SType.IDENTIFIER);
		set.add(SType.PRIMITIVETYPE);
		set.add(SType.MODIFIER);
		set.add(SType.WHILE);
		set.add(SType.RETURN);
		set.add(SType.CONTINUE);
		set.add(SType.NEW);
		set.add(SType.BREAK);
		set.add(SType.CLASS);
		set.add(SType.ELSE);
		set.add(SType.EXTENDS);
		set.add(SType.FOR);
		set.add(SType.IF);
		set.add(SType.THIS);
		set.add(SType.VOID);
		set.add(SType.BREAK);
		set.add(SType.SUPER);
		set.add(SType.OPERATOR);
		set.add(SType.EQUALS);
		set.add(SType.PIPEPIPE);
		set.add(SType.AMPAMP);
		set.add(SType.EQUALSEQUALS);
		set.add(SType.BANGEQUALS);
		set.add(SType.LT);
		set.add(SType.GT);
		set.add(SType.LTEQUALS);
		set.add(SType.GTEQUALS);
		set.add(SType.PLUS);
		set.add(SType.MINUS);
		set.add(SType.STAR);
		set.add(SType.SLASH);
		set.add(SType.PERCENT);
		set.add(SType.COMMA);
		set.add(SType.SEMICOLON);
		set.add(SType.PERIOD);
		set.add(SType.OPENBRACKET);
		set.add(SType.CLOSEBRACKET);
		set.add(SType.OPENPAREN);
		set.add(SType.CLOSEPAREN);
		set.add(SType.OPENBRACE);
		set.add(SType.CLOSEBRACE);
	});

	public Token(TokenType type, String text, int line, int column) {
		this.line = line;
		this.column = column;
		switch (type) {
			case TokenName.INTLIT:
				this.type = SType.INTLIT;
			case TokenName.DOUBLELIT:
				this.type = SType.DOUBLELIT;
			case TokenName.CHARLIT:
				this.type = SType.CHARLIT;
			case TokenName.STRINGLIT:
				this.type = SType.STRINGLIT;
			case TokenName.OPERATOR:
				this.type = operatorMap.get(text);
			case TokenName.IDENTIFIER:
				SType possibleType = identifierMap.get(text);
				if (possibleType == null) {
					this.type = IDENTIFIER;
				}
				else {
					this.type = possibleType;
				}
			case TokenName.PUNCTUATION:
				this.type = punctuationMap.get(text);
			case TokenName.EOF:
				this.type = SType.EOF;
		}
	}

	public Token(TokenType type, String text) {
		// Used for reference in productions
		Token(type, text, -1, -1);
	}

	public boolean isTerminal() {
		return true;
	}

	public boolean equals(Token other) {
		return (type == other.type);
	}

    private boolean isKeyword(String name) {
		return keywords.contains(name);
    }

	private boolean isBoolConst(String name) {
		return (name == "true" || name == "false");
	}

	private boolean isNullConst(String name) {
		return (name == "null");
	}

	public String toString() {
		// TODO:
	}
}
