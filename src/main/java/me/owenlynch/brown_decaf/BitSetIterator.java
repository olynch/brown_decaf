package me.owenlynch.brown_decaf;

import java.util.BitSet;

class BitSetIterator implements java.util.PrimitiveIterator.OfInt {
	private final BitSet set;
	private int pos;

	public BitSetIterator(BitSet set) {
		this.set = set;
		this.pos = 0;
	}

	public int nextInt() {
		pos = set.nextSetBit(pos);
		return pos++; //return pos and then increment it
	}

	public boolean hasNext() {
		return set.nextSetBit(pos) != -1;
	}
}
