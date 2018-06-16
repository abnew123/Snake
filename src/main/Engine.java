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
	public static final int[] NONINVERTED_DIRECTIONS = new int[] {0,2,3,1};
	public static final int[] INVERTED_DIRECTIONS = new int[] {2,0,1,3};
	private int[] directions;
	public Engine(Game game, boolean inversion) {
		myGame = game;
		mySnakes = game.getSnakes();
		mySnake = mySnakes[0];
		getChildren().add(myGame);
		directions = inversion?INVERTED_DIRECTIONS: NONINVERTED_DIRECTIONS;
	}
//	public void startScreen() {};
	public void endScreen(Group group, Scene scene) {
		group.getChildren().clear();
		Text endScreen = new Text("your score is: " + myGame.getScore());
		endScreen.setX(scene.getWidth() / 2 - endScreen.getBoundsInLocal().getWidth()/2);
		endScreen.setY(scene.getHeight() / 2 - endScreen.getBoundsInLocal().getHeight()/2);
		group.getChildren().add(endScreen);
	};
	
	public void handleKeyPress(KeyCode code) {
		switch(code) {
			case RIGHT: 
				mySnake.setDirection(directions[0]);
				break;
			case LEFT:
				mySnake.setDirection(directions[1]);
				break;
			case UP: 
				mySnake.setDirection(directions[2]);
				break;
			case DOWN:
				mySnake.setDirection(directions[3]);
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