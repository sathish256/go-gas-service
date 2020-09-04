package com.connectgas.app.exception;

public class ConnectGasDataAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859519184357798879L;

	public ConnectGasDataAccessException() {
		super();
	}

	public ConnectGasDataAccessException(String message) {
		super(message);
	}

	public ConnectGasDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}