package org.crawler.imp;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Przetwarzana strona
 * @author Marcin
 *
 */
public abstract class PageTask implements Serializable {
	private static final long serialVersionUID = 6857632664456273402L;
	
	@Attribute
	private String url;
	
	@Element(required=false)
	private String errorMessage;
	
	@Attribute
	private int level;

	@Element(required=false)
	private Object data;
	
	public PageTask(String url) {
		this(url, 0);
	}
	
	public PageTask(String url, int level) {
		this.url = url;
		this.level = level;
	}
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		PageTask second = (PageTask) obj;
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
	
	/**
	 * Przetwarzanie danych strony po pobraniu
	 */
	public abstract void process();  
	
	
	/**
	 * Pobieranie danych ze strony
	 */
	public abstract void parse() throws Exception;
	

	@Override
	public String toString() {
		return "Page [" +
				"\n\turl: " + url +
				"\n\tlevel: " + level +
				"\n\terrorMessage: " + errorMessage + 
				"\n\tdata: " + data + "\n]";
	}
}
