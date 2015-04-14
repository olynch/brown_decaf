//CS4
//2014-2015
//Token.java

package me.owenlynch.brown_decaf;

class Token implements Symbol {
	public final SType type;
	public final int line;
	public final int column;
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

	public Token(SType type, int line, int column) {
		this.type = type;
		this.line = line;
		this.column = column;
	}

	public Token(SType type) {
		// Used for reference in productions
		this.type = type;
		this.line = -1;
		this.column = -1;
	}

	public boolean isTerminal() {
		return true;
	}

	public boolean equals(Token other) {
		return (type == other.type);
	}

	public Token clone() {
		return new Token(type);
	}

	public String toString() {
		return "Token(" + SType.toString(type) + ", " + Line + ":" + Column + ")";
	}

	public boolean equals(Token other) {
		return (type.equals(other.type));
	}
}
