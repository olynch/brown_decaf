package me.owenlynch.brown_decaf;

class SymbolUniverse {
	private final BitSetIterable terminals;
	private final BitSetIterable nonTerminals;
	private final BitSetIterable allSymbols;
	private final int size;

	public static SymbolUniverse JavaSymbols = new SymbolUniverse(
			DataDSL.makebitset(set -> {
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
				set.set(SType.EOF.ordinal());
			}),
			DataDSL.makebitset(set -> {
			}));

	public SymbolUniverse(BitSetIterable terminals, BitSetIterable nonTerminals) {
		this.terminals = terminals;
		this.nonTerminals = nonTerminals;
		this.allSymbols = new BitSetIterable();
		allSymbols.or(terminals);
		allSymbols.or(nonTerminals);
		int i = 0;
		for (Integer x : allSymbols) {
			i = x;
		}
		this.size = i + 1; // the last set bit + 1
	}

	public int size() {
		return size;
	}

	public boolean isTerminal(int s) {
		return terminals.get(s);
	}

	public boolean isNonTerminal(int s) {
		return nonTerminals.get(s);
	}

	public boolean isSymbol(int s) {
		return allSymbols.get(s);
	}

	public BitSetIterable nonTerminals() {
		return nonTerminals;
	}

	public BitSetIterable terminals() {
		return terminals;
	}

	public BitSetIterable symbols() {
		return allSymbols;
	}
}
