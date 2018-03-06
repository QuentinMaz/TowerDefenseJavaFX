package application;

import java.util.List;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

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
	
	public Enemy findTheNearest(List<Enemy> list) {
		Enemy target = null;
		if(!list.isEmpty()) {
			double distance = (double)range;
			for(Enemy e : list) {
				double distanceTemp = Math.sqrt(Math.pow((e.getX()-skin.getCenterX()), 2) + Math.pow((e.getY()-skin.getCenterY()), 2));
				if(distanceTemp < distance) {
					distance = distanceTemp;
					target = e;
				}
			}
		}
		return target;
	}
	
	public void attack(List<Enemy> list) {
		for(Enemy e : list) {
			double distance = Math.sqrt(Math.pow((e.getX()-skin.getCenterX()), 2) + Math.pow((e.getY()-skin.getCenterY()), 2));
			if(distance < range) {
				e.setVie(damage);
				tirAnimation(e);
				return; // = break
			}
		}
	}
	
	public void tirAnimation(Enemy e) {
		Line path = new Line(skin.getCenterX(), skin.getCenterY(), e.getX(), e.getY());
		int correction = 20;
		if(e.getOrientation() == "right") {
			path.setEndX(path.getEndX() + correction);
		} else if(e.getOrientation() == "down") {
			path.setEndY(path.getEndY() + correction);
		} else if(e.getOrientation() == "up") {
			path.setEndY(path.getEndY() - correction);
		}
		
		Circle imageTir = new Circle(skin.getCenterX(), skin.getCenterY(), 5);
		this.getChildren().add(imageTir);
		
		PathTransition animation = new PathTransition();
		animation.setNode(imageTir);
		animation.setDuration(Duration.millis(300));
		animation.setPath(path);
		animation.setCycleCount(1);
		
		animation.play();
		
		animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                animation.stop();
                getChildren().remove(imageTir);
            }
        });
	}
	
}
