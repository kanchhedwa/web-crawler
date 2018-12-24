package com.crawler.webcrawler.exception;

import org.springframework.validation.BindingResult;

/**
 * The Class InputValidationException.
 * 
 * @author shashanksh43
 */
public class InputValidationException extends CrawlerException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1814648470287126564L;

	/** The error code. */
	private final String errorCode;

	/** The validation errors. */
	private BindingResult validationErrors;

	/**
	 * Instantiates a new input validation exception.
	 *
	 * @param errorCode
	 *            the error code
	 * @param message
	 *            the message
	 */
	public InputValidationException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		validationErrors = null;
	}

	/**
	 * Instantiates a new input validation exception.
	 *
	 * @param message
	 *            the message
	 */
	public InputValidationException(String message) {
		super(message);
		errorCode = "";
	}

	/**
	 * Instantiates a new input validation exception.
	 *
	 * @param errorCode
	 *            the error code
	 * @param message
	 *            the message
	 * @param validationErrors
	 *            the validation errors
	 */
	public InputValidationException(String errorCode, String message, BindingResult validationErrors) {
		super(message);
		this.errorCode = errorCode;
		this.validationErrors = validationErrors;
	}

	/**
	 * Instantiates a new input validation exception.
	 *
	 * @param errorCode
	 *            the error code
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 */
	public InputValidationException(String errorCode, String message, Throwable e) {
		super(errorCode, message, e);
		this.errorCode = errorCode;
		validationErrors = null;
	}

	/**
	 * Instantiates a new input validation exception.
	 *
	 * @param errorCode
	 *            the error code
	 * @param e
	 *            the e
	 */
	public InputValidationException(String errorCode, Throwable e) {
		super(errorCode, e);
		this.errorCode = errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crawler.webcrawler.exception.CrawlerException#getErrorCode()
	 */
	@Override
	public String getErrorCode() {
		return errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.crawler.webcrawler.exception.CrawlerException#getValidationErrors()
	 */
	@Override
	public BindingResult getValidationErrors() {
		return validationErrors;
	}
}
