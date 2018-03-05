package application;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Tower extends Parent {
	
	private Circle skin;
	private int damage;
	private int range;
	private List<Line> projectile;
	private int nbProjectile;
	
	public Tower(int posX, int posY) {
		this.damage = 10;
		this.range = 100;

		this.skin = new Circle(posX, posY, 20);
		skin.setFill(Color.BLACK);
		this.getChildren().add(skin);
		
		this.nbProjectile = 0;
		projectile = new ArrayList<Line>();
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
				this.projectile.add(new Line(skin.getCenterX(), skin.getCenterY(), e.getX(), e.getY()));
				this.getChildren().add(projectile.get(nbProjectile));
				nbProjectile++;
				e.setVie(damage);
				return; // = break
			}
		}
	}
	
	public void cleanProjectile() {
		if(projectile.isEmpty() == false) {
			for(int i = 0; i < this.nbProjectile; i++) {
				this.getChildren().remove(projectile.get(i));		// on supprime les corps visibles
			}
			projectile.clear();
			nbProjectile = 0;
		}
	}
}
