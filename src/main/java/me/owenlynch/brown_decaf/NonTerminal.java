package me.owenlynch.brown_decaf;

import java.util.BitSet;
import DataDSL.*;

class NonTerminal extends Symbol {
	private int type;
	private Symbol[] children;
	public static final BitSet nonterminals = makebitset(set -> {
		set.set(SType.START);
		set.set(SType.CLASSLIST);
		set.set(SType.CLASSDEF);
		set.set(SType.SUPER);
		set.set(SType.TYPE);
		set.set(SType.MODIFIER);
		set.set(SType.MODIFIERLIST);
		set.set(SType.FIELD);
		set.set(SType.METHOD);
		set.set(SType.CTOR);
		set.set(SType.MEMBERLIST);
		set.set(SType.FORMALARGS);
		set.set(SType.FORMALARGLIST);
		set.set(SType.FORMALARG);
		set.set(SType.VARDECID);
		set.set(SType.VARDEC);
		set.set(SType.BLOCK);
		set.set(SType.STAT);
		set.set(SType.STATLIST);
		set.set(SType.BINARYOP);
		set.set(SType.NEWARRAYEXPR);
		set.set(SType.ACTUALARGS);
		set.set(SType.EXPRLIST);
		set.set(SType.LITERAL);
		set.set(SType.DIMENSION);
		set.set(SType.FIELDEXPR);
		set.set(SType.ARRAYEXPR);
		set.set(SType.NONNEWARRAYEXPR);
		set.set(SType.PRIMARY);
		set.set(SType.EXPRESSION);
	}, SType.k_TYPES);

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
