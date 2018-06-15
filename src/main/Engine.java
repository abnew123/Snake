package main;

import backend.Game;
import backend.Snake;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class Engine extends Group{
	
    private boolean gameStarted;
    private boolean paused;
    private boolean gameEnded;
    private boolean restartGame;
	private Game myGame;
	private Snake[] mySnakes;
	private Snake mySnake;
	public Engine(Game game) {
		myGame = game;
		mySnakes = game.getSnakes();
		mySnake = mySnakes[0];
		getChildren().add(myGame);
	}
	public void startScreen() {};
	public void endScreen(Group group, Scene scene) {
		group.getChildren().clear();
		Text endScreen = new Text("your score was: " + myGame.getScore());
		endScreen.setX(scene.getWidth() / 2 - endScreen.getBoundsInLocal().getWidth()/2);
		endScreen.setY(scene.getHeight() / 2 - endScreen.getBoundsInLocal().getHeight()/2);
		group.getChildren().add(endScreen);
	};
	
	public void handleKeyPress(KeyCode code) {
		switch(code) {
			case RIGHT: 
				mySnake.setDirection(0);
				break;
			case LEFT:
				mySnake.setDirection(2);
				break;
			case UP: 
				mySnake.setDirection(3);
				break;
			case DOWN:
				mySnake.setDirection(1);
				break;
			case ENTER:
				if(!gameStarted) {
					gameStarted = true;
				}
				if(gameEnded) {
					gameEnded = false;
					gameStarted = false;
					restartGame = true;
				}
				break;
			case P:
				paused = !paused;
				break;
			default:
				break;
				
		}
	}
	
	public String update() {
		if(restartGame) {
			restartGame = false;
			return "RESTART";
		}
		if(paused||!gameStarted) 
			return "NOT GOING";
		if(gameEnded) {
			return "DEAD";
		}
		
		if(myGame.checkDead()) {
			gameEnded = true;
			return "DEAD";
		}
		String status = myGame.update();
		return status;
	}
}