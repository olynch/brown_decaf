package me.owenlynch.brown_decaf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.function.Consumer;

class DataDSL {
	public static <K, V> HashMap<K, V> makemap(Consumer<HashMap<K, V>> generator) {
		HashMap<K, V> map = new HashMap<>();
		generator.accept(map);
		return map;
	}

	public static <E> ArrayList<E> makelist(Consumer<ArrayList<E>> generator) {
		ArrayList<E> list = new ArrayList<>();
		generator.accept(list);
		return list;
	}

	public static <E> HashSet<E> makeset(Consumer<HashSet<E>> generator) {
		HashSet<E> set = new HashSet<>();
		generator.accept(set);
		return set;
	}

	public static BitSetIterable makebitset(Consumer<BitSetIterable> generator, int length) {
		BitSetIterable set = new BitSetIterable(length);
		generator.accept(set);
		return set;
	}

	public static BitSetIterable makebitset(Consumer<BitSetIterable> generator) {
		BitSetIterable set = new BitSetIterable();
		generator.accept(set);
		return set;
	}
}
