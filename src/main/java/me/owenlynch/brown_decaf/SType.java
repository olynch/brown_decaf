package me.owenlynch.brown_decaf;

public enum SType {
	// Literals
	NULL,
	BOOLEANLIT,
	INTLIT,
	CHARLIT,
	STRINGLIT,
	DOUBLELIT,
	IDENTIFIER,
	
	//Keywords
	PRIMITIVETYPE,
	MODIFIER,
	WHILE,
	RETURN,
	CONTINUE,
	NEW,
	BREAK,
	CLASS,
	ELSE,
	EXTENDS,
	FOR,
	IF,
	THIS,
	VOID,
	SUPER,
	
	// Operators
	EQUALS,
	PIPEPIPE,
	AMPAMP,
	EQUALSEQUALS,
	BANGEQUALS,
	BANG,
	LT,
	GT,
	LTEQUALS,
	GTEQUALS,
	PLUS,
	MINUS,
	STAR,
	SLASH,
	PERCENT,
	
	// Punctuation
	COMMA,
	SEMICOLON,
	PERIOD,
	OPENBRACKET,
	CLOSEBRACKET,
	OPENPAREN,
	CLOSEPAREN,
	OPENBRACE,
	CLOSEBRACE,

	//EOF
	EOF,

	// NonTerminals
	START,
	CLASSLIST,
	CLASSDEF,
	SUPERDEC,
	TYPE,
	ARRAYSTUFF,
	MODIFIERLIST,
	FIELD,
	METHOD,
	CTOR,
	MEMBERLIST,
	MEMBER,
	FORMALARGS,
	FORMALARGLIST,
	FORMALARG,
	VARDECLIST,
	VARDECID,
	VARDEC,
	BLOCK,
	STAT,
	STATLIST,
	UNMATCHED,
	UNARYOP,
	BINARYOP,
	NEWARRAYEXPR,
	ACTUALARGS,
	EXPRLIST,
	LITERAL,
	DIMENSION,
	DIMENSIONLIST,
	FIELDEXPR,
	ARRAYEXPR,
	NONNEWARRAYEXPR,
	PRIMARY,
	EXPRESSION;

	public static final SType[] lookup = SType.values();
	public static final int size = lookup.length;
}
