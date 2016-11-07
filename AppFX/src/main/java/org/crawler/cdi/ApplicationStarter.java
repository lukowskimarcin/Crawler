package org.crawler.cdi;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ApplicationStarter {
	
	@Inject 
	private FXMLLoader fxmlLoader;
	
	public void launchJavaFXApplication(@Observes @StartupScene Stage primaryStage) {
		try {
			primaryStage.setTitle("Crawler");

			InputStream image = new FileInputStream("src/main/resources/icon.png");
			primaryStage.getIcons().add(new Image(image));

			InputStream is = new FileInputStream("src/main/java/application/Sample.fxml");
			Parent root = (Parent) fxmlLoader.load(is);

			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add("src/main/java/application/application.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
