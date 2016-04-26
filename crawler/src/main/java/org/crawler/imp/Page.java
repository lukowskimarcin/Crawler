package org.crawler.imp;

import java.io.Serializable;

/**
 * Przetwarzana strona
 * @author Marcin
 *
 * @param <T> typ danych pobierany dla strony
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 6857632664456273402L;
	
	private String url;
	private String errorMessage;
	private int level;
	private T data;
	
	public Page(String url) {
		this(url, 0);
	}
	
	
	public Page(String url, int level) {
		this.url = url;
		this.level = level;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
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

}
