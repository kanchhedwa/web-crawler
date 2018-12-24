package com.crawler.webcrawler.dto;

import java.io.Serializable;

/**
 * The Class ErrorDetailDTO.
 * 
 * @author shashanksh43
 */
public class ErrorDetailDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7029797929234672988L;

	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	/**
	 * Instantiates a new error detail DTO.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public ErrorDetailDTO(String code, String message) {
		this.errorCode = code;
		this.errorMessage = message;
	}

	/**
	 * Instantiates a new error detail DTO.
	 */
	public ErrorDetailDTO() {

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
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage
	 *            the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[code=" + errorCode + ", message=" + errorMessage + "]";
	}

}
