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
	
	private Manager manager;
	
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
	
	public void setLooper(Manager sp) {
		this.manager = sp;
	}
	
	@FXML
	public void butTest() {
		manager.spawn();
		manager.translate(line);
	}
	
	@FXML
	public void clicked() {
		Singleton.getInstance().towerPose(path);
	}
	
	@FXML
	public void towerContent() {
		for(Tower t : Singleton.getInstance().getTowerList()) {
			System.out.println(t.toString());
		}
	}
}
