package com.crawler.webcrawler.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;


/**
 * The Class WebPageDetailsDTO.
 * 
 * @author shashanksh43
 */
public class WebPageDetailsDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 47836299469270150L;

	/** The url. */
	private String url;

	/** The title. */
	private String title;

	/** The navigations. */
	private List<Elements> navigations;

	/**
	 * Instantiates a new web page details DTO.
	 *
	 * @param url
	 *            the url
	 * @param navigations
	 *            the navigations
	 * @param title
	 *            the title
	 */
	public WebPageDetailsDTO(String url, List<Elements> navigations, String title) {
		this.url = url;
		this.navigations = navigations;
		this.title = title;
	}
	
	/**
	 * Instantiates a new web page details DTO.
	 */
	public WebPageDetailsDTO() {
		
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the navigations.
	 *
	 * @return the navigations
	 */
	public List<Elements> getNavigations() {
		return navigations;
	}

	/**
	 * Adds the navigations.
	 *
	 * @param navigations
	 *            the navigations
	 */
	public void addNavigations(Elements navigations) {
		if(navigations==null){
			this.navigations = new ArrayList<>();
		}
		this.navigations.add(navigations);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[url=" + url + ", message=" + title + "]";
	}

}
