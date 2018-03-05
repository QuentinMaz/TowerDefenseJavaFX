package application;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Spawner {
	
	private Group group;
	private List<Enemy> enemyList;
	private List<Tower> towerList;
	private int nbEnemy;
	private int nbTower;
	private boolean towerReady;			// si on clique et que on est en train de mettre une tour alors OK
	
	public Spawner(Group grp) {
		this.towerReady = false;
		
		this.nbTower = 0;
		this.nbEnemy = 0;
		this.group = grp;
		enemyList = new ArrayList<Enemy>();
		towerList = new ArrayList<Tower>();
	}
	
	// --------------------------------------------------- ENEMY -------------------------------------------------------------
	
	public void add(Enemy e) {
		enemyList.add(e);
	}

	public void spawn() {
		int newEnemy = 2 * Main.numeroVague + 2;
		for(int i = 0; i < newEnemy; i++) {
			Enemy temp = new Enemy(-40 * nbEnemy, 125, 100);
			enemyList.add(temp);
			group.getChildren().add(enemyList.get(this.nbEnemy));
			(this.nbEnemy)++;
		}
		(Main.numeroVague)++;
		System.out.println(enemyList.toString());
	}
	
	public void destroy(int index) {
		group.getChildren().remove(enemyList.get(index));
		enemyList.remove(index);
	}
	
	
	public void translate(Path path) {
		final LongProperty lastUpdate = new SimpleLongProperty(0);
		final LongProperty fps = new SimpleLongProperty(0);
		final LongProperty projectilePersistence = new SimpleLongProperty(0);
		
		new AnimationTimer() {
			public void handle(long now) {
				// on fait une update globale toutes les 60ième de secondes <=> 60fps
				if(now - fps.getValue() >= 16_666_667) {
					for(int i = 0; i < nbEnemy; i++) {
						Enemy e = enemyList.get(i);
						if(e.getX() < 1280 && e.getVie() != 0) {
							e.followLine(path);
						}else {
							nbEnemy --;
							destroy(i);		// on le retire la liste et on supprime son affichage
						}
					}
					fps.setValue(now);
				}
				// si la différence avec la lastUpdate est supérieure à 1s, on boucle et on update lastUpdate
				if(now - lastUpdate.getValue() >= 500_000_000) {		
					for(int i = 0; i < nbTower; i++) {
						towerList.get(i).attack(enemyList);
					}
					lastUpdate.setValue(now);
				}
				// on clean les projectiles toutes les 2 secondes
				if(now - projectilePersistence.getValue() >= 2_000_000_000) {		
					for(int i = 0; i < nbTower; i++) {
						towerList.get(i).cleanProjectile();
					}
					projectilePersistence.setValue(now);
				}
			}
		}.start();
	}
	
	// ------------------------------------------------------- TOWER ---------------------------------------------------------------------------
	
	public void towerPose(Polygon path) {
		Circle temp = new Circle();
		temp.setRadius(20);
		temp.setFill(Color.RED);
		group.getChildren().add(temp);
		
		EventHandler<MouseEvent> filter = new EventHandler<MouseEvent>() {		// on crée un cercle temporaire sur la souris
		    public void handle(MouseEvent e) {
				temp.setCenterX(e.getSceneX());
				temp.setCenterY(e.getSceneY());
		    }
		};
		
		group.addEventFilter(MouseEvent.MOUSE_MOVED, filter);
		
		towerReady = true;	// tour prete 
		towerBuild(filter, temp, path);
	}
	
	public void towerBuild(EventHandler<MouseEvent> filter, Circle node, Polygon path) { 
		EventHandler<MouseEvent> filter1 = new EventHandler<MouseEvent>() {			// si on clique sachant que la tour est prete on pose la vraie tour
		    public void handle(MouseEvent e) {
		    	Shape s = Shape.intersect(node, path);
				if(towerReady == true && s.getBoundsInParent().isEmpty() == true) {
					Tower tower = new Tower((int)Math.round(e.getSceneX()), (int)Math.round(e.getSceneY()));
					towerList.add(tower);
					group.getChildren().add(towerList.get(nbTower));
					nbTower ++;
					
					towerReady = false;
					
					group.removeEventFilter(MouseEvent.MOUSE_MOVED, filter);
					group.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);	// on supprime les filtres
					group.getChildren().remove(node);
					System.out.println("done !"+towerList.toString());
				}
		    }
		};
		group.addEventFilter(MouseEvent.MOUSE_CLICKED, filter1);
	}
}
