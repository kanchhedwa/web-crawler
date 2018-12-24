package com.crawler.webcrawler.constants;

/**
 * The Class WebCrawlerConstants.
 */
public class WebCrawlerConstants {

	/** The Constant CRAWLER. */
	public static final String CRAWLER = "Crawler";
	
	/** The Constant PAGE_URL_PARAM. */
	public static final String PAGE_URL_PARAM = "pageUrl";
	
	/** The Constant CRAWLER_DEPTH_PARAM. */
	public static final String CRAWLER_DEPTH_PARAM = "crawlDepth";

	/** The Constant BAD_REQUEST. */
	public static final String BAD_REQUEST = "Invalid inputs are provided in request.";

	/** The Constant BAD_REQUEST_URL. */
	public static final String BAD_REQUEST_URL = "Invalid inputs pageUrl-{0}";

	/** The Constant BAD_REQUEST_CRAWL_DEPTH. */
	public static final String BAD_REQUEST_CRAWL_DEPTH = "Invalid inputs crawlDepth-{0}";
	
	/** The Constant BAD_REQUEST_URL_AND_CRAWL_DEPTH. */
	public static final String BAD_REQUEST_URL_AND_CRAWL_DEPTH = "Invalid inputs pageUrl-{0} and crawlDepth-{1}";

	/** The Constant INTERNAL_SERVER_ERROR. */
	public static final String INTERNAL_SERVER_ERROR = "Request encountered an unexpected error.";

	/** The Constant NOT_FOUND. */
	public static final String NOT_FOUND = "Web page not found.";

	/** The Constant TRACE_ID. */
	public static final String TRACE_ID = "X-B3-TraceId";

	/** The Constant SPAN_ID. */
	public static final String SPAN_ID = "X-B3-SpanId";

	/** The Constant REQUEST_TIME. */
	public static final String REQUEST_TIME = "requestTime";

	/** The Constant GENERIC_ERROR_CODE. */
	public static final String GENERIC_ERROR_CODE = "GENERIC_ERROR";
	
	/** The Constant BAD_REQUEST_ERROR_CODE. */
	public static final String BAD_REQUEST_ERROR_CODE = "BAD_REQUEST";
	
	/** The Constant SVC_STATUS_ERROR. */
	public static final String SVC_STATUS_ERROR = "ERROR";
	
	/** The Constant SVC_STATUS_SUCCESS. */
	public static final String SVC_STATUS_SUCCESS = "SUCCESS";

	/**
	 * Instantiates a new web crawler constants.
	 */
	private WebCrawlerConstants() {

	}
}
