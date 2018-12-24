package com.crawler.webcrawler.exception.handler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crawler.webcrawler.builder.ServiceResponseBuilder;
import com.crawler.webcrawler.constants.WebCrawlerConstants;
import com.crawler.webcrawler.dto.ServiceResponseDTO;
import com.crawler.webcrawler.exception.CrawlerException;

/**
 * The Class GlobalExceptionHandler.
 * 
 * @author shashanksh43
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/** The response builder. */
	@Autowired
	private ServiceResponseBuilder responseBuilder;

	/**
	 * Handle crawler exception.
	 *
	 * @param request
	 *            the request
	 * @param ex
	 *            the ex
	 * @return the response entity
	 */
	@ExceptionHandler(CrawlerException.class)
	public ResponseEntity<ServiceResponseDTO<Object>> handleCrawlerException(HttpServletRequest request,
			CrawlerException ex) {

		ServiceResponseDTO<Object> svcResponse = responseBuilder.buildErrorResponse(ex);
		svcResponse.setRequestProcessingTime(ChronoUnit.MILLIS
				.between((Instant) request.getAttribute(WebCrawlerConstants.REQUEST_TIME), Instant.now()));

		switch (ex.getErrorCode()) {
		case "SERVICE_NOT_AVAILABLE":
			return new ResponseEntity<>(svcResponse, HttpStatus.SERVICE_UNAVAILABLE);
		case "BAD_REQUEST":
			return new ResponseEntity<>(svcResponse, HttpStatus.BAD_REQUEST);
		case "UNAUTHORIZED":
			return new ResponseEntity<>(svcResponse, HttpStatus.FORBIDDEN);
		case "UNAUTHENTICATED":
			return new ResponseEntity<>(svcResponse, HttpStatus.UNAUTHORIZED);
		case "NOT_FOUND":
			return new ResponseEntity<>(svcResponse, HttpStatus.NOT_FOUND);
		default:
			return new ResponseEntity<>(svcResponse, HttpStatus.NOT_IMPLEMENTED);

		}

	}

	/**
	 * Handle other exception.
	 *
	 * @param request
	 *            the request
	 * @param exception
	 *            the exception
	 * @return the response entity
	 */
	@ExceptionHandler
	public ResponseEntity<ServiceResponseDTO<Object>> handleOtherException(HttpServletRequest request,
			Exception exception) {
		logger.info("Global Exception Handler unhandled exception::::{}", exception.getMessage());
		String stacktrace = ExceptionUtils.getStackTrace(exception);
		logger.info("Global Exception Handler Stcktrace:::{}", stacktrace);
		ServiceResponseDTO<Object> svcResponse = responseBuilder.buildErrorResponse(exception);
		svcResponse.setRequestProcessingTime(ChronoUnit.MILLIS
				.between((Instant) request.getAttribute(WebCrawlerConstants.REQUEST_TIME), Instant.now()));
		return new ResponseEntity<>(svcResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

}