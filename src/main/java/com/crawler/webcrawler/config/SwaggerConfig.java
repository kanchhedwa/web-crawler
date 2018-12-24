package com.crawler.webcrawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

	@Value("${swagger.api.url.pattern:na}")
	private String swaggerApiUrlPattern;

	@Value("${swagger.api.title:na}")
	private String swaggerTitle;

	@Value("${swagger.api.description:na}")
	private String swaggerDescription;

	@Value("${swagger.api.terms.url:na}")
	private String swaggerTermsOfServiceUrl;

	@Value("${swagger.api.contact:na}")
	private String swaggerContact;

	@Value("${swagger.api.licence:na}")
	private String swaggerLicence;

	@Value("${swagger.api.licence.url:na}")
	private String swaggerLicenceUrl;

	@Value("${swagger.api.version:na}")
	private String swaggerApiVersion;

	@Bean
	public Docket api() {
		logger.info("The Swagger api url pattern is {}", swaggerApiUrlPattern);
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.crawler.webcrawler"))
				.paths(Predicates.not(PathSelectors.regex("/error|/health|/hystrix.*"))).build().apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		logger.info(
				"The Swagger details are,  swagger.api.title: {}, swagger.api.description: {}, swagger.api.terms.url: {}, swagger.api.contact:{}, swagger.api.licence:{}, swagger.api.licence.url:{}, swagger.api.version:{}",
				swaggerTitle, swaggerDescription, swaggerTermsOfServiceUrl, swaggerContact, swaggerLicence,
				swaggerLicenceUrl, swaggerApiVersion);
		return new ApiInfoBuilder().title(swaggerTitle).description(swaggerDescription)
				.termsOfServiceUrl(swaggerTermsOfServiceUrl).license(swaggerLicence).licenseUrl(swaggerLicenceUrl)
				.version(swaggerApiVersion).build();
	}

}
