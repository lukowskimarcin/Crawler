package application;

import java.util.List;
import java.util.logging.Level;

import org.crawler.IWebCrawler;
import org.crawler.app.ProxyCentrumPagesCrawler;
import org.crawler.imp.Proxy;
import org.crawler.imp.WebCrawler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class SampleController {

	@FXML
	private Button button;

	@FXML
	private ProgressIndicator indicator;

	@FXML
	private ProgressBar progressBar;

	@FXML
	void onAction(ActionEvent event) {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		IWebCrawler<List<Proxy>> proxyWebCrawler = new WebCrawler<List<Proxy>>(8);

		proxyWebCrawler.addOnCrawlingFinishedListener(e -> {
			System.out.println("FINISHED " + String.format("[%.2f sec] !!!", e.getElapsedTime() * 1.0 / 1000) );
			progressBar.setProgress(1.0);
			indicator.setProgress(1.0);
			button.setDisable(false);
		});
		
		proxyWebCrawler.addOnPageCrawlingCompletedListener(e -> 
			System.out.println("Page complete " + e.getPage().getUrl() + " [" + e.getTimeSeconds() + " sec]"));
		

		proxyWebCrawler.addOnCrawlingChangeStateListener(e -> {
			if (e.getTotalPages() != 0) {
				double percent = 1.0 * (e.getCompletePages() + e.getErrorPages() + e.getProcessingPages())
						/ e.getTotalPages();

				if (button.isDisable() && percent == 1.0) {
					percent = 0.99;
				}
				progressBar.setProgress(percent);
				indicator.setProgress(percent);
			}
		});

		ProxyCentrumPagesCrawler rootTask = new ProxyCentrumPagesCrawler("http://prx.centrump2p.com");
		proxyWebCrawler.addTask(rootTask);

		button.setDisable(true);

	}

}