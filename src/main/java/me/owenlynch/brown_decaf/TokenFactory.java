package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.ArrayList;
import static me.owenlynch.brown_decaf.DataDSL.*;

public class TokenFactory {
	public final SymbolUniverse universe;
	public final ArrayList<HashMap<String, Integer>> tokenTypeToSymbol;
	public final int[] defaultSymbolForTokenType;

	public static TokenFactory JavaTokenFactory = new TokenFactory(
			SymbolUniverse.JavaSymbols,
			makelist(list -> {
				// 0 = INTLIT
				list.add(makemap(map -> {}));
				// 1 = DOUBLELIT
				list.add(makemap(map -> {}));
				// 2 = STRINGLIT
				list.add(makemap(map -> {}));
				// 3 = CHARLIT
				list.add(makemap(map -> {}));
				// 4 = OPERATOR
				list.add(makemap(map -> {
					map.put("=", SType.EQUALS.ordinal());
					map.put("||", SType.PIPEPIPE.ordinal());
					map.put("&&", SType.AMPAMP.ordinal());
					map.put("==", SType.EQUALSEQUALS.ordinal());
					map.put("!=", SType.BANGEQUALS.ordinal());
					map.put("!", SType.BANG.ordinal());
					map.put("<", SType.LT.ordinal());
					map.put(">", SType.GT.ordinal());
					map.put("<=", SType.LTEQUALS.ordinal());
					map.put(">=", SType.GTEQUALS.ordinal());
					map.put("+", SType.PLUS.ordinal());
					map.put("-", SType.MINUS.ordinal());
					map.put("*", SType.STAR.ordinal());
					map.put("/", SType.SLASH.ordinal());
					map.put("%", SType.PERCENT.ordinal());
				}));
				// 5 = IDENTIFIER
				list.add(makemap(map -> {
					map.put("int", SType.PRIMITIVETYPE.ordinal());
					map.put("double", SType.PRIMITIVETYPE.ordinal());
					map.put("char", SType.PRIMITIVETYPE.ordinal());
					map.put("boolean", SType.PRIMITIVETYPE.ordinal());
					map.put("public", SType.MODIFIER.ordinal());
					map.put("static", SType.MODIFIER.ordinal());
					map.put("private", SType.MODIFIER.ordinal());
					map.put("protected", SType.MODIFIER.ordinal());
					map.put("while", SType.WHILE.ordinal());
					map.put("return", SType.RETURN.ordinal());
					map.put("continue", SType.CONTINUE.ordinal());
					map.put("new", SType.NEW.ordinal());
					map.put("break", SType.BREAK.ordinal());
					map.put("class", SType.CLASS.ordinal());
					map.put("else", SType.ELSE.ordinal());
					map.put("extends", SType.EXTENDS.ordinal());
					map.put("for", SType.FOR.ordinal());
					map.put("if", SType.IF.ordinal());
					map.put("this", SType.THIS.ordinal());
					map.put("void", SType.VOID.ordinal());
					map.put("super", SType.SUPER.ordinal());
				}));
				// 6 = PUNCTUATION
				list.add(makemap(map -> {
					map.put(",", SType.COMMA.ordinal());
					map.put(";", SType.SEMICOLON.ordinal());
					map.put(".", SType.PERIOD.ordinal());
					map.put("[", SType.OPENBRACKET.ordinal());
					map.put("]", SType.CLOSEBRACKET.ordinal());
					map.put("(", SType.OPENPAREN.ordinal());
					map.put(")", SType.CLOSEPAREN.ordinal());
					map.put("{", SType.OPENBRACE.ordinal());
					map.put("}", SType.CLOSEBRACE.ordinal());
				}));
				// 7 = EOF
				list.add(makemap(map -> {}));
			}),
			new int[] {
				SType.INTLIT.ordinal(),
				SType.DOUBLELIT.ordinal(),
				SType.STRINGLIT.ordinal(),
				SType.CHARLIT.ordinal(),
				-1, // Should never reach this
				SType.IDENTIFIER.ordinal(),
				-1,
				SType.EOF.ordinal()
			}
	);

	/**
	 * @param universe
	 * @param lexerStateToToken
	 * @param defaultTokenForState
	 */
	public TokenFactory(SymbolUniverse universe,
			ArrayList<HashMap<String, Integer>> tokenTypeToSymbol,
			int[] defaultSymbolForTokenType) {
		this.universe = universe;
		this.tokenTypeToSymbol = tokenTypeToSymbol;
		this.defaultSymbolForTokenType = defaultSymbolForTokenType;
	}

	public Token makeToken(int tokenType, String text, int line, int col) {
		HashMap<String, Integer> stateMap = tokenTypeToSymbol.get(tokenType);
		Integer symbolType = stateMap.get(text);
		if (symbolType == null) {
			symbolType = defaultSymbolForTokenType[tokenType];
		}
		assert symbolType != null: "Text not valid";
		return new Token(symbolType, text, line, col);
	}
}
