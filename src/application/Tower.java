package application;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Tower extends Parent {
	
	private Circle skin;
	private int damage;
	private int range;
	
	
	public Tower(int posX, int posY) {
		this.damage = 10;
		this.range = 100;

		this.skin = new Circle(posX, posY, 20);
		skin.setFill(Color.BLACK);
		this.getChildren().add(skin);
	}

	public Tower() {
		this.damage = 10;
		this.range = 20;
		this.skin = new Circle();
		skin.setFill(Color.BLACK);
		this.getChildren().add(skin);
	}
	
	public void attack(Enemy e) {
		Circle atkZone = new Circle(skin.getCenterX(), skin.getCenterY(), range);
		atkZone.setFill(Color.TRANSPARENT);
		atkZone.setStroke(Color.BLUEVIOLET);
		this.getChildren().add(atkZone);
		if(atkZone.contains(e.getX(), e.getY()) == true) {
			Line line = new Line(skin.getCenterX(), skin.getCenterY(), e.getX(), e.getY());
			this.getChildren().add(line);
			e.setVie(this.damage);
		}
	}
}
