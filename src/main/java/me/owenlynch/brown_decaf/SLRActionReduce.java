package me.owenlynch.brown_decaf;

class SLRActionReduce implements SLRAction {
	public final Production production;
	
	public SLRActionReduce(Production production) {
		this.production = production;
	}

	public int getType() {
		return SLRAction.REDUCE;
	}
}
