package org.crawler.imp;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.crawler.ICrawler;
import org.crawler.PageStatus;

/**
 * Klasa bazowa przetwarzanych stron
 * @author Marcin
 *
 * @param <T> typ danych pobierany dla strony
 */
public  class Page<T>   implements Serializable {
	private static final long serialVersionUID = 6857632664456273402L;
	
	private String url;
	private PageStatus status;
	private String errorMessage;
	private int level;
	private T data;
	
	
	public Page(String url, int level) {
		status = PageStatus.NOT_VISITED;
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

	public PageStatus getStatus() {
		return status;
	}

	public void setStatus(PageStatus status) {
		this.status = status;
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
