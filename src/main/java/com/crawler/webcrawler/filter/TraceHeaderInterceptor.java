package com.crawler.webcrawler.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.crawler.webcrawler.constants.WebCrawlerConstants;

import brave.Span;
import brave.Tracer;

@Component
public class TraceHeaderInterceptor extends GenericFilterBean {
	
	@Autowired 
	Tracer tracer;

	@Override
	public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		logger.info("===inside TraceHeaderInterceptor Filter==");
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Span currentSpan = this.tracer.currentSpan();
		if(currentSpan != null){
			String traceId = currentSpan.context().traceIdString();
			long spanId = currentSpan.context().spanId();
			
			response.setHeader(WebCrawlerConstants.TRACE_ID, traceId);
			response.setHeader(WebCrawlerConstants.SPAN_ID, Long.toString(spanId));
		}
		
		filterChain.doFilter(request, response);
	}

}
