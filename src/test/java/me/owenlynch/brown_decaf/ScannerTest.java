package me.owenlynch.brown_decaf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class ScannerTest extends TestCase {
	public ScannerTest(String testName) {
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(ScannerTest.class);
	}

	/*
	 * Write Tests!
	 */

	public void testSimpleScan() throws ScanException {
		String testText = "90; int blah";
		InputStream is = new ByteArrayInputStream(testText.getBytes());
		ArrayList<Token> reference = new ArrayList<Token>();
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.INTLIT, "90", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ";", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "int", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "blah", 0, 0));
		ArrayList<Token> generated = new ArrayList<Token>();
		Scanner scanner = new Scanner(is, DFA.JavaDFA, TokenFactory.JavaTokenFactory);
		while (true) {
			Token next = scanner.getToken();
			if (next == null) {
				break;
			}
			generated.add(next);
		}
		for (int i = 0; i < generated.size(); i++) {
			assertTrue(generated.get(i).equals(reference.get(i)));
		}
	}

	public void testMoreComplexScan() throws ScanException {
		String testText = 
			"public void main(String[] args) {\n" +
			"\t System.out.println(\"Hello World\");\n" +
			"\t System.out.println(4 / 2 == 2); \n" +
			"}";
		InputStream is = new ByteArrayInputStream(testText.getBytes());
		ArrayList<Token> reference = new ArrayList<Token>();
		// .equals doesn't care about line number
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "public", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "void", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "main", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "(", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "String", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "[", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "]", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "args", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ")", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "{", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "System", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ".", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "out", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ".", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "println", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "(", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.STRINGLIT, "\"Hello World\"", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ")", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ";", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "System", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ".", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "out", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ".", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.IDENTIFIER, "println", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "(", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.INTLIT, "4", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.OPERATOR, "/", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.INTLIT, "2", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.OPERATOR, "==", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.INTLIT, "2", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ")", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, ";", 0, 0));
		reference.add(TokenFactory.JavaTokenFactory.makeToken(TokenType.PUNCTUATION, "}", 0, 0));
		ArrayList<Token> generated = new ArrayList<Token>();
		Scanner scanner = new Scanner(is, DFA.JavaDFA, TokenFactory.JavaTokenFactory);
		//int j = 0;
		while (true) {
			Token next = scanner.getToken();
			if (next == null) {
				break;
			}
			//System.out.println(next.toString());
			//System.out.println(reference.get(j).toString());
			//j++;
			generated.add(next);
		}
		for (int i = 0; i < generated.size(); i++) {
			/*
			 *System.out.println(generated.get(i).toString());
			 *System.out.println(reference.get(i).toString());
			 */
			assertTrue(generated.get(i).equals(reference.get(i)));
		}
	}
}
