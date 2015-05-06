package me.owenlynch.brown_decaf;

class SLRActionShift extends SLRAction {
	public final int state;

	public SLRActionShift(int state) {
		this.state = state;
	}

	public int getType() {
		return SLRAction.SHIFT;
	}
}
