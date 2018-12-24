package com.crawler.webcrawler.exception;

import org.springframework.validation.BindingResult;

/**
 * The Class CrawlerException.
 * 
 * @author shashanksh43
 */
public class CrawlerException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8126333453283572035L;

	/** The error code. */
	private final String errorCode;
	
	/** The validation errors. */
	private BindingResult validationErrors;
	
	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param pMessage the message
	 */
	public CrawlerException(String pMessage) {
		super(pMessage);
		errorCode = null;
		validationErrors = null;
	}

	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param pErrorCode the error code
	 * @param pMessage the message
	 */
	public CrawlerException(String pErrorCode, String pMessage) {
		super(pMessage);
		errorCode = pErrorCode;
		validationErrors = null;
	}
	
	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param pErrorCode the error code
	 * @param pMessage the message
	 * @param validationErrors the validation errors
	 */
	public CrawlerException(String pErrorCode, String pMessage, BindingResult validationErrors) {
		super(pMessage);
		errorCode = pErrorCode;
		this.validationErrors = validationErrors;
	}
	
	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param pErrorCode the error code
	 * @param pMessage the message
	 * @param e the e
	 */
	public CrawlerException(String pErrorCode, String pMessage, Throwable e) {
		super(pMessage, e);
		errorCode = pErrorCode;
		validationErrors = null;
	}
	
	/**
	 * Instantiates a new crawler exception.
	 *
	 * @param pMessage the message
	 * @param e the e
	 */
	public CrawlerException(String pMessage, Throwable e) {
		super(e);
		errorCode = null;
	}


	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the validation errors.
	 *
	 * @return the validation errors
	 */
	public BindingResult getValidationErrors() {
		return validationErrors;
	}
}
