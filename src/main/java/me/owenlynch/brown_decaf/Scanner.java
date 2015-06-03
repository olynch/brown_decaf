//CS4
//2014-2015
//Scanner.java (Lexical Analysis)

//This class will be instantiated and used by the next piece, Parser.java
package me.owenlynch.brown_decaf;
import java.io.*;
import static me.owenlynch.brown_decaf.DataDSL.*;
import java.util.HashMap;

class Scanner {
    final InputStream file;
	final DFA dfa;
	final TokenFactory factory;
	/* 
	 * identifier = [a-zA-Z0-9_-_]+
	 * stringLiteral = "(!|[#-~]|\\n|\\")*"
	 * int = [0-9]+
	 * double = [0-9]+\.[0-9]+
	 * punctuation = [.,;\[\]\(\)\{\}]
	 * character = '([!-&]|[(-~]|\\n|\\')'
	 * operator = +|*|-|/|=|==|!=|>=|<=|>|<|!|&&| || |
	 */
	private int col;
	private int line;

    public Scanner(java.io.InputStream file, DFA dfa, TokenFactory factory) {
        this.file = file;
		this.dfa = dfa;
		this.factory = factory;
		this.col = 0;
		this.line = 0;
    }

    public int look() {
        //should look at the next character but not read it
		file.mark(2);
		int theChar;
		try {
			theChar = file.read();
			file.reset();
		} catch (IOException e) {
			return -1;
		}
		return theChar;
    }

    public int getChar() {
        //should read the next character
		char curChar;
		try {
			curChar = (char) file.read();
		} catch (IOException e) {
			return -1;
		}
		col++;
		if (curChar == '\n') {
			line++;
			col = 0;
		}
		return curChar;
    }

	void skipSingleLineComment() throws IOException {
		while (file.available() > 0) {
			switch ((char) getChar()) {
				case '\n':
					return;
				default:
					break;
			}
		}
	}

	void skipMultiLineComment() throws IOException {
		while (file.available() > 0) {
			switch ((char) getChar()) {
				case '*':
					char next = (char) getChar();
					if (next == '/') {
						return;
					}
					break;
				default:
					break;
			}
		}
	}

    void skipSpace() throws IOException {
        //to implement
		while (true) {
			switch ((char) look()) {
				case ' ':
				case '\n':
				case '\t':
					break;
				case '/':
					file.mark(3);
					int curLine = line;
					int curCol = col;
					getChar();
					char next = (char) getChar();
					if (next == '/') {
						skipSingleLineComment();
						break;
					}
					else if (next == '*') {
						skipMultiLineComment();
						break;
					}
					else {
						file.reset();
						line = curLine;
						col = curCol;
						return;
					}
				default:
					return;
			}
			getChar();
		}
    }

    public Token getToken() throws ScanException {
        //to implement
        //this is the meat of the Scanner, I would suggest breaking it down
		try {
			skipSpace();
		} catch (IOException e) { //eof
			return factory.makeToken(dfa.getEOFType(), "", line, col);
		}
		int state = 0;
		int next_state = 0;
		StringBuilder acc = new StringBuilder();
		int cur;
		char curChar;
		while (true) {
			cur = look();
			if (cur == -1) {
				if (dfa.isAccepting(state)) {
					break;
				}
				else if (state == 0) {
					return null;
				}
				else {
					throw new ScanException("End of Input while scanning");
				}
			}
			curChar = (char) cur;
			next_state = dfa.transition(state, curChar);
			if (next_state == -1) {
				if (dfa.isAccepting(state)) {
					break;
				}
				else {
					throw new ScanException("No transition at " + line + ":" + col + ", state: " + state + ", curChar: " + curChar);
				}
			}
			acc.append(curChar);
			state = next_state;
			getChar();
		}
		int tkn = dfa.token(state);
		String accStr = acc.toString();
		return factory.makeToken(tkn, accStr, line, col);
    }
}
