package org.sobit.elqu.admin.inMessage;

@SuppressWarnings("serial")
public class INMesException extends Exception{


	public INMesException(String s) {
		this(s, null);
	}

	public INMesException(Throwable throwable) {
		this(throwable.getMessage(), throwable);
	}

	public INMesException(String s, Throwable throwable) {
		super(s, throwable);
	}
}