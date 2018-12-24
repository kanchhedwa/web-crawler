package com.crawler.webcrawler.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.crawler.webcrawler.constants.WebCrawlerConstants;
import com.crawler.webcrawler.dto.ErrorDetailDTO;
import com.crawler.webcrawler.dto.ServiceResponseDTO;
import com.crawler.webcrawler.exception.CrawlerException;
import com.crawler.webcrawler.exception.InputValidationException;

/**
 * The Class ServiceResponseBuilder.
 * 
 * @author shashanksh43
 */
@Component
@Primary
public class ServiceResponseBuilder {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceResponseBuilder.class);

	/**
	 * Builds the validation error response.
	 *
	 * @param errors
	 *            the errors
	 * @return the service response DTO
	 */
	public ServiceResponseDTO<Object> buildValidationErrorResponse(Errors errors) {
		ServiceResponseDTO<Object> svcResp = new ServiceResponseDTO<>();
		StringBuilder sb = new StringBuilder();
		for (ObjectError error : errors.getAllErrors()) {
			sb.append(error.getObjectName()).append(",");
		}
		String errMsg = sb.toString();
		errMsg = errMsg.substring(errMsg.lastIndexOf(','));

		LOGGER.error(errMsg);
		ErrorDetailDTO errorDetail = new ErrorDetailDTO();
		errorDetail.setErrorMessage(errMsg);
		errorDetail.setErrorCode(WebCrawlerConstants.GENERIC_ERROR_CODE);
		svcResp.appendErrorMessage(errorDetail);
		svcResp.setServiceStatus(WebCrawlerConstants.SVC_STATUS_ERROR);
		return svcResp;
	}

	/**
	 * Builds the error response.
	 *
	 * @param e
	 *            the e
	 * @return the service response DTO
	 */
	public ServiceResponseDTO<Object> buildErrorResponse(CrawlerException e) {
		String errMsg = e.getMessage();
		LOGGER.error(errMsg);
		ServiceResponseDTO<Object> svcResp = new ServiceResponseDTO<>();
		ErrorDetailDTO errorDetail = new ErrorDetailDTO();
		errorDetail.setErrorMessage(errMsg);
		if(e instanceof InputValidationException){
			errorDetail.setErrorCode(WebCrawlerConstants.BAD_REQUEST_ERROR_CODE);
			svcResp.appendErrorMessage(errorDetail);
			svcResp.setServiceStatus(WebCrawlerConstants.SVC_STATUS_ERROR);
			
		}else{
			errorDetail.setErrorCode(WebCrawlerConstants.GENERIC_ERROR_CODE);
			svcResp.appendErrorMessage(errorDetail);
			svcResp.setServiceStatus(WebCrawlerConstants.SVC_STATUS_ERROR);
		}
		return svcResp;
	}

	/**
	 * Builds the error response.
	 *
	 * @param e
	 *            the e
	 * @return the service response DTO
	 */
	public ServiceResponseDTO<Object> buildErrorResponse(Exception e) {
		String errMsg = e.getMessage();
		LOGGER.error(errMsg);
		ServiceResponseDTO<Object> svcResp = new ServiceResponseDTO<>();
		ErrorDetailDTO errorDetail = new ErrorDetailDTO();
		errorDetail.setErrorMessage(errMsg);
		errorDetail.setErrorCode(WebCrawlerConstants.GENERIC_ERROR_CODE);
		svcResp.appendErrorMessage(errorDetail);
		svcResp.setServiceStatus(WebCrawlerConstants.SVC_STATUS_ERROR);
		return svcResp;
	}

}
