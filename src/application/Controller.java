package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
	
	private Stage stage;
	private Scene scene;
	
	public Controller(Stage stg, Scene scn) {
		this.stage = stg;
		this.scene = scn;
	}
	
	public Controller() {
		
	}
	
	@FXML
	public void close() {
		Platform.exit();
	}
	
	@FXML
	public void switchAndPlay() {
		stage.setScene(scene);
	}
}
