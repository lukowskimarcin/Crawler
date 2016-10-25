package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;

import org.crawler.ICrawlTaskListener;
import org.crawler.IEventListener;
import org.crawler.events.CrawlTaskEvent;

/**
 * Podstawowe zdarzenia obslugiwane przez przetwarzane strony
 * @author Marcin
 *
 * @param 
 */
public class CrawlTaskBaseListener implements ICrawlTaskListener {
	
	protected List<IEventListener<CrawlTaskEvent>> onPageCrawlingStartListener;
	protected List<IEventListener<CrawlTaskEvent>> onPageCrawlingCompletedListener;
	protected List<IEventListener<CrawlTaskEvent>> onPageCrawlingFailedListener;
	protected List<IEventListener<CrawlTaskEvent>> onPageAlreadyVisitedListener;
	protected List<IEventListener<CrawlTaskEvent>> onPageProcessingProgressListener;

	@Override
	public List<IEventListener<CrawlTaskEvent>> getOnPageCrawlingStartListener() {
		return onPageCrawlingStartListener;
	}
	
	@Override
	public void addOnPageCrawlingStartListener(IEventListener<CrawlTaskEvent> listener) {
		if(listener!=null) {
			if(onPageCrawlingStartListener==null) {
				onPageCrawlingStartListener = new ArrayList<IEventListener<CrawlTaskEvent>>();
			}
			onPageCrawlingStartListener.add(listener);
		}
	}
	
	@Override
	public List<IEventListener<CrawlTaskEvent>> getOnPageCrawlingCompletedListener() {
		return onPageCrawlingCompletedListener;
	}
	
	@Override
	public void addOnPageCrawlingCompletedListener(IEventListener<CrawlTaskEvent> listener) {
		if(listener!=null) {
			if(onPageCrawlingCompletedListener==null) {
				onPageCrawlingCompletedListener = new ArrayList<IEventListener<CrawlTaskEvent>>();
			}
			onPageCrawlingCompletedListener.add(listener);
		}
	}
	 
	@Override
	public List<IEventListener<CrawlTaskEvent>> getOnPageCrawlingFailedListener() {
		return onPageCrawlingFailedListener;
	}
	
	@Override
	public void addOnPageCrawlingFailedListener(IEventListener<CrawlTaskEvent> listener) {
		if(listener!=null) {
			if(onPageCrawlingFailedListener==null) {
				onPageCrawlingFailedListener = new ArrayList<IEventListener<CrawlTaskEvent>>();
			}
			onPageCrawlingFailedListener.add(listener);
		}
	}
	
	@Override
	public List<IEventListener<CrawlTaskEvent>> getOnAlreadyVisitedListener() {
		return onPageAlreadyVisitedListener;
	}
	
	@Override
	public void addOnAlreadyVisitedListener(IEventListener<CrawlTaskEvent> listener) {
		if(listener!=null) {
			if(onPageAlreadyVisitedListener==null) {
				onPageAlreadyVisitedListener = new ArrayList<IEventListener<CrawlTaskEvent>>();
			}
			onPageAlreadyVisitedListener.add(listener);
		}
	}
	
	@Override
	public List<IEventListener<CrawlTaskEvent>> getOnPageProcessingProgressListener() {
		return onPageProcessingProgressListener;
	}
	
	@Override
	public void addOnPageProcessingProgressListener(IEventListener<CrawlTaskEvent> listener) {
		if(listener!=null) {
			if(onPageProcessingProgressListener==null) {
				onPageProcessingProgressListener = new ArrayList<IEventListener<CrawlTaskEvent>>();
			}
			onPageProcessingProgressListener.add(listener);
		}
	}
	
}
