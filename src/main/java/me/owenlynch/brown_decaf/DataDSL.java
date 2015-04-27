package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.BitSet;

class DataDSL {
	public <K, V> HashMap<K, V> makemap(Function<HashMap<K, V>, void> generator) {
		HashMap<K, V> map = new HashMap<>();
		generator.call(map);
		return map;
	}

	public <E> ArrayList<E> makelist(Function<ArrayList<E>, void> generator) {
		ArrayList<E> list = new ArrayList<>();
		generator.call(list);
		return list;
	}

	public <E> HashSet<E> makeset(Function<HashSet<E>, void> generator) {
		HashSet<E> set = new HashSet<>();
		generator.call(set);
		return set;
	}

	public BitSet makebitset(Function<BitSet, void> generator, int length) {
		BitSet set = new BitSet(length);
		generator.call(set);
		return set;
	}
}
