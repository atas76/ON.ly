package org.sjon.only.parser.exceptions;

import org.sjon.only.scanner.Token;

public class LookaheadException extends Exception {
	
	public LookaheadException(Token token, int currentRecord) {
		super(token.toString() + " Current record: " + currentRecord);
	}
}
