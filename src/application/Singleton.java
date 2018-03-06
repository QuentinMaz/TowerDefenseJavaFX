package application;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Singleton {
	
	private Group group;
	private List<Tower> towerList;
	private List<Enemy> enemyList;
	private int nbEnemy;
	private int nbTower;
	private boolean towerReady;			// si on clique et que on est en train de mettre une tour alors OK
	
	// constructeur privé
    private Singleton() {
    	towerList = new ArrayList<Tower>();
    	enemyList = new ArrayList<Enemy>();
    	nbEnemy = 0;
    	nbTower = 0;
    	this.towerReady = false;
    }
 
    // instance unique pré initialisée
    private static Singleton INSTANCE = new Singleton();
     
    // point d'accès pour l'instance unique du singleton
    public static Singleton getInstance() {   
    	return INSTANCE;
    }
    
    // ------------------------------------------------------ ENEMY --------------------------------------------------------------
    
    public void addEnemy(Enemy e) {
		enemyList.add(e);
		group.getChildren().add(enemyList.get(nbEnemy));
		nbEnemy++;
	}
    
	public void destroy(int index) {
		group.getChildren().remove(enemyList.get(index));
		enemyList.remove(index);
	}
    
	// ------------------------------------------------------ TOWER --------------------------------------------------------------
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
    
	// ------------------------------------------------------ SETTERS --------------------------------------------------------------
    
    public void setGroup(Group grp) {
    	this.group = grp;
    }
    
    public void setNbEnemy(int num) {
    	this.nbEnemy = num;
    }
    
    public void setNbTower(int num) {
    	this.nbTower = num;
    }

	// ------------------------------------------------------ GETTERS ---------------------------------------------------------------
    
    public int getNbEnemy() {
    	return this.nbEnemy;
    }
    
    public List<Enemy> getEnemyList() {
    	return enemyList;
    }
    
    public int getNbTower() {
    	return this.nbTower;
    }
    
    public List<Tower> getTowerList() {
    	return towerList;
    }
    
    public Group getGroup() {
    	return this.group;
    }
}
