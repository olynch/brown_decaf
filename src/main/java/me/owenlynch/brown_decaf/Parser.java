package me.owenlynch.brown_decaf

import java.util.HashMap;
import DataDSL.*;

class Parser {
	// This is the lookup table for productions
	// The first dimension is by type of NonTerminal
	// The second dimension is by type of Token (or Terminal)

	private static ArrayList<Production> productions = makelist(list -> {
			list.push(new Production(SType.START, SType.CLASSLIST, SType.EOF));
			list.push(new Production(SType.CLASSLIST, SType.CLASS, SType.CLASSLIST));
			list.push(new Production(SType.CLASSLIST, SType.CLASS));
			list.push(new Production(SType.CLASS, SType.CLASS, SType.IDENTIFIER, SType.SUPER, SType.OPENBRACE, SType.MEMBERLIST, SType.CLOSEBRACE));
			list.push(new Production(SType.SUPER, SType.EXTENDS, SType.IDENTIFIER));
			list.push(new Production(SType.SUPER));
			list.push(new Production(SType.MEMBERLIST, SType.MEMBER, SType.MEMBERLIST));
			list.push(new Production(SType.MEMBERLIST));
			list.push(new Production(SType.MEMBER, SType.FIELD));
			list.push(new Production(SType.MEMBER, SType.METHOD));
			list.push(new Production(SType.MEMBER, SType.CTOR));
			list.push(new Production(SType.FIELD, SType.MODIFIERLIST, SType.TYPE, SType.VARDECLIST, SType.SEMICOLON));
			list.push(new Production(SType.MODIFIERLIST, SType.MODIFIER, SType.MODIFIERLIST));
			list.push(new Production(SType.MODIFIERLIST));
			list.push(new Production(SType.METHOD, SType.MODIFIERLIST, SType.TYPE, SType.IDENTIFIER, SType.FORMALARGS, SType.BLOCK));
			list.push(new Production(SType.CTOR, SType.MODIFIERLIST, SType.IDENTIFIER, SType.FORMALARGS, SType.BLOCK));
			list.push(new Production(SType.FORMALARGS, SType.OPENPAREN, SType.FORMALARGLIST, SType.CLOSEPAREN));
			list.push(new Production(SType.FORMALARGLIST));
			list.push(new Production(SType.FORMALARGLIST, SType.FORMALARG, SType.COMMA, SType.FORMALARGLIST));
			list.push(new Production(SType.FORMALARG, SType.TYPE, SType.VARDECID));
			list.push(new Production(SType.TYPE, SType.PRIMITIVETYPE));
			list.push(new Production(SType.TYPE, SType.IDENTIFIER));
			list.push(new Production(SType.TYPE, SType.PRIMITIVETYPE, (NTName.ARRAYSTUFF)));
			list.push(new Production(SType.TYPE, SType.IDENTIFIER, SType.ARRAYSTUFF));
			list.push(new Production(SType.ARRAYSTUFF, SType.OPENBRACKET, SType.CLOSEBRACKET, SType.ARRAYSTUFF));
			list.push(new Production(SType.ARRAYSTUFF));
			list.push(new Production(SType.VARDECLIST, SType.VARDEC, SType.COMMA, SType.VARDECLIST));
			list.push(new Production(SType.VARDECLIST, SType.VARDEC));
			list.push(new Production(SType.VARDEC, SType.VARDECID));
			list.push(new Production(SType.VARDEC, SType.VARDECID, SType.EQUALS, SType.EXPRESSION));
			list.push(new Production(SType.VARDECID, SType.IDENTIFIER));
			list.push(new Production(SType.VARDECID, SType.IDENTIFIER, SType.ARRAYSTUFF));
			list.push(new Production(SType.BLOCK, SType.OPENBRACE, SType.STATLIST, SType.CLOSEBRACE));
			list.push(new Production(SType.STATLIST, SType.STAT, SType.STATLIST));
			list.push(new Production(SType.STATLIST));
			list.push(new Production(SType.STAT, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.TYPE, SType.VARDECLIST, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.UNMATCHED));
			list.push(new Production(SType.STAT, SType.IF, SType.OPENPAREN, SType.EXPRESSION, SType.CLOSEPAREN, SType.STAT, SType.ELSE, SType.STAT));
			list.push(new Production(SType.STAT, SType.EXPRESSION, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.WHILE, SType.OPENPAREN, SType.EXPRESSION, SType.CLOSEPAREN, SType.STAT));
			list.push(new Production(SType.STAT, SType.RETURN, SType.EXPRESSION, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.RETURN, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.CONTINUE, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.BREAK, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.SUPER, SType.ACTUALARGS, SType.SEMICOLON));
			list.push(new Production(SType.STAT, SType.BLOCK));
			list.push(new Production(SType.UNMATCHED, SType.IF, SType.OPENPAREN, SType.EXPRESSION, SType.CLOSEPAREN, SType.STAT, SType.ELSE, SType.UNMATCHED));
			list.push(new Production(SType.UNMATCHED, SType.IF, SType.OPENPAREN, SType.EXPRESSION, SType.CLOSEPAREN, SType.STAT));
			list.push(new Production(SType.EXPRESSION, SType.EXPRESSION, SType.BINARYOP, SType.EXPRESSION));
			list.push(new Production(SType.EXPRESSION, SType.UNARYOP, SType.EXPRESSION));
			list.push(new Production(SType.EXPRESSION, SType.PRIMARY));
			list.push(new Production(SType.BINARYOP, SType.EQUALS));
			list.push(new Production(SType.BINARYOP, SType.PIPEPIPE));
			list.push(new Production(SType.BINARYOP, SType.AMPAMP));
			list.push(new Production(SType.BINARYOP, SType.EQUALSEQUALS));
			list.push(new Production(SType.BINARYOP, SType.BANGEQUALS));
			list.push(new Production(SType.BINARYOP, SType.LT));
			list.push(new Production(SType.BINARYOP, SType.GT));
			list.push(new Production(SType.BINARYOP, SType.LTEQUALS));
			list.push(new Production(SType.BINARYOP, SType.GTEQUALS));
			list.push(new Production(SType.BINARYOP, SType.PLUS));
			list.push(new Production(SType.BINARYOP, SType.MINUS));
			list.push(new Production(SType.BINARYOP, SType.STAR));
			list.push(new Production(SType.BINARYOP, SType.SLASH));
			list.push(new Production(SType.BINARYOP, SType.PERCENT));
			list.push(new Production(SType.UNARYOP, SType.PLUS));
			list.push(new Production(SType.UNARYOP, SType.MINUS));
			list.push(new Production(SType.UNARYOP, SType.BANG));
			list.push(new Production(SType.PRIMARY, SType.NEWARRAYEXPR));
			list.push(new Production(SType.PRIMARY, SType.NONNEWARRAYEXPR));
			list.push(new Production(SType.PRIMARY, SType.IDENTIFIER));
			list.push(new Production(SType.NEWARRAYEXPR, SType.NEW, SType.IDENTIFIER, SType.DIMENSIONLIST));
			list.push(new Production(SType.DIMENSIONLIST, SType.DIMENSION, SType.DIMENSIONLIST));
			list.push(new Production(SType.DIMENSION, SType.DIMENSION));
			list.push(new Production(SType.NEWARRAYEXPR, SType.NEW, SType.PRIMITIVETYPE, SType.DIMENSIONLIST));
			list.push(new Production(SType.DIMENSION, SType.OPENBRACKET, SType.EXPRESSION, SType.CLOSEBRACKET));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.LITERAL));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.THIS));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.OPENPAREN, SType.EXPRESSION, SType.CLOSEPAREN));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.NEW, SType.IDENTIFIER, SType.ACTUALARGS));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.IDENTIFIER, SType.ACTUALARGS));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.PRIMARY, SType.PERIOD, SType.IDENTIFIER, SType.ACTUALARGS));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.SUPER, SType.PERIOD, SType.IDENTIFIER, SType.ACTUALARGS));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.ARRAYEXPR));
			list.push(new Production(SType.NONNEWARRAYEXPR, SType.FIELDEXPR));
			list.push(new Production(SType.FIELDEXPR, SType.PRIMARY, SType.PERIOD, SType.IDENTIFIER));
			list.push(new Production(SType.FIELDEXPR, SType.SUPER, SType.PERIOD, SType.IDENTIFIER));
			list.push(new Production(SType.ARRAYEXPR, SType.IDENTIFIER, SType.DIMENSION));
			list.push(new Production(SType.ARRAYEXPR, SType.NONNEWARRAYEXPR, SType.DIMENSION));
			list.push(new Production(SType.LITERAL, SType.NULL));
			list.push(new Production(SType.LITERAL, SType.BOOLEANLIT));
			list.push(new Production(SType.LITERAL, SType.INTLIT));
			list.push(new Production(SType.LITERAL, SType.CHARLIT));
			list.push(new Production(SType.LITERAL, SType.STRINGLIT));
			list.push(new Production(SType.LITERAL, SType.DOUBLELIT));
			list.push(new Production(SType.ACTUALARGS, SType.OPENPAREN, SType.EXPRLIST, SType.CLOSEPAREN));
			list.push(new Production(SType.EXPRLIST));
			list.push(new Production(SType.EXPRLIST, SType.EXPRESSION, SType.COMMA, SType.EXPRLIST));
	});

	private static SLRDFA slr_table = new SLRDFA(productions);

	public static NonTerminal parse(Deque<Token> input) throws ParseError {
		// This stack gets both Terminals and NonTerminals pushed onto it
		// Whenever a NonTerminal gets replaced by a production, the
		// right hand side of the production gets pushed onto the stack,
		// but also become children of the Terminal of the left hand side
		// of the production.
		Deque<Symbol> stack = new Deque<>();

		// The begining NonTerminal
		NonTerminal start = new StartNode();
		stack.push(start);
		while (!stack.empty()) {
			Symbol top = stack.pop();
			Token next = input.top();
			if (top.isTerminal()) {
				// Make sure that the terminal on the top of the input matches
				// what we expect on the top of the stack. If it does,
				// get the internal value of the token on the
				// top of the input stack, then pop it.
				// It may be the case that we don't care about whatever is on the
				// top of the stack: we just need to match it because it is
				// a syntactic detail and the input needs to get popped.
				// If we do care about the value of this terminal
				// it was already added to the syntax tree when
				// it was placed on the stack, so we don't have to worry about
				// popping it.
				if (! top.matches(next)) {
					throw ParseError("Expected: " + top.toString() + " Found: " + next.toString());
				}
				top.setVal(next);
				input.pop();
			}
			else {
				// Find the production corresponding to the type of the
				// NonTerminal and the type of the next token
				Production cur = table.get(top.getType())
					.get(next.type);
				
				if (cur == null) {
					throw ParseError("No Production found on Token: " + 
							next.toString());
				}

				// Symbols are instantiated to default values
				// Their values will be updated when they themselves are
				// popped
				Symbol[] newSyms = cur.getLHS();
				// Add the new symbols to the AST
				cur.setChildren(top, newSyms);
				// Push them back onto the stack
				for (Symbol s : newSyms ) {
					stack.push(s);
				}
			}
		}
		return start;
	}

	class SLREntry {
		public final Symbol sym;
		public final int state;

		public SLREntry(Symbol sym, int state) {
			this.sym = sym;
			this.state = state;
		}
	}

	public static NonTerminal slr_parse(Deque<Symbol> input) {
		Deque<SLREntry> stack = new Deque<>();
		stack.push(table.startState());
		Symbol next;
		SLRAction action;
		while (true) {
			next = input.pop();
			action = table.get();
			SLRState newState = state.createFrom(next);
			if (newState == null) {
				// error
			} else if (newState.isShift()) {
				stack.push(newState);
			} else if (newState.isReduce()) {
				ArrayList<SLREntry> children = new ArrayList<>();
				for (int i = 0; i < newState.numLHS(); i++) {
					children.push(stack.pop());
				} 
				stack.push(newState.createNonTerminal(children));
			} else if (newState.isAccepting()) {
				return stack.top().sym;
			}
		}
	}
}
