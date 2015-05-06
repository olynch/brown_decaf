//CS4
//2014-2015
//Token.java

package me.owenlynch.brown_decaf;
import java.util.HashSet;
import java.util.BitSet;

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

	public final static BitSet tokens = makebitset(set -> {
		set.set(SType.NULL.ordinal());
		set.set(SType.BOOLEANLIT.ordinal());
		set.set(SType.INTLIT.ordinal());
		set.set(SType.CHARLIT.ordinal());
		set.set(SType.STRINGLIT.ordinal());
		set.set(SType.DOUBLELIT.ordinal());
		set.set(SType.IDENTIFIER.ordinal());
		set.set(SType.PRIMITIVETYPE.ordinal());
		set.set(SType.MODIFIER.ordinal());
		set.set(SType.WHILE.ordinal());
		set.set(SType.RETURN.ordinal());
		set.set(SType.CONTINUE.ordinal());
		set.set(SType.NEW.ordinal());
		set.set(SType.BREAK.ordinal());
		set.set(SType.CLASS.ordinal());
		set.set(SType.ELSE.ordinal());
		set.set(SType.EXTENDS.ordinal());
		set.set(SType.FOR.ordinal());
		set.set(SType.IF.ordinal());
		set.set(SType.THIS.ordinal());
		set.set(SType.VOID.ordinal());
		set.set(SType.BREAK.ordinal());
		set.set(SType.SUPER.ordinal());
		set.set(SType.OPERATOR.ordinal());
		set.set(SType.EQUALS.ordinal());
		set.set(SType.PIPEPIPE.ordinal());
		set.set(SType.AMPAMP.ordinal());
		set.set(SType.EQUALSEQUALS.ordinal());
		set.set(SType.BANGEQUALS.ordinal());
		set.set(SType.BANG.ordinal());
		set.set(SType.LT.ordinal());
		set.set(SType.GT.ordinal());
		set.set(SType.LTEQUALS.ordinal());
		set.set(SType.GTEQUALS.ordinal());
		set.set(SType.PLUS.ordinal());
		set.set(SType.MINUS.ordinal());
		set.set(SType.STAR.ordinal());
		set.set(SType.SLASH.ordinal());
		set.set(SType.PERCENT.ordinal());
		set.set(SType.COMMA.ordinal());
		set.set(SType.SEMICOLON.ordinal());
		set.set(SType.PERIOD.ordinal());
		set.set(SType.OPENBRACKET.ordinal());
		set.set(SType.CLOSEBRACKET.ordinal());
		set.set(SType.OPENPAREN.ordinal());
		set.set(SType.CLOSEPAREN.ordinal());
		set.set(SType.OPENBRACE.ordinal());
		set.set(SType.CLOSEBRACE.ordinal());
	}, SType.values());

	public static boolean isToken(int t) {
		return tokens.get(t);
	}

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
