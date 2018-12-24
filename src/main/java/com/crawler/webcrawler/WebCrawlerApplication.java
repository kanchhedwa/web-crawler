package com.crawler.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class WebCrawlerApplication.
 * 
 * @author shashanksh43
 */
@EnableSwagger2
@SpringBootApplication
public class WebCrawlerApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApplication.class, args);
	}

}

