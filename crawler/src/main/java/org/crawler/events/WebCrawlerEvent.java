package org.crawler.events;

public class WebCrawlerEvent {
	
	int visitedPages;
	
	int errorPages;
	
	int completePages;
	
	int processingPages;
	
	int rejectedPages;
	
	int totalPages;
	
	long elapsedTime;

	/**
	 * Liczba unikalnych odwiedzonych stron
	 * @return
	 */
	public int getVisitedPages() {
		return visitedPages;
	}

	public void setVisitedPages(int visitedPages) {
		this.visitedPages = visitedPages;
	}

	/**
	 * Liczba stron zawierajacych bledy
	 * @return
	 */
	public int getErrorPages() {
		return errorPages;
	}

	public void setErrorPages(int errorPages) {
		this.errorPages = errorPages;
	}

	/**
	 * Liczba poprawnie przetworzonych stron
	 * @return
	 */
	public int getCompletePages() {
		return completePages;
	}

	public void setCompletePages(int completePages) {
		this.completePages = completePages;
	}

	/**
	 * Liczba aktualnie przetwarzanych stron
	 * @return
	 */
	public int getProcessingPages() {
		return processingPages;
	}

	public void setProcessingPages(int processingPages) {
		this.processingPages = processingPages;
	}

	/**
	 * Łączna liczba przetwarzanych stron (wraz z powtarzającymi się URL)
	 * @return
	 */
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	/**
	 * Liczba odrzuconych stron
	 * @return
	 */
	public int getRejectedPages() {
		return rejectedPages;
	}
	
	public void setRejectedPages(int rejectedPages) {
		this.rejectedPages = rejectedPages;
	}
	
	
	/**
	 * Czas miniony od początku pracy crawlera w ms
	 * @return
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public double getProgress(){
		if(totalPages==0) {
			return 0;
		}
		
		double percent = 1.0 * (completePages + errorPages +  rejectedPages) / totalPages;
		return percent;
	}
	
	
	@Override
	public String toString() {
		return "Crawler statistic [" +
				"\n\tvisitedPages: " + visitedPages +
				"\n\terrorPages: " + errorPages +
				"\n\tcompletePages: " + completePages +
				"\n\tprocessingPages: " + processingPages +
				"\n\trejectedPages: " + rejectedPages +
				"\n\ttotalPages: " + totalPages +
				"\n\telapsedTime: " +  String.format("%.2f s\n]", elapsedTime*1.0/1000);
	}

}
