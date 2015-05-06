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
		reference.add(new IntToken("90", 0, 0));
		reference.add(new PunctuationToken(";", 0, 0));
		reference.add(new KeywordToken("int", 0, 0));
		reference.add(new IDToken("blah", 0, 0));
		ArrayList<Token> generated = new ArrayList<Token>();
		Scanner scanner = new Scanner(is);
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
		reference.add(new IDToken("public", 0, 0));
		reference.add(new KeywordToken("void", 0, 0));
		reference.add(new IDToken("main", 0, 0));
		reference.add(new PunctuationToken("(", 0, 0));
		reference.add(new IDToken("String", 0, 0));
		reference.add(new PunctuationToken("[", 0, 0));
		reference.add(new PunctuationToken("]", 0, 0));
		reference.add(new IDToken("args", 0, 0));
		reference.add(new PunctuationToken(")", 0, 0));
		reference.add(new PunctuationToken("{", 0, 0));
		reference.add(new IDToken("System", 0, 0));
		reference.add(new PunctuationToken(".", 0, 0));
		reference.add(new IDToken("out", 0, 0));
		reference.add(new PunctuationToken(".", 0, 0));
		reference.add(new IDToken("println", 0, 0));
		reference.add(new PunctuationToken("(", 0, 0));
		reference.add(new StringToken("\"Hello World\"", 0, 0));
		reference.add(new PunctuationToken(")", 0, 0));
		reference.add(new PunctuationToken(";", 0, 0));
		reference.add(new IDToken("System", 0, 0));
		reference.add(new PunctuationToken(".", 0, 0));
		reference.add(new IDToken("out", 0, 0));
		reference.add(new PunctuationToken(".", 0, 0));
		reference.add(new IDToken("println", 0, 0));
		reference.add(new PunctuationToken("(", 0, 0));
		reference.add(new IntToken("4", 0, 0));
		reference.add(new OperatorToken("/", 0, 0));
		reference.add(new IntToken("2", 0, 0));
		reference.add(new OperatorToken("==", 0, 0));
		reference.add(new IntToken("2", 0, 0));
		reference.add(new PunctuationToken(")", 0, 0));
		reference.add(new PunctuationToken(";", 0, 0));
		reference.add(new PunctuationToken("}", 0, 0));
		ArrayList<Token> generated = new ArrayList<Token>();
		Scanner scanner = new Scanner(is);
		int j = 0;
		while (true) {
			Token next = scanner.getToken();
			if (next == null) {
				break;
			}
			/*
			 *System.out.println(next.toString());
			 *System.out.println(reference.get(j).toString());
			 */
			j++;
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
