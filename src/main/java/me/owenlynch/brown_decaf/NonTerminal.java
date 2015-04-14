package me.owenlynch.brown_decaf;

import java.util.HashSet;

class NonTerminal extends Symbol {
	private SType type;
	private Symbol[] children;
	public static final HashSet<SType> STypes = makeset(set -> {
		set.add(SType.START);
		set.add(SType.CLASSLIST);
		set.add(SType.CLASSDEF);
		set.add(SType.SUPER);
		set.add(SType.TYPE);
		set.add(SType.MODIFIER);
		set.add(SType.MODIFIERLIST);
		set.add(SType.FIELD);
		set.add(SType.METHOD);
		set.add(SType.CTOR);
		set.add(SType.MEMBERLIST);
		set.add(SType.FORMALARGS);
		set.add(SType.FORMALARGLIST);
		set.add(SType.FORMALARG);
		set.add(SType.VARDECID);
		set.add(SType.VARDEC);
		set.add(SType.BLOCK);
		set.add(SType.STAT);
		set.add(SType.STATLIST);
		set.add(SType.BINARYOP);
		set.add(SType.NEWARRAYEXPR);
		set.add(SType.ACTUALARGS);
		set.add(SType.EXPRLIST);
		set.add(SType.LITERAL);
		set.add(SType.DIMENSION);
		set.add(SType.FIELDEXPR);
		set.add(SType.ARRAYEXPR);
		set.add(SType.NONNEWARRAYEXPR);
		set.add(SType.PRIMARY);
		set.add(SType.EXPRESSION);
	});

	public NonTerminal(SType type) {
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
