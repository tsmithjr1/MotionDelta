package com.motiondelta;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

// Initializes the JavaFX user interface for MotionDelta
public class TestFX extends Application {

	// Launches the JavaFX application
	public static void main(String[] args) {
		launch(args);

	}
	
	// Initializes the primary application window and its contents
	@Override
	public void start(Stage window) {
		window.setTitle("MotionDelta");
		
		// Creates the root layout container for the user interface
		BorderPane root = new BorderPane();
		
		// Creates the display area for the live camera feed
		ImageView cameraArea = new ImageView();
				
		// Creates a label that identifies the camera feed display area
		Label cameraFeedLabel = new Label();
	
		// Creates a container that groups the camera feed label and display area
		VBox cameraContainer = new VBox();
		
		// Adds the camera feed label and display area to the camera container
		cameraContainer.getChildren().addAll(cameraFeedLabel, cameraArea);
		
		// Creates the button used to start camera capture
		Button startButton = new Button();
		
		// Creates the button used to stop camera capture
		Button stopButton = new Button();
		
		// Creates a container that groups the start and stop buttons
		HBox buttonContainer = new HBox();
		
		// Adds the start and stop buttons to the button container
		buttonContainer.getChildren().addAll(startButton, stopButton);
		
		// Places the camera container in the center of the application window
		root.setCenter(cameraContainer);
				
		// Places the button container at the bottom of the application window
		root.setBottom(buttonContainer);
	
		// Creates the scene with the initial window dimensions
		Scene scene = new Scene (root, 800, 500);
		
		// Sets the scene and displays the application window
		window.setScene(scene);
		window.show();
	
		
		
		
		
	
		
		
	}

}
