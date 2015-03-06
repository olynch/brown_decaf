//CS4
//2014-2015
//Scanner.java (Lexical Analysis)

//This class will be instantiated and used by the next piece, Parser.java
package me.owenlynch.brown_decaf;
import java.io.*;
import java.util.HashSet;

class Scanner {
    final InputStream File;
	/* 
	 * token = [a-zA-Z0-9_-_]+
	 * stringLiteral = "(!|[#-~]|\\n|\\")*"
	 * int = [0-9]+
	 * double = [0-9]+\.[0-9]+
	 * punctuation = [.,;\[\]\(\)\{\}]
	 * character = '([!-&]|[(-~]|\\n|\\')'
	 * operator = +|*|-|/|=|==|!=|>=|<=|>|<|!|&&| || |
	 */
	private static DFA decafDFA = new DFA(
			"{" +
			"\"num_states\": 17," +
			"\"accepting\": [[1, \"Identifier\"], [4, \"StringConst\"], [5, \"IntConst\"], [7, \"DoubleConst\"], [11, \"CharacterConst\"], [12, \"Punctuation\"], [14, \"Operator\"], [15, \"Operator\"]]," +
			"\"dfa_arr\": [" +
				//0 (Start)
				"{ \"a-zA-Z\": 1, \"\\\"\": 2, \"0-9\": 5, \"'\": 8, \";,.{}[]()\": 12, \"&\": 13, \"+*-/\": 14, \"!=<>\": 15, \"|\": 16}," +
				//1 (ID)
				"{ \"a-zA-Z0-9\": 1 }," +
				//2 (String)
				"{ \"\\\\\": 3, \"^'\\\\\": 2, \"\\\"\": 4}," +
				//3 (String \)
				"{ \" -~\": 2 }," +
				//4 (String F)
				"{ }," +
				//5 (Int)
				"{ \"0-9\": 5, \".\": 6 }," +
				//6 (Decimal point)
				"{ \"0-9\": 7 }," +
				//7 (Float)
				"{ \"0-9\": 7 }," +
				//8 (Char)
				"{ \"\\\\\": 9, \"^'\\\\\": 10 }," +
				//9 (Char \)
				"{ \"!-~\": 10 }," +
				//10 (Char after \)
				"{ \"'\": 11 }," +
				//11 (Char F)
				"{ }," +
				//12 (Punctuation)
				"{ }," +
				//13 (Operator &)
				"{ \"&\": 14 }," +
				//14 (Operator F)
				"{ }," +
				//15 (Operator =)
				"{ \"=\": 14 }," +
				//16 (Operator |)
				"{ \"|\": 14 }" +
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

    public int look() {
        //should look at the next character but not read it
		File.mark(2);
		int theChar;
		try {
			theChar = File.read();
			File.reset();
		} catch (IOException e) {
			return -1;
		}
		return theChar;
    }

    public int getChar() {
        //should read the next character
		char curChar;
		try {
			curChar = (char) File.read();
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
		while (true) {
			switch ((char) look()) {
				case ' ':
				case '\n':
				case '\t':
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
			return null;
		}
		int curCol = col;
		int curLine = line;
		int state = 0;
		int next_state = 0;
		StringBuilder acc = new StringBuilder();
		int cur;
		char curChar;
		while (true) {
			cur = look();
			if (cur == -1) {
				if (decafDFA.isAccepting(state)) {
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
			next_state = decafDFA.transition(state, curChar);
			if (next_state == -1) {
				if (decafDFA.isAccepting(state)) {
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
		String tkn = decafDFA.token(state);
		String accStr = acc.toString();
		switch (tkn) {
			case "Identifier":
				if (isKeyword(accStr)){
					return new KeywordToken(accStr, curLine, curCol);
				}
				if (isBoolConst(accStr)) {
					return new BooleanToken(accStr, curLine, curCol);
				}
				if (isNullConst(accStr)) {
					return new NullToken(accStr, curLine, curCol);
				}
				else {
					return new IDToken(accStr, curLine, curCol);
				}
			case "IntConst":
				return new IntToken(accStr, curLine, curCol);
			case "DoubleConst":
				return new DoubleToken(accStr, curLine, curCol);
			case "StringConst":
				return new StringToken(accStr, curLine, curCol);
			case "CharacterConst":
				return new CharacterToken(accStr, curLine, curCol);
			case "Operator":
				return new OperatorToken(accStr, curLine, curCol);
			case "Punctuation":
				return new PunctuationToken(accStr, curLine, curCol);
			default:
				throw new ScanException("Token type not found");
		}
    }

    private boolean isKeyword(String name){
		return keywords.contains(name);
    }

	private boolean isBoolConst(String name) {
		return (name == "true" || name == "false");
	}

	private boolean isNullConst(String name) {
		return (name == "null");
	}
    
    String getIdentifier() {
        //to implement
		return "";
    }
    
}
