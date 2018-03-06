package application;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class Enemy extends Parent {
		
	private Circle body;		// l'affichage 
	private Rectangle lifeBar;
	private String orientation;
	private double vie;
	
	public Enemy(int posX, int posY, double vie) {
		this.vie = vie;
		this.orientation = "";

		this.body = new Circle();
		body.setCenterX(posX);
		body.setCenterY(posY);
		body.setRadius(15);
		body.setFill(Color.ALICEBLUE);
		body.setStroke(Color.BLACK);
		this.getChildren().add(body);
		
		this.lifeBar = new Rectangle(body.getCenterX()-body.getRadius(), body.getCenterY()-body.getRadius()-10, (this.vie/100)*(2*body.getRadius()), 5);
		lifeBar.setFill(Color.RED);
		this.getChildren().add(lifeBar);
	}
	
	
	public void setOrientation(Path path) {
		double x = this.body.getCenterX();
		double y = this.body.getCenterY();
		
		if(path.contains(x, y + 1) == false && path.contains(x, y - 1) == true) {
			if(this.orientation == "down") {											// si tu descendais, tu ne peux que right
				this.orientation = "right";
			} else {
				this.orientation = "up";
			}
		} else if(path.contains(x, y + 1) == true && path.contains(x, y - 1) == false) {
			if(this.orientation == "up") {												// si tu montais, tu ne peux que right
				this.orientation = "right";
			} else {
				this.orientation = "down";
			}
		} else if(path.contains(x, y + 1) == false && path.contains(x, y - 1) == false) {
			this.orientation = "right";
		}
	}
	
	public void followLine(Path path) {
		double x = this.body.getCenterX();
		double y = this.body.getCenterY();
		double xx = this.lifeBar.getX();
		double yy = this.lifeBar.getY();
		
		this.setOrientation(path);
		
		if(orientation == "right") {
			body.setCenterX(x + 1);
			lifeBar.setX(xx + 1);
		} else if(orientation == "down") {
			body.setCenterY(y + 1);
			lifeBar.setY(yy + 1);
		} else if(orientation == "up") {
			body.setCenterY(y - 1);
			lifeBar.setY(yy - 1);
		}
	}
	
	public double getX() {
		return body.getCenterX();
	}
	
	public double getY() {
		return body.getCenterY();
	}
	
	public double getVie() {
		return this.vie;
	}
	
	public String getOrientation() {
		return this.orientation;
	}

	public void setVie(double life) {
		this.vie -= life;
		this.lifeBar.setWidth((this.vie/100)*(2*body.getRadius()));
	}
}
