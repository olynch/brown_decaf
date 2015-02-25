//CS4
//2014-2015
//Scanner.java (Lexical Analysis)

//This class will be instantiated and used by the next piece, Parser.java
package me.owenlynch.brown_decaf;
import java.io.*;

class Scanner {
    final InputStream File;
    public Scanner(java.io.InputStream file) {
        File = file;
    }

	public void helloworld() {
		System.out.println("Hello World");
	}

    public int look() {
        //should look at the next character but not read it
		return -1;
    }

    public int getChar() {
        //should read the next character
		return -1;
    }

    void skipSpace() {
        //to implement
		return;
    }

    public Token getToken() {
        //to implement
        //this is the meat of the Scanner, I would suggest breaking it down
		return null;
    }

    boolean isKeyword(String name){
        //to implement
		return false;
    }
    
    String getIdentifier() {
        //to implemenet
		return "";
    }
    
}
