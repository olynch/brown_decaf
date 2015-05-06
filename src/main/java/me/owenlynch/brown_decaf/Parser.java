package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.ArrayList;
import me.owenlynch.brown_decaf.DataDSL.*;

class Parser {
	// This is the lookup table for productions
	// The first dimension is by type of NonTerminal
	// The second dimension is by type of Token (or Terminal)

	private static ArrayList<Production> productions = DataDSL.makelist(list -> {
			list.add(new Production(SType.START.ordinal(), SType.CLASSLIST.ordinal(), SType.EOF.ordinal()));
			list.add(new Production(SType.CLASSLIST.ordinal(), SType.CLASS.ordinal(), SType.CLASSLIST.ordinal()));
			list.add(new Production(SType.CLASSLIST.ordinal(), SType.CLASS.ordinal()));
			list.add(new Production(SType.CLASS.ordinal(), SType.CLASS.ordinal(), SType.IDENTIFIER.ordinal(), SType.SUPERDEC.ordinal(), SType.OPENBRACE.ordinal(), SType.MEMBERLIST.ordinal(), SType.CLOSEBRACE.ordinal()));
			list.add(new Production(SType.SUPERDEC.ordinal(), SType.EXTENDS.ordinal(), SType.IDENTIFIER.ordinal()));
			list.add(new Production(SType.SUPERDEC.ordinal()));
			list.add(new Production(SType.MEMBERLIST.ordinal(), SType.MEMBER.ordinal(), SType.MEMBERLIST.ordinal()));
			list.add(new Production(SType.MEMBERLIST.ordinal()));
			list.add(new Production(SType.MEMBER.ordinal(), SType.FIELD.ordinal()));
			list.add(new Production(SType.MEMBER.ordinal(), SType.METHOD.ordinal()));
			list.add(new Production(SType.MEMBER.ordinal(), SType.CTOR.ordinal()));
			list.add(new Production(SType.FIELD.ordinal(), SType.MODIFIERLIST.ordinal(), SType.TYPE.ordinal(), SType.VARDECLIST.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.MODIFIERLIST.ordinal(), SType.MODIFIER.ordinal(), SType.MODIFIERLIST.ordinal()));
			list.add(new Production(SType.MODIFIERLIST.ordinal()));
			list.add(new Production(SType.METHOD.ordinal(), SType.MODIFIERLIST.ordinal(), SType.TYPE.ordinal(), SType.IDENTIFIER.ordinal(), SType.FORMALARGS.ordinal(), SType.BLOCK.ordinal()));
			list.add(new Production(SType.CTOR.ordinal(), SType.MODIFIERLIST.ordinal(), SType.IDENTIFIER.ordinal(), SType.FORMALARGS.ordinal(), SType.BLOCK.ordinal()));
			list.add(new Production(SType.FORMALARGS.ordinal(), SType.OPENPAREN.ordinal(), SType.FORMALARGLIST.ordinal(), SType.CLOSEPAREN.ordinal()));
			list.add(new Production(SType.FORMALARGLIST.ordinal()));
			list.add(new Production(SType.FORMALARGLIST.ordinal(), SType.FORMALARG.ordinal(), SType.COMMA.ordinal(), SType.FORMALARGLIST.ordinal()));
			list.add(new Production(SType.FORMALARG.ordinal(), SType.TYPE.ordinal(), SType.VARDECID.ordinal()));
			list.add(new Production(SType.TYPE.ordinal(), SType.PRIMITIVETYPE.ordinal()));
			list.add(new Production(SType.TYPE.ordinal(), SType.IDENTIFIER.ordinal()));
			list.add(new Production(SType.TYPE.ordinal(), SType.PRIMITIVETYPE.ordinal(), SType.ARRAYSTUFF.ordinal()));
			list.add(new Production(SType.TYPE.ordinal(), SType.IDENTIFIER.ordinal(), SType.ARRAYSTUFF.ordinal()));
			list.add(new Production(SType.ARRAYSTUFF.ordinal(), SType.OPENBRACKET.ordinal(), SType.CLOSEBRACKET.ordinal(), SType.ARRAYSTUFF.ordinal()));
			list.add(new Production(SType.ARRAYSTUFF.ordinal()));
			list.add(new Production(SType.VARDECLIST.ordinal(), SType.VARDEC.ordinal(), SType.COMMA.ordinal(), SType.VARDECLIST.ordinal()));
			list.add(new Production(SType.VARDECLIST.ordinal(), SType.VARDEC.ordinal()));
			list.add(new Production(SType.VARDEC.ordinal(), SType.VARDECID.ordinal()));
			list.add(new Production(SType.VARDEC.ordinal(), SType.VARDECID.ordinal(), SType.EQUALS.ordinal(), SType.EXPRESSION.ordinal()));
			list.add(new Production(SType.VARDECID.ordinal(), SType.IDENTIFIER.ordinal()));
			list.add(new Production(SType.VARDECID.ordinal(), SType.IDENTIFIER.ordinal(), SType.ARRAYSTUFF.ordinal()));
			list.add(new Production(SType.BLOCK.ordinal(), SType.OPENBRACE.ordinal(), SType.STATLIST.ordinal(), SType.CLOSEBRACE.ordinal()));
			list.add(new Production(SType.STATLIST.ordinal(), SType.STAT.ordinal(), SType.STATLIST.ordinal()));
			list.add(new Production(SType.STATLIST.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.TYPE.ordinal(), SType.VARDECLIST.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.UNMATCHED.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.IF.ordinal(), SType.OPENPAREN.ordinal(), SType.EXPRESSION.ordinal(), SType.CLOSEPAREN.ordinal(), SType.STAT.ordinal(), SType.ELSE.ordinal(), SType.STAT.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.EXPRESSION.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.WHILE.ordinal(), SType.OPENPAREN.ordinal(), SType.EXPRESSION.ordinal(), SType.CLOSEPAREN.ordinal(), SType.STAT.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.RETURN.ordinal(), SType.EXPRESSION.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.RETURN.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.CONTINUE.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.BREAK.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.SUPER.ordinal(), SType.ACTUALARGS.ordinal(), SType.SEMICOLON.ordinal()));
			list.add(new Production(SType.STAT.ordinal(), SType.BLOCK.ordinal()));
			list.add(new Production(SType.UNMATCHED.ordinal(), SType.IF.ordinal(), SType.OPENPAREN.ordinal(), SType.EXPRESSION.ordinal(), SType.CLOSEPAREN.ordinal(), SType.STAT.ordinal(), SType.ELSE.ordinal(), SType.UNMATCHED.ordinal()));
			list.add(new Production(SType.UNMATCHED.ordinal(), SType.IF.ordinal(), SType.OPENPAREN.ordinal(), SType.EXPRESSION.ordinal(), SType.CLOSEPAREN.ordinal(), SType.STAT.ordinal()));
			list.add(new Production(SType.EXPRESSION.ordinal(), SType.EXPRESSION.ordinal(), SType.BINARYOP.ordinal(), SType.EXPRESSION.ordinal()));
			list.add(new Production(SType.EXPRESSION.ordinal(), SType.UNARYOP.ordinal(), SType.EXPRESSION.ordinal()));
			list.add(new Production(SType.EXPRESSION.ordinal(), SType.PRIMARY.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.EQUALS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.PIPEPIPE.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.AMPAMP.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.EQUALSEQUALS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.BANGEQUALS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.LT.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.GT.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.LTEQUALS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.GTEQUALS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.PLUS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.MINUS.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.STAR.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.SLASH.ordinal()));
			list.add(new Production(SType.BINARYOP.ordinal(), SType.PERCENT.ordinal()));
			list.add(new Production(SType.UNARYOP.ordinal(), SType.PLUS.ordinal()));
			list.add(new Production(SType.UNARYOP.ordinal(), SType.MINUS.ordinal()));
			list.add(new Production(SType.UNARYOP.ordinal(), SType.BANG.ordinal()));
			list.add(new Production(SType.PRIMARY.ordinal(), SType.NEWARRAYEXPR.ordinal()));
			list.add(new Production(SType.PRIMARY.ordinal(), SType.NONNEWARRAYEXPR.ordinal()));
			list.add(new Production(SType.PRIMARY.ordinal(), SType.IDENTIFIER.ordinal()));
			list.add(new Production(SType.NEWARRAYEXPR.ordinal(), SType.NEW.ordinal(), SType.IDENTIFIER.ordinal(), SType.DIMENSIONLIST.ordinal()));
			list.add(new Production(SType.DIMENSIONLIST.ordinal(), SType.DIMENSION.ordinal(), SType.DIMENSIONLIST.ordinal()));
			list.add(new Production(SType.DIMENSION.ordinal(), SType.DIMENSION.ordinal()));
			list.add(new Production(SType.NEWARRAYEXPR.ordinal(), SType.NEW.ordinal(), SType.PRIMITIVETYPE.ordinal(), SType.DIMENSIONLIST.ordinal()));
			list.add(new Production(SType.DIMENSION.ordinal(), SType.OPENBRACKET.ordinal(), SType.EXPRESSION.ordinal(), SType.CLOSEBRACKET.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.LITERAL.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.THIS.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.OPENPAREN.ordinal(), SType.EXPRESSION.ordinal(), SType.CLOSEPAREN.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.NEW.ordinal(), SType.IDENTIFIER.ordinal(), SType.ACTUALARGS.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.IDENTIFIER.ordinal(), SType.ACTUALARGS.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.PRIMARY.ordinal(), SType.PERIOD.ordinal(), SType.IDENTIFIER.ordinal(), SType.ACTUALARGS.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.SUPER.ordinal(), SType.PERIOD.ordinal(), SType.IDENTIFIER.ordinal(), SType.ACTUALARGS.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.ARRAYEXPR.ordinal()));
			list.add(new Production(SType.NONNEWARRAYEXPR.ordinal(), SType.FIELDEXPR.ordinal()));
			list.add(new Production(SType.FIELDEXPR.ordinal(), SType.PRIMARY.ordinal(), SType.PERIOD.ordinal(), SType.IDENTIFIER.ordinal()));
			list.add(new Production(SType.FIELDEXPR.ordinal(), SType.SUPER.ordinal(), SType.PERIOD.ordinal(), SType.IDENTIFIER.ordinal()));
			list.add(new Production(SType.ARRAYEXPR.ordinal(), SType.IDENTIFIER.ordinal(), SType.DIMENSION.ordinal()));
			list.add(new Production(SType.ARRAYEXPR.ordinal(), SType.NONNEWARRAYEXPR.ordinal(), SType.DIMENSION.ordinal()));
			list.add(new Production(SType.LITERAL.ordinal(), SType.NULL.ordinal()));
			list.add(new Production(SType.LITERAL.ordinal(), SType.BOOLEANLIT.ordinal()));
			list.add(new Production(SType.LITERAL.ordinal(), SType.INTLIT.ordinal()));
			list.add(new Production(SType.LITERAL.ordinal(), SType.CHARLIT.ordinal()));
			list.add(new Production(SType.LITERAL.ordinal(), SType.STRINGLIT.ordinal()));
			list.add(new Production(SType.LITERAL.ordinal(), SType.DOUBLELIT.ordinal()));
			list.add(new Production(SType.ACTUALARGS.ordinal(), SType.OPENPAREN.ordinal(), SType.EXPRLIST.ordinal(), SType.CLOSEPAREN.ordinal()));
			list.add(new Production(SType.EXPRLIST.ordinal()));
			list.add(new Production(SType.EXPRLIST.ordinal(), SType.EXPRESSION.ordinal(), SType.COMMA.ordinal(), SType.EXPRLIST.ordinal()));
	});

