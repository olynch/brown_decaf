//CS4
//2014-2015
//Token.java

package me.owenlynch.brown_decaf;

class Token {
    public static final String
        IntConst     = "IntConst",
        DoubleConst  = "DoubleConst",
        BooleanConst = "BooleanConst",
        StringConst  = "StringConst",
        ID           = "Identifier",
        OP           = "Operator",
        NEWARRAY     = "NEWARRAY",
        NEW          = "NEW",
        PRINT        = "PRINT",
        READINT      = "READINT",
        READLINE     = "READLINE",
        BOOL         = "BOOL",
        BREAK        = "BREAK",
        CLASS        = "CLASS",
        DOUBLE       = "DOUBLE",
        ELSE         = "ELSE",
        EXTENDS      = "EXTENDS",
        FOR          = "FOR",
        IF           = "IF",
        IMPLEMENTS   = "IMPLEMENTS",
        INT          = "INT",
        INTERFACE    = "INTERFACE",
        NULL         = "NULL",
        RETURN       = "RETURN",
        STRING       = "STRING",
        THIS         = "THIS",
        VOID         = "VOID",
        WHILE        = "WHILE",
        EOF          = "EOF";

	public final String Type;
	public final String Text;
	public final int Line;
	public final int Column;

	public Token(String type, String text, int line, int column) {
		Type = type;
		Text = text;
		Line = line;
		Column = column;
	}

	public String toString() {
		return "Token(" + Type + ", \"" + Text + "\", " + Line + ":" + Column + ")";
	}

}

class KeywordToken extends Token {
	public KeywordToken(String text, int line, int col) {
		super(text, text, line, col);
	}
	public String toString() {
		return "Keyword(<" + Text + ">, " + Line + ":" + Column + ")";
	}
}

class IntToken extends Token {
	int Value;
	public IntToken(int value, int line, int col) {
		super(IntConst, "" + value, line, col);
		Value = value;
	}

	public String toString() {
		return "Int(" + Value + ")";
	}
}

class DoubleToken extends Token {
	double Value;
	public DoubleToken(double value, int line, int col) {
		super(DoubleConst, "" + value, line, col);
		Value = value;
	}
	public String toString() {
		return "Double(" + Value + ")";
	}
}

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

class BooleanToken extends Token {
	boolean Value;
	public BooleanToken(boolean value, int line, int col) {
		super(BooleanConst, "" + value, line, col);
		Value = value;
	}
	public String toString() {
		return "Boolean(" + Value + ")";
	}
}

class IDToken extends Token {
	String Name;
	public IDToken(String name, int line, int col) {
		super(ID, name, line, col);
		Name = name;
	}
	public String toString() {
		return "ID(" + Name + ")";
	}
}

class OperatorToken extends Token {
	String Value;

	public OperatorToken(String op, int line, int col) {
		super(Token.OP, "" + op, line, col);
		Value = op;
	}
	public String toString() {
		return "Operator(" + Text + ", " + Line + ":" + Column + ")";
	}
}
