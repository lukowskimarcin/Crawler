package org.crawler.imp;

import java.util.concurrent.FutureTask;

import org.crawler.ICrawler;
import org.crawler.Page;

/**
 * Zadanie przetworzenia strony
 * @author Marcin
 *
 * @param <T> Page<?> przetworzona strona
 */
public class PageTask<T extends Page<?>> extends FutureTask<T> {
	
	public PageTask(ICrawler<T> crawler) {
		super(crawler);
	}

}
