package com.crawler.webcrawler.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.crawler.webcrawler.constants.WebCrawlerConstants;

/**
 * The Class ServiceResponseDTO.
 *
 * @author shashanksh43
 *
 * @param <T>
 *            the generic type
 */
public class ServiceResponseDTO<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8280709910081266990L;

	/** The service status. */
	protected String serviceStatus = WebCrawlerConstants.SVC_STATUS_SUCCESS;

	/** The error messages. */
	private List<ErrorDetailDTO> errorMessages = new ArrayList<>();

	/** The data. */
	private T data;

	/** The request processing time. */
	private String requestProcessingTime;

	/**
	 * Gets the request processing time.
	 *
	 * @return the request processing time
	 */
	public String getRequestProcessingTime() {
		return requestProcessingTime;
	}

	/**
	 * Sets the request processing time.
	 *
	 * @param requestProcessingTime
	 *            the new request processing time
	 */
	public void setRequestProcessingTime(long millis) {
		this.requestProcessingTime = Long.toString(millis)+" ms";
	}

	/**
	 * Gets the service status.
	 *
	 * @return the service status
	 */
	public String getServiceStatus() {
		return serviceStatus;
	}

	/**
	 * Sets the service status.
	 *
	 * @param serviceStatus
	 *            the new service status
	 */
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/**
	 * Sets the error messages.
	 *
	 * @param errorMessages
	 *            the new error messages
	 */
	public void setErrorMessages(List<ErrorDetailDTO> errorMessages) {
		this.errorMessages = errorMessages;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	public List<ErrorDetailDTO> getErrorMessages() {
		return Collections.unmodifiableList(errorMessages);
	}

	/**
	 * Append error message.
	 *
	 * @param errorMessage
	 *            the error message
	 */
	public void appendErrorMessage(ErrorDetailDTO errorMessage) {
		if (null != errorMessage) {
			this.errorMessages.add(errorMessage);
		}
	}
}
