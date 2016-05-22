package org.crawler.imp;

import java.io.Serializable;

/**
 * Przetwarzana strona
 * @author Marcin
 *
 * @param <T> typ danych pobierany dla strony
 */
public class PageWrapper<T> implements Serializable {
	private static final long serialVersionUID = 6857632664456273402L;
	
	private String url;
	private String errorMessage;
	private int level;
	private T data;
	
	public PageWrapper(String url) {
		this(url, 0);
	}
	
	public PageWrapper(String url, int level) {
		this.url = url;
		this.level = level;
	}
	
	@Override
	public boolean equals(Object obj) {
		PageWrapper<?> second = (PageWrapper<?>) obj;
		return  url.equals(second.getUrl());
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
