//CS4
//2014-2015
//Token.java

package me.owenlynch.brown_decaf;

class Token implements Symbol {
	public final int type;
	public final String text;
	public final int line;
	public final int column;

	public Token(int type, String text, int line, int column) {
		this.line = line;
		this.column = column;
		this.type = type;
		this.text = text;
	}

	public boolean isTerminal() {
		return true;
	}

	public boolean equals(Token other) {
		return (type == other.type);
	}

	public String toString() { // JAVA-SPECIFIC :(
		// TODO:
		return String.format("{%s, %s, %d:%d}", SType.lookup[type].toString(), text, line, column);
	}

	public int type() {
		return type;
	}
}
