package application;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

public class GameController {
	
	@SuppressWarnings("unused")
	private Group group;
	@SuppressWarnings("unused")
	private Scene scene;
	
	private Spawner spawner;
	
	@FXML
	private Path line;
	@FXML
	protected Polygon path;
	
	public GameController() {
		this.group = null;
		this.scene = null;
	}
	
	public void setScene(Scene scn) {
		this.scene = scn;
	}
	
	public void setGroup(Group grp) {
		this.group = grp;
	}
	
	public void setSpawner(Spawner sp) {
		this.spawner = sp;
	}
	
	@FXML
	public void butTest() {
		spawner.spawn();
		spawner.translate(line);
	}
	
	@FXML
	public void clicked() {
		spawner.towerPose(path);
	}
}
