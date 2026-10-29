package com.motiondelta;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

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
		
		// Creates the scene with the initial window dimensions
		Scene scene = new Scene (root, 800, 500);
		
		// Sets the scene and displaying the application window
		window.setScene(scene);
		window.show();
	
		
	}

}
