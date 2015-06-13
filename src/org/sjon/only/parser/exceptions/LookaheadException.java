package org.sjon.only.parser.exceptions;

import org.sjon.only.scanner.Token;

public class LookaheadException extends Exception {
	
	public LookaheadException(Token token) {
		super(token.toString());
	}
}
