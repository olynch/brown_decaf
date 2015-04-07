//CS4
//2014-2015
//Token.java

package me.owenlynch.brown_decaf;

class Token {
	public final TName type;
	public final int line;
	public final int column;

	public Token(String type, String text, int line, int column) {
		this.type = type;
		this.line = line;
		this.column = column;
	}

	public String toString() {
		return "Token(" + TName.toString(type) + ", " + Line + ":" + Column + ")";
	}

	public boolean equals(Token other) {
		return (type.equals(other.type));
	}

}
