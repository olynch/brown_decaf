* [x] Find unused classes and classes that could be merged
* [ ] Change use of enums to use of ints, change `HashMap<SType, ...>` and `HashSet<Stype>` to ArrayList<...> and BitSet
		* Reasoning: This will make it easier to test, less brittle and more efficient
* [ ] put null pointer checking in where needed 
		* I think there will be a lot of situations in which accessing ArrayLists or Maps will return null pointers because we're using a nonterminal for a map of terminals or vice versa, we need to actually do something in that situation
* [ ] Think about error handling
* [ ] Get everything to compile
* [ ] Write tests
* [ ] Pass tests
