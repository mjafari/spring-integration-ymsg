package com.tosan.ymsg;

import org.openymsg.network.YahooException;

/**
 * GenericYahooException is a concrete subclass of abstract YahooException for
 * generic usages.
 * 
 * @author mjafari
 */
public class GenericYahooException extends YahooException {

	private static final long serialVersionUID = -4769947990953182110L;

	public GenericYahooException(String message) {
		super(message);
	}

	public GenericYahooException(String message, Throwable cause) {
		super(message, cause);
	}
}
