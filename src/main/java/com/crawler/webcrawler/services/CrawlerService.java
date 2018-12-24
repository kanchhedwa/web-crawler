package com.crawler.webcrawler.services;

import java.util.List;

import com.crawler.webcrawler.dto.CompleteWebPageDetailDTO;

/**
 * The Interface CrawlerService.
 * 
 * @author shashanksh43
 */
public interface CrawlerService {

	/**
	 * Crawl html page with depth.
	 *
	 * @param url
	 *            the url
	 * @param crawlDepth
	 *            the crawl depth
	 * @param visitedUrls
	 *            the visited urls
	 * @return the complete web page detail DTO
	 */
	public CompleteWebPageDetailDTO crawlHtmlPageWithDepth(String url, int crawlDepth, List<String> visitedUrls);
}
