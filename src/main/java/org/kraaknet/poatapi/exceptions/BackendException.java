package org.kraaknet.poatapi.exceptions;

import lombok.ToString;

@ToString(callSuper = true)
public class BackendException extends ApiException {

	public BackendException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public BackendException(String msg) {
		super(msg);
	}
}
