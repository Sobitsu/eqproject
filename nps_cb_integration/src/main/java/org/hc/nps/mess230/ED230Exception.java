package org.hc.nps.mess230;

@SuppressWarnings("serial")
public class ED230Exception extends Exception{


	public ED230Exception(String s) {
		this(s, null);
	}

	public ED230Exception(Throwable throwable) {
		this(throwable.getMessage(), throwable);
	}

	public ED230Exception(String s, Throwable throwable) {
		super(s, throwable);
	}
}
