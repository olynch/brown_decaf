package me.owenlynch.brown_decaf;

class BitSetIterable extends java.util.BitSet implements java.lang.Iterable<Integer> {
	static final long serialVersionUID = 1;

	public BitSetIterable(int nbits) {
		super(nbits);
	}

	public BitSetIterable() {
		super();
	}

	public BitSetIterator iterator() {
		return new BitSetIterator(this);
	}
}
