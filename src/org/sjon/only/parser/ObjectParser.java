package org.sjon.only.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sjon.only.parser.exceptions.IllegalColumnGroupStartException;
import org.sjon.only.parser.exceptions.LookaheadException;
import org.sjon.only.scanner.ObjectAnalyzer;
import org.sjon.only.scanner.Token;

public class ObjectParser {
	
	private ObjectAnalyzer analyzer;
	private List<ColumnGroup> document = new ArrayList<ColumnGroup>();
	
	private List<Token> tokens;
	private List<String> tokenValues;
	
	private int currentIndex = 0;
	
	public ObjectParser(ObjectAnalyzer analyzer) {
		this.analyzer = analyzer;
	}
	
	public List<ColumnGroup> getDocument() {
		return this.document;
	}
	
	public void parse() throws IllegalColumnGroupStartException, LookaheadException {
		
		this.tokens = analyzer.getTokens();
		this.tokenValues = analyzer.getTokenValues();
		
		while (currentIndex < tokens.size()) {
			this.document.add(getNextColumnGroup());
		}
	}
	
	private ColumnGroup getNextColumnGroup() throws IllegalColumnGroupStartException, LookaheadException {
		
		ColumnGroup columnGroup;
		
		switch(this.tokens.get(currentIndex)) {
			case BLOCK_START:
				currentIndex++;
				columnGroup = new ColumnMap(getNextColumnCommalist());
				lookahead(Token.BLOCK_END);
				break;
			case LIST_START:
				currentIndex++;
				columnGroup = new ColumnList(getNextValueCommalist());
				lookahead(Token.LIST_END);
				break;
			default:
				throw new IllegalColumnGroupStartException();
		}
		
		return columnGroup;
	}
	
	private Map<String, Column> getNextColumnCommalist() throws LookaheadException, IllegalColumnGroupStartException {

		Map<String, Column> columnMap = new HashMap<String, Column>();
		
		Column column = getNextColumn();
		columnMap.put(column.getKey(), column);
		
		while (tokens.get(currentIndex).equals(Token.COMMA)) {
			this.currentIndex++;
			column = getNextColumn(); // Lookahead next column
			columnMap.put(column.getKey(), column);
		}
		
		return columnMap;
	}
	
	private List<Object> getNextValueCommalist() throws IllegalColumnGroupStartException, LookaheadException {
		
		List<Object> retVal = new ArrayList<Object>();
		
		retVal.add(getNextValue());
		
		while (tokens.get(currentIndex).equals(Token.COMMA)) {
			this.currentIndex++;
			retVal.add(getNextValue());
		}
		
		return retVal;
	}
	
	
	private Object getNextValue() throws LookaheadException, IllegalColumnGroupStartException {
		
		ColumnGroup columnGroup;
		
		switch(this.tokens.get(currentIndex)) {
		case BLOCK_START:
			currentIndex++;
			columnGroup = new ColumnMap(getNextColumnCommalist());
			lookahead(Token.BLOCK_END);
			break;
		case LIST_START:
			currentIndex++;
			columnGroup = new ColumnList(getNextValueCommalist());
			lookahead(Token.LIST_END);
			break;
		default:
			/**
			 * TODO: DEBUG
			 */
			System.out.println("Current value: " + tokenValues.get(currentIndex));
			return tokenValues.get(currentIndex++);
		}
		
		return columnGroup;
	}
	
	private Column getNextColumn() throws LookaheadException, IllegalColumnGroupStartException {
 		
		String currentColumnKey = tokenValues.get(currentIndex++);
		
		lookahead(Token.KEY_VALUE_SEPARATOR);
		Object currentColumnValue = getNextColumnValue();
		
		/**
		 * TODO: DEBUG
		 */
		System.out.println("Current column: (" + currentColumnKey + ", " + currentColumnValue + ")");
		
		return new Column(currentColumnKey, currentColumnValue);
	}
	
	private void lookahead(Token token) throws LookaheadException {
		
		if (!tokens.get(currentIndex).equals(token)) {
			
			/**
			 * DEBUG
			 */
			// System.out.println("Current token: " + tokens.get(currentIndex));
			// System.out.println("Current token value: " + tokenValues.get(currentIndex));
			
			throw new LookaheadException(token);
		}
		
		currentIndex++;
	}
	
	private Object getNextColumnValue() throws LookaheadException, IllegalColumnGroupStartException {
		switch(this.tokens.get(currentIndex)) {
			case BLOCK_START:
			case LIST_START:
				return getNextColumnGroup();
			default:
				return tokenValues.get(currentIndex++);
		}
	}
}
