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
		CharConst    = "CharacterConst",
        ID           = "Identifier",
        OP           = "Operator",
		Punc		 = "Punctuation",
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
