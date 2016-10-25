package org.crawler.imp;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Przetwarzana strona
 * @author Marcin
 *
 * @param <T> typ danych pobierany dla strony
 */
public class PageWrapper implements Serializable {
	private static final long serialVersionUID = 6857632664456273402L;
	
	@Attribute
	private String url;
	
	@Element(required=false)
	private String errorMessage;
	
	@Attribute
	private int level;

	@Element(required=false)
	private Object data;
	
	
	public PageWrapper(){
	}
	
	public PageWrapper(String url) {
		this(url, 0);
	}
	
	public PageWrapper(String url, int level) {
		this.url = url;
		this.level = level;
	}
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		PageWrapper second = (PageWrapper) obj;
		return  url.equals(second.getUrl());
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	@Override
	public String toString() {
		return "Page [" +
				"\n\turl: " + url +
				"\n\tlevel: " + level +
				"\n\terrorMessage: " + errorMessage + "\n]";
	}
}
