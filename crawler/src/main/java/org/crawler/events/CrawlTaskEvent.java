package org.crawler.events;

import org.crawler.imp.CrawlTask;

public class CrawlTaskEvent {
	
	private CrawlTask page;
	
	//czas w ms
	private long time;
	
	private double progress;
	
	private String errorMessage;

	public CrawlTaskEvent(CrawlTask page, long time) {
		this.page = page;
		this.time = time;
	}
	
	
	public CrawlTaskEvent(CrawlTask page) {
		this.page = page;
		this.time = -1;
	}
	 

	public CrawlTask getPage() {
		return page;
	}

	public long getTimeMillis() {
		return time;
	}
	
	public String getTimeSeconds() {
		return String.format("%.2f", time * 1.0 / 1000);
	}

	public double getProgress() {
		return progress;
	}


	public void setProgress(double progress) {
		this.progress = progress;
	}


	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
