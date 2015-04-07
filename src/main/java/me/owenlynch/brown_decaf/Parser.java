package me.owenlynch.brown_decaf

import java.util.HashMap;
import DataDSL.*;

class Parser {
	// This is the lookup table for productions
	// The first dimension is by type of NonTerminal
	// The second dimension is by type of Token (or Terminal)
	private static HashMap<NTName, HashMap<TName, Production>> table = 
		// Use DSL to construct HashMap
		// Note: Check if the generic typing here actually works
		makemap(map -> {
			map.put(NTName.START, makemap(imap -> {

			}));
			map.put(NTName.CLASSLIST, makemap(imap -> {
				
			}));
			map.put(NTName.CLASS, makemap(imap -> {
				
			}));
			map.put(NTName.SUPER, makemap(imap -> {
				
			}));
			map.put(NTName.PRIMITIVETYPE, makemap(imap -> {
				
			}));
			map.put(NTName.TYPE, makemap(imap -> {
				
			}));
			map.put(NTName.MODIFIER, makemap(imap -> {
				
			}));
			map.put(NTName.MODIFIERLIST, makemap(imap -> {
				
			}));
			map.put(NTName.FIELD, makemap(imap -> {
				
			}));
			map.put(NTName.METHOD, makemap(imap -> {
				
			}));
			map.put(NTName.CTOR, makemap(imap -> {
				
			}));
			map.put(NTName.MEMBERLIST, makemap(imap -> {
				
			}));
			map.put(NTName.FORMALARGS, makemap(imap -> {
				
			}));
			map.put(NTName.FORMALARGLIST, makemap(imap -> {
				
			}));
			map.put(NTName.FORMALARG, makemap(imap -> {
				
			}));
			map.put(NTName.VARDECID, makemap(imap -> {
				
			}));
			map.put(NTName.VARDEC, makemap(imap -> {
				
			}));
			map.put(NTName.BLOCK, makemap(imap -> {
				
			}));
			map.put(NTName.STAT, makemap(imap -> {
				
			}));
			map.put(NTName.STATLIST, makemap(imap -> {
				
			}));
			map.put(NTName.BINARYOP, makemap(imap -> {
				
			}));
			map.put(NTName.NEWARRAYEXPR, makemap(imap -> {
				
			}));
			map.put(NTName.ACTUALARGS, makemap(imap -> {
				
			}));
			map.put(NTName.EXPRLIST, makemap(imap -> {
				
			}));
			map.put(NTName.LITERAL, makemap(imap -> {
				
			}));
			map.put(NTName.DIMENSION, makemap(imap -> {
				
			}));
			map.put(NTName.FIELDEXPR, makemap(imap -> {
				
			}));
			map.put(NTName.ARRAYEXPR, makemap(imap -> {
				
			}));
			map.put(NTName.NONNEWARRAYEXPR, makemap(imap -> {
				
			}));
			map.put(NTName.PRIMARY, makemap(imap -> {
				
			}));
			map.put(NTName.EXPRESSION, makemap(imap -> {
				
			}));
		});

	public static ASTNode parse(Deque<Token> input) throws ParseError {
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
}
