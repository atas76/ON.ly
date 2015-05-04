package org.sjon.only.scanner;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sjon.only.properties.Resources;

public class ObjectAnalyzerTest {
	
	private ObjectAnalyzer analyzer;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
	
		File dataFile = new File(Resources.TEST_DATA);
		
		BufferedReader br = new BufferedReader(new FileReader(dataFile));
		
	    try {
	    	
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        
	        this.analyzer = new ObjectAnalyzer(sb.toString());
	        
	    } finally {
	        br.close();
	    }
	}
	
	@Test
	public void testAnalyze() {
		
		analyzer.analyze();
		
		List<Token> tokens = analyzer.getTokens();
		List<String> tokenValues = analyzer.getTokenValues();
		
		// System.out.println(tokenValues.get(7));
		
		assertEquals(Token.BLOCK_START, tokens.get(0));
		assertEquals(Token.LITERAL, tokens.get(1));
		assertEquals(Token.KEY_VALUE_SEPARATOR, tokens.get(2));
		assertEquals(Token.LITERAL, tokens.get(3));
		assertEquals(Token.COMMA, tokens.get(4));
		assertEquals(Token.LITERAL, tokens.get(5));
		assertEquals(Token.KEY_VALUE_SEPARATOR, tokens.get(6));
		assertEquals(Token.BLOCK_START, tokens.get(7));
		
		assertEquals(Token.BLOCK_START, tokens.get(164));
		assertEquals(Token.BLOCK_START, tokens.get(169));
	}
}
