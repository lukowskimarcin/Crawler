package application;

import java.io.InputStream;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weld.StartupScene;

public class ApplicationStarter {
	
@Inject FXMLLoader fxmlLoader;

	
	public void launchJavaFXApplication(@Observes @StartupScene Stage primaryStage) {
		try {
			
			primaryStage.setTitle("Crawler");
			primaryStage.setResizable(false);
			primaryStage.setHeight(150);
			primaryStage.setWidth(300);
			
			
	//		primaryStage.getIcons().add(new Image(ApplicationStarter.class.getResourceAsStream("../resource/icon.png")));
			
		 
			
		//	ProxyCentrumPagesCrawler rootTask = new ProxyCentrumPagesCrawler("http://prx.centrump2p.com");
		//	crawler.addTask(rootTask);
			InputStream is = getClass().getResourceAsStream("Sample.fxml");
			Parent root = (Parent) fxmlLoader.load(is);
			
			
			//StackPane root = (StackPane)fxmlLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
