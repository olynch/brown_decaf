package me.owenlynch.brown_decaf;

class SType {
	// Literals
	-1
	public static int NULL = 0;
	public static int BOOLEANLIT = 1;
	public static int INTLIT = 2;
	public static int CHARLIT = 3;
	public static int STRINGLIT = 4;
	public static int DOUBLELIT = 5;
	public static int IDENTIFIER = 6;
	
	//Keywords
	public static int PRIMITIVETYPE = 7;
	public static int MODIFIER = 8;
	public static int WHILE = 9;
	public static int RETURN = 10;
	public static int CONTINUE = 11;
	public static int NEW = 12;
	public static int BREAK = 13;
	public static int CLASS = 14;
	public static int ELSE = 15;
	public static int EXTENDS = 16;
	public static int FOR = 17;
	public static int IF = 18;
	public static int THIS = 19;
	public static int VOID = 20;
	public static int BREAK = 21;
	public static int SUPER = 22;
	
	// Operators
	public static int EQUALS = 23;
	public static int PIPEPIPE = 24;
	public static int AMPAMP = 25;
	public static int EQUALSEQUALS = 26;
	public static int BANGEQUALS = 27;
	public static int LT = 28;
	public static int GT = 29;
	public static int LTEQUALS = 30;
	public static int GTEQUALS = 31;
	public static int PLUS = 32;
	public static int MINUS = 33;
	public static int STAR = 34;
	public static int SLASH = 35;
	public static int PERCENT = 36;
	
	// Punctuation
	public static int COMMA = 37;
	public static int SEMICOLON = 38;
	public static int PERIOD = 39;
	public static int OPENBRACKET = 40;
	public static int CLOSEBRACKET = 41;
	public static int OPENPAREN = 42;
	public static int CLOSEPAREN = 43;
	public static int OPENBRACE = 44;
	public static int CLOSEBRACE = 45;

	// NonTerminals
	public static int START = 46;
	public static int CLASSLIST = 47;
	public static int CLASSDEF = 48;
	public static int SUPER = 49;
	public static int TYPE = 50;
	public static int MODIFIER = 51;
	public static int MODIFIERLIST = 52;
	public static int FIELD = 53;
	public static int METHOD = 54;
	public static int CTOR = 55;
	public static int MEMBERLIST = 56;
	public static int FORMALARGS = 57;
	public static int FORMALARGLIST = 58;
	public static int FORMALARG = 59;
	public static int VARDECID = 60;
	public static int VARDEC = 61;
	public static int BLOCK = 62;
	public static int STAT = 63;
	public static int STATLIST = 64;
	public static int BINARYOP = 65;
	public static int NEWARRAYEXPR = 66;
	public static int ACTUALARGS = 67;
	public static int EXPRLIST = 68;
	public static int LITERAL = 69;
	public static int DIMENSION = 70;
	public static int FIELDEXPR = 71;
	public static int ARRAYEXPR = 72;
	public static int NONNEWARRAYEXPR = 73;
	public static int PRIMARY = 74;
	public static int EXPRESSION = 75;
}
