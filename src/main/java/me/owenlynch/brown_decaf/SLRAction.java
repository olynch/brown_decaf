package me.owenlynch.brown_decaf;

interface SLRAction {
	public static final int SHIFT = 0;
	public static final int REDUCE = 1;
	public static final int ACCEPT = 2;
	public static final int ERROR = 3;
	public static final int GO = 4;
	public int getType();
}
