package me.owenlynch.brown_decaf;

import java.util.BitSet;
import DataDSL.*;

class NonTerminal extends Symbol {
	private int type;
	private Symbol[] children;
	public static final BitSet nonterminals = makebitset(set -> {
		set.set(SType.START.ordinal());
		set.set(SType.CLASSLIST.ordinal());
		set.set(SType.CLASSDEF.ordinal());
		set.set(SType.SUPERDEC.ordinal());
		set.set(SType.TYPE.ordinal());
		set.set(SType.MODIFIERLIST.ordinal());
		set.set(SType.FIELD.ordinal());
		set.set(SType.METHOD.ordinal());
		set.set(SType.CTOR.ordinal());
		set.set(SType.MEMBERLIST.ordinal());
		set.set(SType.FORMALARGS.ordinal());
		set.set(SType.FORMALARGLIST.ordinal());
		set.set(SType.FORMALARG.ordinal());
		set.set(SType.VARDECID.ordinal());
		set.set(SType.VARDEC.ordinal());
		set.set(SType.BLOCK.ordinal());
		set.set(SType.STAT.ordinal());
		set.set(SType.STATLIST.ordinal());
		set.set(SType.BINARYOP.ordinal());
		set.set(SType.NEWARRAYEXPR.ordinal());
		set.set(SType.ACTUALARGS.ordinal());
		set.set(SType.EXPRLIST.ordinal());
		set.set(SType.LITERAL.ordinal());
		set.set(SType.DIMENSION.ordinal());
		set.set(SType.FIELDEXPR.ordinal());
		set.set(SType.ARRAYEXPR.ordinal());
		set.set(SType.NONNEWARRAYEXPR.ordinal());
		set.set(SType.PRIMARY.ordinal());
		set.set(SType.EXPRESSION.ordinal());
	}, SType.values());

	public static boolean isNonTerminal(int t) {
		return nonterminals.get(t);
	}

	public NonTerminal(int type) {
		this.type = type;
		this.children = null;
	}

	public void setChildren(Symbol[] children) {
		this.children = children;
	}

	public NonTerminal clone() {
		return new NonTerminal(type);
	}

	public boolean isTerminal() {
		return false;
	}
}
