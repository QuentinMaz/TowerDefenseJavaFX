package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static int numeroVague = 0;
	private Manager manager;

	public void start(Stage primaryStage) throws Exception {
		
		// ---------------- Base -----------------------------------------
		
		primaryStage.setTitle("Tower Defense");
		this.manager = new Manager();

		// ---------------- GameScene --------------------------------------------
		
		GameController gameController = new GameController();
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game.fxml")); 
		gameLoader.setController(gameController);										// vue jeu
		Parent stepGame = gameLoader.load();		// le seul Parent qui peut load() est une Region
		Group root = new Group(stepGame);			// on crée un Group à partir de notre Region... ca marche...
		Scene game = new Scene(root);				// on ajoute la scene non pas à stepGame mais a root :)
		
		Singleton.getInstance().setGroup(root);
		
		gameController.setGroup(root);
		gameController.setScene(game);
		gameController.setLooper(manager);	
		
		// ---------------- MenuScene --------------------------------------------
		
		Controller menuController = new Controller(primaryStage, game);
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml")); 
		menuLoader.setController(menuController);										// vue menu
		Parent stepStart = menuLoader.load();
		
		Scene menu = new Scene(stepStart);
		
		primaryStage.setScene(menu); // on démarre avec la scène du menu
		
		// --------------- Show() -----------------------------------------------
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}
}
