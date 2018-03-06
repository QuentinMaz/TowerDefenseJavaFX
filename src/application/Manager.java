package application;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.shape.Path;

public class Manager {
	
	public Manager() {
		
	}
	
	public void spawn() {
		int newEnemy = 2 * Main.numeroVague + 2;
		for(int i = 0; i < newEnemy; i++) {
			Enemy temp = new Enemy(-40 * Singleton.getInstance().getNbEnemy(), 125, 100);
			Singleton.getInstance().addEnemy(temp);
		}
		(Main.numeroVague)++;
		System.out.println(Singleton.getInstance().getEnemyList().toString());
	}
	
	public void translate(Path path) {
		final LongProperty lastUpdate = new SimpleLongProperty(0);
		final LongProperty fps = new SimpleLongProperty(0);
		
		new AnimationTimer() {
			public void handle(long now) {
				// on fait une update globale toutes les 60ième de secondes <=> 60fps
				if(now - fps.getValue() >= 10_000_000) {
					for(int i = 0; i < Singleton.getInstance().getNbEnemy(); i++) {
						Enemy e = Singleton.getInstance().getEnemyList().get(i);
						if(e.getX() < 1280 && e.getVie() != 0) {
							e.followLine(path);
						}else {
							Singleton.getInstance().setNbEnemy(Singleton.getInstance().getNbEnemy() - 1);
							Singleton.getInstance().destroy(i);		// on le retire la liste et on supprime son affichage
						}
					}
					fps.setValue(now);
				}
				// si la différence avec la lastUpdate est supérieure à 1s, on boucle et on update lastUpdate
				if(now - lastUpdate.getValue() >= 500_000_000) {		
					for(int i = 0; i < Singleton.getInstance().getNbTower(); i++) {
						Singleton.getInstance().getTowerList().get(i).attack(Singleton.getInstance().getEnemyList());
					}
					lastUpdate.setValue(now);
				}
			}
		}.start();
	}
}
