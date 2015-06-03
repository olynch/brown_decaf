package me.owenlynch.brown_decaf;

class SLRActionGo implements SLRAction {
	public final int state;

	public SLRActionGo(int state) {
		this.state = state;
	}

	public int getType() {
		return SLRAction.GO;
	}
}
