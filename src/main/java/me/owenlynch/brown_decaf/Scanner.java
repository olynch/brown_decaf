//CS4
//2014-2015
//Scanner.java (Lexical Analysis)

//This class will be instantiated and used by the next piece, Parser.java
package me.owenlynch.brown_decaf;
import java.io.*;
import java.util.HashSet;

class Scanner {
    final InputStream File;
	private static DFA decafDFA = new DFA(
			"{ 'accepting': []," +
			"'dfa_arr': [" +
			"]" +
			"}"
			);
	private static HashSet<String> keywords = keywordsHash();
	private int col;
	private int line;

    public Scanner(java.io.InputStream file) {
        File = file;
    }

	public static HashSet<String> keywordsHash() {
		HashSet<String> keyset = new HashSet<String>();
        keyset.add("new");
        keyset.add("print");
        keyset.add("readint");
        keyset.add("readline");
        keyset.add("bool");
        keyset.add("break");
        keyset.add("class");
        keyset.add("double");
        keyset.add("else");
        keyset.add("extends");
        keyset.add("for");
        keyset.add("if");
        keyset.add("implements");
        keyset.add("int");
        keyset.add("interface");
        keyset.add("null");
        keyset.add("return");
        keyset.add("string");
        keyset.add("this");
        keyset.add("void");
        keyset.add("while");
		return keyset;
	}

    public int look() throws IOException {
        //should look at the next character but not read it
		File.mark(2);
		int theChar = File.read();
		File.reset();
		return theChar;
    }

    public int getChar() throws IOException {
        //should read the next character
		char curChar = (char) File.read();
		col++;
		if (curChar == '\n') {
			line++;
			col = 0;
		}
		return curChar;
    }

	void skipSingleLineComment() throws IOException {
		while (File.available() > 0) {
			switch ((char) getChar()) {
				case '\n':
					return;
				default:
					break;
			}
		}
	}

	void skipMultiLineComment() throws IOException {
		while (File.available() > 0) {
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
		while (File.available() > 0) {
			switch ((char) look()) {
				case ' ':
				case '\n':
					getChar();
					break;
				case '/':
					File.mark(3);
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
						File.reset();
						line = curLine;
						col = curCol;
						return;
					}
			}
			getChar();
		}
    }

    public Token getToken() throws IOException, ScanException {
        //to implement
        //this is the meat of the Scanner, I would suggest breaking it down
		skipSpace();
		int curCol = col;
		int curLine = line;
		int state = 0;
		StringBuilder acc = new StringBuilder();
		char curChar;
		while (File.available() > 0) {
			curChar = (char) look();
			acc.append(curChar);
			try {
				state = decafDFA.transition(state, curChar);
			} catch (ScanException e) {
				if (decafDFA.isAccepting(state)) {
					break;
				}
				else {
					throw e;
				}
			}
		}
		String tkn = decafDFA.token(state);
		String accStr = acc.toString();
		switch (tkn) {
			case "Identifier":
				if (isKeyword(accStr) ){
					return new KeywordToken(accStr.toUpperCase(), curLine, curCol);
				}
				else {
					return new IDToken(accStr, curLine, curCol);
				}
			case "IntConst":
				return new IntToken(Integer.parseInt(accStr), curLine, curCol);
			case "DoubleConst":
				return new DoubleToken(Double.parseDouble(accStr), curLine, curCol);
			case "BooleanConst":
				return new BooleanToken(Boolean.parseBoolean(accStr), curLine, curCol);
			case "StringConst":
				return new StringToken(accStr.substring(1, accStr.length() - 1), curLine, curCol);
			case "CharacterConst":
				return new CharacterToken(accStr.substring(1, accStr.length() - 1), curLine, curCol);
			case "Operator":
				return new OperatorToken(accStr, curLine, curCol);
			case "Punctuation":
				return new PunctuationToken(accStr.charAt(0), curLine, curCol);
			default:
				throw new ScanException("Token type not found");
		}
    }

    boolean isKeyword(String name){
        //to implement
		return keywords.contains(name);
    }
    
    String getIdentifier() {
        //to implement
		return "";
    }
    
}
