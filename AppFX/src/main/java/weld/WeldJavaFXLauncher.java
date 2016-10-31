package weld;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.util.AnnotationLiteral;

import org.jboss.weld.environment.se.*;

public class WeldJavaFXLauncher extends Application {
    /**
     * Nothing special, we just use the JavaFX Application methods to boostrap
     * JavaFX
     */
    public static void main(String[] args) {
        Application.launch(WeldJavaFXLauncher.class, args);
    }

    @SuppressWarnings("serial")
    @Override
    public void start(final Stage primaryStage) throws Exception {
    	
    	
        // Let's initialize CDI/Weld.
        WeldContainer weldContainer = CDIUtil.instance().getWeldContainer();
        // Make the application parameters injectable with a standard CDI
        // annotation
        CDIUtil.instance().lookup(ApplicationParametersProvider.class).setParameters(getParameters());
        
        // Now that JavaFX thread is ready
        // let's inform whoever cares using standard CDI notification mechanism:
        // CDI events
        weldContainer.event().select(Stage.class, new AnnotationLiteral<StartupScene>() {}).fire(primaryStage);
    }
}