	private static SLRDFA slr_table = new SLRDFA(productions);
/*
 *
 *    public static NonTerminal parse(Deque<Token> input) throws ParseError {
 *        // This stack gets both Terminals and NonTerminals pushed onto it
 *        // Whenever a NonTerminal gets replaced by a production, the
 *        // right hand side of the production gets pushed onto the stack,
 *        // but also become children of the Terminal of the left hand side
 *        // of the production.
 *        Deque<Symbol> stack = new Deque<>();
 *
 *        // The begining NonTerminal
 *        NonTerminal start = new StartNode();
 *        stack.push(start);
 *        while (!stack.empty()) {
 *            Symbol top = stack.pop();
 *            Token next = input.top();
 *            if (top.isTerminal()) {
 *                // Make sure that the terminal on the top of the input matches
 *                // what we expect on the top of the stack. If it does,
 *                // get the internal value of the token on the
 *                // top of the input stack, then pop it.
 *                // It may be the case that we don't care about whatever is on the
 *                // top of the stack: we just need to match it because it is
 *                // a syntactic detail and the input needs to get popped.
 *                // If we do care about the value of this terminal
 *                // it was already added to the syntax tree when
 *                // it was placed on the stack, so we don't have to worry about
 *                // popping it.
 *                if (! top.matches(next)) {
 *                    throw ParseError("Expected: " + top.toString() + " Found: " + next.toString());
 *                }
 *                top.setVal(next);
 *                input.pop();
 *            }
 *            else {
 *                // Find the production corresponding to the type of the
 *                // NonTerminal and the type of the next token
 *                Production cur = table.get(top.getType())
 *                    .get(next.type);
 *                
 *                if (cur == null) {
 *                    throw ParseError("No Production found on Token: " + 
 *                            next.toString());
 *                }
 *
 *                // Symbols are instantiated to default values
 *                // Their values will be updated when they themselves are
 *                // popped
 *                Symbol[] newSyms = cur.getLHS();
 *                // Add the new symbols to the AST
 *                cur.setChildren(top, newSyms);
 *                // Push them back onto the stack
 *                for (Symbol s : newSyms ) {
 *                    stack.push(s);
 *                }
 *            }
 *        }
 *        return start;
 *    }
 */

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
		stack.push(slr_table.startState());
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
