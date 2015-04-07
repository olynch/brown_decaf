//CS4
//2014-2015
//Scanner.java (Lexical Analysis)

//This class will be instantiated and used by the next piece, Parser.java
package me.owenlynch.brown_decaf;
import java.io.*;
import DataDSL.*
import java.util.HashSet;

class Scanner {
    final InputStream file;
	/* 
	 * token = [a-zA-Z0-9_-_]+
	 * stringLiteral = "(!|[#-~]|\\n|\\")*"
	 * int = [0-9]+
	 * double = [0-9]+\.[0-9]+
	 * punctuation = [.,;\[\]\(\)\{\}]
	 * character = '([!-&]|[(-~]|\\n|\\')'
	 * operator = +|*|-|/|=|==|!=|>=|<=|>|<|!|&&| || |
	 */
	private static DFA decafDFA = new DFA(makemap(map -> {
		map.put("num_states", 17);
		map.put("accepting", makelist(list -> {
			list.add(makelist(list -> 
						{ list.add(1); list.add(TName.IDENTIFIER); }));
			list.add(makelist(list ->
						{ list.add(4); list.add(TName.STRINGLIT); }));
			list.add(makelist(list ->
						{ list.add(5); list.add(TName.INTLIT); }));
			list.add(makelist(list ->
						{ list.add(7); list.add(TName.DOUBLELIT); }));
			list.add(makelist(list ->
						{ list.add(11); list.add(TName.CHARACTERLIT); }));
			list.add(makelist(list ->
						{ list.add(12); list.add(TName.PUNCTUATION); }));
			list.add(makelist(list ->
						{ list.add(14); list.add(TName.OPERATOR); }));
			list.add(makelist(list ->
						{ list.add(15); list.add(TName.OPERATOR); }));
		}));
		map.put("dfa_arr", makelist(list -> {
			list.add(makemap(map -> {
				map.put("a-zA-Z", 1);
				map.put("\"", 2);
				map.put("0-9", 5);
				map.put("'", 8);
				map.put(";,.{}[]()", 12);
				map.put("&", 13);
				map.put("+*-/", 14);
				map.put("!=<>", 15);
				map.put("|", 16);
				// Start
			}));
			list.add(makemap(map -> {
				map.put("a-zA-Z0-9", 1);
				// 1 (ID)
			}));
			list.add(makemap(map -> {
				map.put("\\", 3);
				map.put("^'\\", 2);
				map.put("\"", 4);
				// 2 (String)
			}));
			list.add(makemap(map -> {
				map.put(" -~", 2);
				// 3 (String)
			}));
			list.add(makemap(map -> {
				// 4 (String F)
			}));
			list.add(makemap(map -> {
				map.put("0-9", 5);
				map.put(".", 6);
				// 5 (Int)
			}));
			list.add(makemap(map -> {
				map.put("0-9", 7);
				// 6 (Decimal Point)
			}));
			list.add(makemap(map -> {
				map.put("0-9", 7);
				// 7 (Float)
			}));
			list.add(makemap(map -> {
				map.put("\\", 9);
				map.put("^'\\", 10);
				// 8 (Char),
			}));
			list.add(makemap(map -> {
				map.put("!-~", 10);
				// 9 (Char \\),
			}));
			list.add(makemap(map -> {
				map.put("'", 11);
				// 10 (Char after \\),
			}));
			list.add(makemap(map -> {
				// 11 (Char F),
			}));
			list.add(makemap(map -> {
				// 12 (Punctuation),
			}));
			list.add(makemap(map -> {
				map.put("&", 14);
				// 13 (Operator &),
			}));
			list.add(makemap(map -> {
				// 14 (Operator F),
			}));
			list.add(makemap(map -> {
				map.put("=", 14);
				// 15 (Operator =),
			}));
			list.add(makemap(map -> {
				map.put("|", 14);
				// 16 (Operator |)
			}));
		}));
	}));
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
	private int col;
	private int line;

    public Scanner(java.io.InputStream file) {
        this.file = file;
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
		TName tkn = decafDFA.token(state);
		String accStr = acc.toString();
		switch (tkn) {
			case IDENTIFIER:
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
			case INTLIT:
				return new IntToken(accStr, curLine, curCol);
			case DOUBLELIT:
				return new DoubleToken(accStr, curLine, curCol);
			case STRINGLIT:
				return new StringToken(accStr, curLine, curCol);
			case CHARLIT:
				return new CharacterToken(accStr, curLine, curCol);
			case OPERATOR:
				return new OperatorToken(accStr, curLine, curCol);
			case PUNCTUATION:
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
