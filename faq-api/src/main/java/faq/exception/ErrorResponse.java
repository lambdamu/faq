package faq.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
	private final int code;
	private final String message;
	private final Map<String, String> fieldErrors;

	public ErrorResponse(int code, String message) {
		this(code, message, new HashMap<>());
	}

	public ErrorResponse(int code, String message, Map<String,String> fieldErrors) {
		this.code = code;
		this.message = message;
		this.fieldErrors = fieldErrors;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public Map<String, String> getFieldErrors() {
		return this.fieldErrors;
	}
}
