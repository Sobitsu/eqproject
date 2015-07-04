package org.hc.nps.inMessage;


@SuppressWarnings("serial")
public class JINMesParseException extends Exception
{
	public JINMesParseException(String s) {
		this(s, null);
	}

	public JINMesParseException(Throwable throwable) {
		this(throwable.getMessage(), throwable);
	}

	public JINMesParseException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
