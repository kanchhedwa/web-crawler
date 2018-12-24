package com.crawler.webcrawler.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class CompleteWebPageDetailDTO.
 * 
 * @author shashanksh43
 */
public class CompleteWebPageDetailDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6960111378599550580L;

	/** The url. */
	private String url;

	/** The internal links. */
	private List<String> internalLinks = new ArrayList<>();

	/** The external links. */
	private List<String> externalLinks = new ArrayList<>();

	/** The static content. */
	private List<String> staticContent = new ArrayList<>();

	/**
	 * Instantiates a new complete web page detail DTO.
	 *
	 * @param url
	 *            the url
	 */
	public CompleteWebPageDetailDTO(String url) {
		this.url = url;
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
	 * Gets the internal links.
	 *
	 * @return the internal links
	 */
	public List<String> getInternalLinks() {
		return internalLinks;
	}

	/**
	 * Adds the internal links.
	 *
	 * @param internalLinks
	 *            the internal links
	 */
	public void addInternalLinks(String internalLinks) {
		this.internalLinks.add(internalLinks);
	}

	/**
	 * Gets the external links.
	 *
	 * @return the external links
	 */
	public List<String> getExternalLinks() {
		return externalLinks;
	}

	/**
	 * Adds the external links.
	 *
	 * @param externalLinks
	 *            the external links
	 */
	public void addExternalLinks(String externalLinks) {
		this.externalLinks.add(externalLinks);
	}

	/**
	 * Gets the static content.
	 *
	 * @return the static content
	 */
	public List<String> getStaticContent() {
		return staticContent;
	}

	/**
	 * Adds the static content.
	 *
	 * @param staticContent
	 *            the static content
	 */
	public void addStaticContent(String staticContent) {
		this.staticContent.add(staticContent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final java.lang.Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final CompleteWebPageDetailDTO completeWebPageDetailDTO = (CompleteWebPageDetailDTO) obj;
		return Objects.equals(url, completeWebPageDetailDTO.url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("    url: ").append(toIndentedString(url)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * To indented string.
	 *
	 * @param obj
	 *            the obj
	 * @return the string
	 */
	private String toIndentedString(final java.lang.Object obj) {
		if (obj == null) {
			return "null";
		}
		return obj.toString().replace("\n", "\n    ");
	}

}
