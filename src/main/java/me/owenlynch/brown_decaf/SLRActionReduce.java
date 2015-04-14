package me.owenlynch.brown_decaf;

class SLRActionReduce<T> implements SLRAction {
	public final Production<T> production;
	
	public SLRActionReduce(Production<T> production) {
		this.production = production;
	}

	public int getType() {
		return SLRAction.REDUCE;
	}
}
