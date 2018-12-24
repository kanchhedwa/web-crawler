package com.crawler.webcrawler.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.crawler.webcrawler.dto.CompleteWebPageDetailDTO;
import com.crawler.webcrawler.dto.WebPageDetailsDTO;

/**
 * The Class CrawlerServiceImpl.
 * 
 * @author shashanksh43
 */
@Service("crawlerServiceImpl")
public class CrawlerServiceImpl implements CrawlerService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerServiceImpl.class);

	/** The Constant SCRIPT. */
	private static final String SCRIPT = "script";

	/** The Constant LINK. */
	private static final String LINK = "link";

	/** The Constant IMAGES. */
	private static final String IMAGES = "img";

	/** The Constant ANCHOR. */
	private static final String ANCHOR = "a";

	/** The Constant SRC_ATTR. */
	private static final String SRC_ATTR = "src";

	/** The Constant HREF_ATTR. */
	private static final String HREF_ATTR = "href";

	/** The Constant ABS_HREF_URL. */
	private static final String ABS_HREF_URL = "abs:href";
	
	/** The Constant ABS_SRC_URL. */
	private static final String ABS_SRC_URL = "abs:src";

	/** The Constant PROTOCOL_HTTP. */
	private static final String PROTOCOL_HTTP = "http://";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.crawler.webcrawler.services.CrawlerService#crawlHtmlPageWithDepth(
	 * java.lang.String, int, java.util.List)
	 */
	@Override
	@Timed
	@ExceptionMetered
	public CompleteWebPageDetailDTO crawlHtmlPageWithDepth(String url, int crawlDepth, List<String> visitedUrls) {
		if (crawlDepth < 0) {
			return null;
		} else {
			List<String> visitedPages = Optional.ofNullable(visitedUrls).orElse(new ArrayList<>());
			if (visitedPages.contains(url)) {
				return null;
			} else {
				visitedPages.add(url);
				final CompleteWebPageDetailDTO completeWebPageDetail = new CompleteWebPageDetailDTO(url);
				crawl(url).ifPresent(pageDetails -> {
					pageDetails.getNavigations().parallelStream().forEach(links -> {
						links.parallelStream().forEach(navigation -> {
							visitIndividualLink(url, crawlDepth, visitedPages, completeWebPageDetail, navigation);
						});

					});
				});
				return completeWebPageDetail;
			}
		}
	}

	/**
	 * Visit individual link.
	 *
	 * @param url
	 *            the url
	 * @param crawlDepth
	 *            the crawl depth
	 * @param visitedPages
	 *            the visited pages
	 * @param completeWebPageDetail
	 *            the complete web page detail
	 * @param navigation
	 *            the navigation
	 */
	private void visitIndividualLink(String url, int crawlDepth, List<String> visitedPages,
			final CompleteWebPageDetailDTO completeWebPageDetail, Element navigation) {
		if (navigation.absUrl(HREF_ATTR).startsWith(url) || navigation.absUrl(SRC_ATTR).startsWith(url)) {
			if (navigation.tag().getName().equals(ANCHOR)) {
				CompleteWebPageDetailDTO internalLinks = crawlHtmlPageWithDepth(navigation.attr(ABS_HREF_URL),
						crawlDepth - 1, visitedPages);
				if (internalLinks != null) {
					completeWebPageDetail.addInternalLinks(internalLinks.getUrl());
				}
			} else if (navigation.tag().getName().equals(IMAGES)) {
				completeWebPageDetail.addStaticContent(navigation.attr(ABS_SRC_URL));
			} else if (navigation.tag().getName().equals(SCRIPT)) {
				completeWebPageDetail.addStaticContent(navigation.attr(ABS_SRC_URL));
			} else if (navigation.tag().getName().equals(LINK)) {
				completeWebPageDetail.addStaticContent(navigation.attr(ABS_HREF_URL));
			}
		} else if (!navigation.attr(HREF_ATTR).isEmpty() && navigation.attr(HREF_ATTR).startsWith(PROTOCOL_HTTP)) {
			completeWebPageDetail.addExternalLinks(navigation.attr(HREF_ATTR));
		} else if (!navigation.attr(SRC_ATTR).isEmpty() && navigation.attr(SRC_ATTR).startsWith(PROTOCOL_HTTP)) {
			completeWebPageDetail.addExternalLinks(navigation.attr(SRC_ATTR));
		}
	}

	/**
	 * Crawl.
	 *
	 * @param url
	 *            the url
	 * @return the optional
	 */
	@Timed(name="service.time.crawl")
	@ExceptionMetered(name="service.exception.meter.crawl")
	public Optional<WebPageDetailsDTO> crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).followRedirects(false);

			Document doc = connection.get();
			String title = doc.title();
			Elements anchorNav = doc.select("a[href]");
			Elements iframeNav = doc.select("iframe[href]");
			Elements img = doc.select("[src]");
			Elements cssLink = doc.select("link[href]");

			List<Elements> navigationList = new ArrayList<>();
			navigationList.add(anchorNav);
			navigationList.add(iframeNav);
			navigationList.add(img);
			navigationList.add(cssLink);
			WebPageDetailsDTO pageDetails = new WebPageDetailsDTO(url, navigationList, title);
			return Optional.of(pageDetails);
		} catch (final IOException | IllegalArgumentException e) {
			LOGGER.error(String.format("Error while fetching webpage having url %s", url), e);
			return Optional.empty();
		}
	}

}
