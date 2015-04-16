package me.owenlynch.brown_decaf;

class SLRActionError implements SLRAction {
	public SLRActionError() {}

	public int getType() {
		return SLRAction.ERROR;
	}
}
