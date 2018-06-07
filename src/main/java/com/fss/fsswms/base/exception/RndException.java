package com.fss.fsswms.base.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class RndException extends Exception {

	private String messageId;
	private Object [] messageParams;

	public RndException(String messageId) {
		super("");
		this.messageId = messageId;
	}

	public RndException(String messageId, String defaultMessage) {
		super(defaultMessage);
		this.messageId = messageId;
	}

	public RndException(String messageId, String defaultMessage, Throwable cause) {
		super(defaultMessage, cause);
		this.messageId = messageId;
	}

	public RndException(String messageId, Object[] messageParams) {
		super("");
		this.messageId = messageId;
		this.messageParams = messageParams;
	}

	public RndException(String messageId, String defaultMessage, Object[] messageParams) {
		super(defaultMessage);
		this.messageId = messageId;
		this.messageParams = messageParams;
	}

	public RndException(String messageId, String defaultMessage, Object[] messageParams, Throwable cause) {
		super(defaultMessage, cause);
		this.messageId = messageId;
		this.messageParams = messageParams;
	}

	public RndException(Throwable cause) {
		super(cause);
		if (cause instanceof RndException) {
			RndException bizException = ((RndException) cause);
			this.messageId = bizException.messageId;
			this.messageParams = bizException.messageParams;
		}
	}

	public String getMessageId() {
		return messageId;
	}

	public Object [] getMessageParams() {
		return messageParams;
	}

	@Override
	public String getMessage() {
        if (messageParams == null) {
        	return super.getMessage();
        }
        return MessageFormat.format(super.getMessage(), messageParams);
    }

    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": [" + messageId + "] " + message) : (s + ": [" + messageId + "]");
    }
	
}
