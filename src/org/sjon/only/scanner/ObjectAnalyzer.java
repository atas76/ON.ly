package org.sjon.only.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.sjon.only.scanner.exceptions.EmptyObjectException;

public class ObjectAnalyzer {
	
	private final char BLOCK_START = '{';
	private final char BLOCK_END = '}';
	private final char LIST_START = '[';
	private final char LIST_END = ']';
	private final char COMMA = ',';
	private final char KEY_VALUE_SEPARATOR = ':';
	
	private String text;
	private List<Token> tokens = new ArrayList<Token>();
	private List<String> tokenValues = new ArrayList<String>();
	
	public ObjectAnalyzer(File file) {
		
		try {
		
			BufferedReader br = new BufferedReader(new FileReader(file));
		
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        
	        this.text = sb.toString();
	        
	    } catch (Exception ex) {
	    	this.text = null;
	    }
	}
	
	public ObjectAnalyzer(String text) {
		this.text = text;
	}
	
	public List<Token> getTokens() {
		return this.tokens;
	}
	
	public List<String> getTokenValues() {
		return this.tokenValues;
	}
	
	private char [] lineChars;
	
	/**
	 * Scans each input character
	 */
	public void analyze() throws EmptyObjectException {
		
		if (this.text == null) {
			throw new EmptyObjectException();
		}
		
		lineChars = new char[this.text.length()]; 
		this.text.getChars(0, this.text.length(), lineChars, 0);
		
		int currentIndex = 0;
		
		/**
		 * Scan each input character
		 */
		while (currentIndex < this.lineChars.length) {
			currentIndex = scan(currentIndex);
		}
	}

	/**
	 * Scan next token and assign its value and type to symbol table
	 * @param startIndex the index of the input array, from which the scanning for the current token will start 
	 * @return the index of the next token to scan
	 */
	private int scan(int startIndex) {
		
		int charIndex;
		
		/**
		 * Add special characters as their corresponding tokens and scan literals
		 */
		for (charIndex = startIndex; charIndex < lineChars.length; charIndex++) {
			
			char c = lineChars[charIndex];
			
			if (Character.isWhitespace(c)) continue;
			
			switch(c) {
			case BLOCK_START:
				this.tokens.add(Token.BLOCK_START);
				addCharacterToken(charIndex, c);
				break;
			case BLOCK_END:
				this.tokens.add(Token.BLOCK_END);
				addCharacterToken(charIndex, c);
				break;
			case LIST_START:
				this.tokens.add(Token.LIST_START);
				addCharacterToken(charIndex, c);
				break;
			case LIST_END:
				this.tokens.add(Token.LIST_END);
				addCharacterToken(charIndex, c);
				break;
			case COMMA:
				this.tokens.add(Token.COMMA);
				addCharacterToken(charIndex, c);
				break;
			case KEY_VALUE_SEPARATOR:
				this.tokens.add(Token.KEY_VALUE_SEPARATOR);
				addCharacterToken(charIndex, c);
				break;
			default: // literal
				this.tokens.add(Token.LITERAL);
				charIndex = scanLiteral(charIndex);
			}
		}
		
		return charIndex;
	}
	
	/**
	 * Scan next token as a literal
	 * @param startIndex the index of the input array, from which the scanning for the current token will start 
	 * @return the index of the next token to scan
	 */
	private int scanLiteral(int startIndex) {
		
		int charIndex;
		
		StringBuilder literal = new StringBuilder();
		
		/**
		 * Add each character to the current token literal if applicable; 
		 * halt if a special character is found and backtrack the index so that it will be scanned by the calling method 
		 */
		for (charIndex = startIndex; charIndex < lineChars.length; charIndex++) {
			
			char c = lineChars[charIndex];
			
			switch(c) {
			case BLOCK_START:
			case BLOCK_END:
			case LIST_START:
			case LIST_END:
			case COMMA:
			case KEY_VALUE_SEPARATOR:
				this.tokenValues.add(literal.toString());
				return charIndex - 1;
			default: // literal
				literal.append(String.valueOf(c));
			}
		}
		
		this.tokenValues.add(literal.toString());
		return charIndex;
	}

	private void addCharacterToken(int nextIndex, char c) {
		this.tokenValues.add(String.valueOf(c));
	}
}
