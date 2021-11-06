package com.betfair.aping.exceptions;

public class APINGException extends Exception {

	private final String errorDetails;
	private final String errorCode;

	public APINGException(String errorDetails, String errorCode) {
		this.errorCode=errorCode;
		this.errorDetails=errorDetails;
	}
	
	public String getErrorDetails() {
		return errorDetails;
	}
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "ErrorCode: " + errorCode + " ErrorDetails: " + errorDetails;
	}

	public void printError() {
		System.err.println(this);
	}

}
