package me.owenlynch.brown_decaf;

class OperatorToken extends Token {
	public OperatorToken(String op, int line, int col) {
		super(OPERATOR, line, col);
		switch (op) {
			case "=":
				type = TName.EQUALS;
			case "||":
				type = TName.PIPEPIPE;
			case "&&":
				type = TName.AMPAMP;
			case "==":
				type = TName.EQUALSEQUALS;
			case "!=":
				type = TName.BANGEQUALS;
			case "<":
				type = TName.LT;
			case ">":
				type = TName.GT;
			case "<=":
				type = TName.LTEQUALS;
			case ">=":
				type = TName.GTEQUALS;
			case "+":
				type = TName.PLUS;
			case "-":
				type = TName.MINUS;
			case "*":
				type = TName.STAR;
			case "/":
				type = TName.SLASH;
			case "%":
				type = TName.PERCENT;
		}
	}


	public void setVal(Token other) {
		value = other.value;
	}

	public String toString() {
		return "Operator(" + type.toString() + ", " + line + ":" + column + ")";
	}
}
