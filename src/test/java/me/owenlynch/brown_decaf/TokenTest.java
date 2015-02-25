package me.owenlynch.brown_decaf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TokenTest extends TestCase {
	public TokenTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/*
	 * Write tests!
	 */
	public void testToken() {
		assertTrue(true);
	}
}
