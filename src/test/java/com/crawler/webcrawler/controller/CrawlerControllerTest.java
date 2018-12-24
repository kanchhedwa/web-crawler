package com.crawler.webcrawler.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.MessageFormat;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.crawler.webcrawler.config.MetricService;
import com.crawler.webcrawler.constants.WebCrawlerConstants;
import com.crawler.webcrawler.constants.WebCrawlerTestConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CrawlerControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@MockBean(MetricService.class)
@TestExecutionListeners(listeners = {
		DependencyInjectionTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public class CrawlerControllerTest {

	/** The port. */
	@LocalServerPort
	private int port;

	/** The web test client. */
	@Autowired
	private WebTestClient webTestClient;

	/** The obj mapper. */
	private ObjectMapper objMapper;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		objMapper = new ObjectMapper();
		webTestClient = WebTestClient.bindToServer().baseUrl(WebCrawlerTestConstant.SERVER + port)
				.responseTimeout(Duration.ofSeconds(500000)).build();

		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test invalid input param page URL.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testInvalidInputParamPageURL() throws Exception {
		String uri = WebCrawlerTestConstant.END_POINT +"=icici.com";
		ResponseSpec respSpec = webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON).exchange();
		respSpec.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
		BodySpec<String, ?> body = respSpec.expectBody(String.class);
		assertNotNull(body.returnResult().getResponseBody());
		JsonNode bodyNode = objMapper.readTree(body.returnResult().getResponseBody());
		assertEquals(WebCrawlerConstants.BAD_REQUEST_ERROR_CODE,
				bodyNode.at("/errorMessages").get(0).at("/errorCode").asText());
		assertEquals(WebCrawlerConstants.BAD_REQUEST_ERROR_CODE,
				bodyNode.at("/errorMessages").get(0).at("/errorCode").asText());
		String message = MessageFormat.format(WebCrawlerConstants.BAD_REQUEST_URL, "icici.com");
		assertEquals(message, bodyNode.at("/errorMessages").get(0).at("/errorMessage").asText());
	}
	
	/**
	 * Test invalid input param crawler depth.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testInvalidInputParamCrawlerDepth() throws Exception {
		String uri = WebCrawlerTestConstant.END_POINT + "=http://www.icici.com&"+WebCrawlerConstants.CRAWLER_DEPTH_PARAM+"=abc";
		ResponseSpec respSpec = webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON).exchange();
		respSpec.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
		BodySpec<String, ?> body = respSpec.expectBody(String.class);
		assertNotNull(body.returnResult().getResponseBody());
		JsonNode bodyNode = objMapper.readTree(body.returnResult().getResponseBody());
		assertEquals(WebCrawlerConstants.BAD_REQUEST_ERROR_CODE,
				bodyNode.at("/errorMessages").get(0).at("/errorCode").asText());
		assertEquals(WebCrawlerConstants.BAD_REQUEST_ERROR_CODE,
				bodyNode.at("/errorMessages").get(0).at("/errorCode").asText());
		String message = MessageFormat.format(WebCrawlerConstants.BAD_REQUEST_CRAWL_DEPTH, "abc");
		assertEquals(message, bodyNode.at("/errorMessages").get(0).at("/errorMessage").asText());
	}

}
