package application;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
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
			
			
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
