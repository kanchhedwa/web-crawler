package com.crawler.webcrawler.controller;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.crawler.webcrawler.constants.WebCrawlerConstants;
import com.crawler.webcrawler.dto.CompleteWebPageDetailDTO;
import com.crawler.webcrawler.dto.ServiceResponseDTO;
import com.crawler.webcrawler.exception.InputValidationException;
import com.crawler.webcrawler.services.CrawlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class CrawlerController.
 * 
 * @author shashanksh43
 */
@RestController
@Api(tags = WebCrawlerConstants.CRAWLER)
public class CrawlerController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerController.class);

	/** The crawler service. */
	@Autowired
	@Qualifier("crawlerServiceImpl")
	private CrawlerService crawlerService;

	/**
	 * Gets the web page info.
	 *
	 * @param request
	 *            the request
	 * @param url
	 *            the url
	 * @param depth
	 *            the optional depth
	 * @return the web page info
	 */
	@ApiResponses(value = { @ApiResponse(code = 400, message = WebCrawlerConstants.BAD_REQUEST),
			@ApiResponse(code = 404, message = WebCrawlerConstants.NOT_FOUND),
			@ApiResponse(code = 500, message = WebCrawlerConstants.INTERNAL_SERVER_ERROR) })
	@GetMapping(value = "/v1.0/crawler", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Timed
	@ExceptionMetered
	public ResponseEntity<ServiceResponseDTO<CompleteWebPageDetailDTO>> getWebPageInfo(HttpServletRequest request,
			@RequestParam(name = WebCrawlerConstants.PAGE_URL_PARAM, required = true) String pageUrl,
			@RequestParam(value = WebCrawlerConstants.CRAWLER_DEPTH_PARAM, required = false) String crawlDepth) {
		Instant requestReceivedTime = Instant.now();
		request.setAttribute(WebCrawlerConstants.REQUEST_TIME, requestReceivedTime);
		LOGGER.info("crawl request recieved for web page:::-{}:::at time:::-{}", pageUrl, requestReceivedTime);
		CompleteWebPageDetailDTO webPageDetail = null;
		if (crawlDepth == null) {
			validateInput(pageUrl, "1");
			webPageDetail = crawlerService.crawlHtmlPageWithDepth(pageUrl, 1, null);
		} else {
			validateInput(pageUrl,crawlDepth);
			webPageDetail = crawlerService.crawlHtmlPageWithDepth(pageUrl, Integer.parseInt(crawlDepth), null);
		}
		ServiceResponseDTO<CompleteWebPageDetailDTO> crawlerServiceDTO = prepareResponse(requestReceivedTime,
				webPageDetail);
		LOGGER.info("crawl request served, exiting method getWebPageInfo");
		return new ResponseEntity<>(crawlerServiceDTO, HttpStatus.OK);
	}

	/**
	 * Prepare response.
	 *
	 * @param requestReceivedTime
	 *            the request received time
	 * @param webPageDetail
	 *            the web page detail
	 * @return the service response DTO
	 */
	private ServiceResponseDTO<CompleteWebPageDetailDTO> prepareResponse(Instant requestReceivedTime,
			CompleteWebPageDetailDTO webPageDetail) {
		ServiceResponseDTO<CompleteWebPageDetailDTO> serviceresponse = new ServiceResponseDTO<>();
		serviceresponse.setData(webPageDetail);
		serviceresponse.setRequestProcessingTime(ChronoUnit.MILLIS.between(requestReceivedTime, Instant.now()));
		return serviceresponse;
	}

	/**
	 * Validate input.
	 *
	 * @param crawlDepth
	 *            the crawl depth
	 * @param pageUrl
	 *            the page url
	 */
	private void validateInput(String pageUrl, String crawlDepth) {
		UrlValidator url = new UrlValidator();
		boolean invalidUrl = false;
		boolean invalidCrawlDepth = false;
		String message = "";
		if (!url.isValid(pageUrl)) {
			invalidUrl = true;
			message = MessageFormat.format(WebCrawlerConstants.BAD_REQUEST_URL, pageUrl);
		}
		try {
			Integer.parseInt(crawlDepth);
		} catch (NumberFormatException nfe) {
			invalidCrawlDepth = true;
			message = MessageFormat.format(WebCrawlerConstants.BAD_REQUEST_CRAWL_DEPTH, crawlDepth);
		}

		if (invalidUrl && invalidCrawlDepth) {
			message = MessageFormat.format(WebCrawlerConstants.BAD_REQUEST_URL_AND_CRAWL_DEPTH, pageUrl, crawlDepth);
			throw new InputValidationException(HttpStatus.BAD_REQUEST.name(),message);
		}
		if (invalidUrl) {
			throw new InputValidationException(HttpStatus.BAD_REQUEST.name(),message);
		}
		if (invalidCrawlDepth) {
			throw new InputValidationException(HttpStatus.BAD_REQUEST.name(),message);
		}
	}

}
