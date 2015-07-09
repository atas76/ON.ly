package org.sjon.only.scanner;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sjon.only.parser.Column;
import org.sjon.only.parser.ColumnGroup;
import org.sjon.only.parser.ColumnList;
import org.sjon.only.parser.ColumnMap;
import org.sjon.only.parser.ObjectParser;
import org.sjon.only.properties.Resources;

public class ObjectParserTest {
	
	private ObjectAnalyzer analyzer;
	private ObjectParser parser;
	
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
	
	/*
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
	*/
	
	@Test
	public void throwawayTest() throws Exception {
		
		File dataFile = new File(Resources.THROWAWAY_DATA);
		
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
	        
	        analyzer.analyze();
			this.parser = new ObjectParser(this.analyzer);
			
			this.parser.parse();
			
			List<ColumnGroup> document = this.parser.getDocument();
	        
	    } catch(Exception ex) {
	    	// System.out.println("Current record: " + this.parser.getCurrentRecord());
	    	ex.printStackTrace();
	    } finally {
	        br.close();
	    }
	}
	
	@Test
	public void testParse() throws Exception {
		
		analyzer.analyze();
		this.parser = new ObjectParser(this.analyzer);
		
		this.parser.parse();
		
		List<ColumnGroup> document = this.parser.getDocument();
		
		ColumnMap singleColumnGroup = (ColumnMap) document.get(0);
		
		Column typeColumn = singleColumnGroup.getColumn("type");
		assertEquals("match", typeColumn.getValue());
		
		Column homeTeam = singleColumnGroup.getColumn("homeTeam");
		
		ColumnMap homeTeamColumnGroup = (ColumnMap) homeTeam.getValue();
		
		Column homeTeamNameColumn = homeTeamColumnGroup.getColumn("name");
		assertEquals("Real Madrid", homeTeamNameColumn.getValue());
		
		Column lineupColumn = homeTeamColumnGroup.getColumn("lineup");
		ColumnMap lineupColumnGroup = (ColumnMap) lineupColumn.getValue();
		
		Column gkColumn = lineupColumnGroup.getColumn("GK");
		ColumnMap gkColumnGroup = (ColumnMap) gkColumn.getValue();
		
		Column playerNameColumn = gkColumnGroup.getColumn("playerName");
		String gkName = (String) playerNameColumn.getValue();
		assertEquals("Casillas", gkName);
		
		Column subsColumn = homeTeamColumnGroup.getColumn("substitutes");
		ColumnList subsList = (ColumnList) subsColumn.getValue();
		
		ColumnMap subColumnGroup = (ColumnMap) subsList.getValue(2);
		
		Column subNameColumn = subColumnGroup.getColumn("playerName");
		assertEquals("Arbeloa", subNameColumn.getValue());
		
		Column playDurationColumn = subColumnGroup.getColumn("duration");
		assertEquals("0.01", playDurationColumn.getValue());
		
		Column homeScoreColumn = singleColumnGroup.getColumn("homeScore");
		assertEquals("3", homeScoreColumn.getValue());
		
		Column dateColumn = singleColumnGroup.getColumn("date");
		assertEquals("25-10-2014", dateColumn.getValue());
		
		ColumnMap columnGroup1 = (ColumnMap) document.get(1);
		
		Column typeColumn1 = columnGroup1.getColumn("type");
		assertEquals("match", typeColumn1.getValue());
		
		Column homeTeam1 = columnGroup1.getColumn("homeTeam");
		
		ColumnMap homeTeamColumnGroup1 = (ColumnMap) homeTeam1.getValue();
		
		Column homeTeamNameColumn1 = homeTeamColumnGroup1.getColumn("name");
		assertEquals("Real Madrid", homeTeamNameColumn1.getValue());
		
		Column lineupColumn1 = homeTeamColumnGroup1.getColumn("lineup");
		ColumnMap lineupColumnGroup1 = (ColumnMap) lineupColumn1.getValue();
		
		Column gkColumn1 = lineupColumnGroup1.getColumn("GK");
		ColumnMap gkColumnGroup1 = (ColumnMap) gkColumn1.getValue();
		
		Column playerNameColumn1 = gkColumnGroup1.getColumn("playerName");
		String gkName1 = (String) playerNameColumn1.getValue();
		assertEquals("Casillas", gkName1);
		
		Column subsColumn1 = homeTeamColumnGroup1.getColumn("substitutes");
		ColumnList subsList1 = (ColumnList) subsColumn1.getValue();
		
		ColumnMap subColumnGroup1 = (ColumnMap) subsList1.getValue(2);
		
		Column subNameColumn1 = subColumnGroup1.getColumn("playerName");
		assertEquals("Hernandez", subNameColumn1.getValue());
		
		Column playDurationColumn1 = subColumnGroup1.getColumn("duration");
		assertEquals("0.09", playDurationColumn1.getValue());
		
		Column homeScoreColumn1 = columnGroup1.getColumn("homeScore");
		assertEquals("5", homeScoreColumn1.getValue());
		
		Column dateColumn1 = columnGroup1.getColumn("date");
		assertEquals("16-09-2014", dateColumn1.getValue());
	}
}
