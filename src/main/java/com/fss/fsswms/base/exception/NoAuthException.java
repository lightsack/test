package com.fss.fsswms.base.exception;

@SuppressWarnings("serial")
public class NoAuthException extends RuntimeException {
	
	private String messageId;
	
	public NoAuthException() {
		super();
	}
	
	public NoAuthException(String messageId) {
		super();
		this.messageId = messageId;
	}
	
	public NoAuthException(String messageId, String defaultMessage) {
		super(defaultMessage);
		this.messageId = messageId;
	}

	public NoAuthException(String messageId, Throwable cause) {
		super(null, cause);
		this.messageId = messageId;	
	}

	public NoAuthException(Throwable cause) {
		super(cause);
	}
	
	public String getMessageId() {
		return messageId;
	}
	
}