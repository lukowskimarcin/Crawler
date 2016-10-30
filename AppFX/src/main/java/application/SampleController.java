package application;

import java.util.logging.Level;

import javax.inject.Inject;

import org.crawler.IWebCrawler;
import org.crawler.app.ProxyCentrumPagesCrawler;
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
	private Button stop;

	@FXML
	private ProgressIndicator indicator;

	@FXML
	private ProgressBar progressBar;
	
	@Inject
	private WebCrawler proxyWebCrawler;
	
	public SampleController() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	
	private void init() {
		proxyWebCrawler.addOnCrawlingFinishedListener(e -> {
			System.out.println("FINISHED " + String.format("[%.2f sec] !!!", e.getElapsedTime() * 1.0 / 1000) );
			progressBar.setProgress(1.0);
			indicator.setProgress(1.0);
			button.setDisable(false);
		});
		
		proxyWebCrawler.addOnPageCrawlingCompletedListener(e -> 
			System.out.println("Page complete " + e.getPage().getUrl() + " [" + e.getTimeSeconds() + " sec]"));
		

		proxyWebCrawler.addOnCrawlingChangeStateListener(e -> {
			progressBar.setProgress(e.getProgress());
			indicator.setProgress(e.getProgress());
		});
	}
	

	@FXML
	void onStartAction(ActionEvent event) {
		init();
		
		ProxyCentrumPagesCrawler rootTask = new ProxyCentrumPagesCrawler("http://prx.centrump2p.com");
		proxyWebCrawler.addTask(rootTask);
		button.setDisable(true);
	}

	@FXML
	void onStopAction(ActionEvent event) {
		button.setDisable(false);
		proxyWebCrawler.shutdownAndAwaitTermination();
	}
}