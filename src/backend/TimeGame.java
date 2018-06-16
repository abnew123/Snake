package backend;

import java.awt.Point;

public class TimeGame extends Game {

	private Snake mySnake;
	private int width;
	private int height;
	public static final int DEFAULT_SIZE = 50;
	public static final int TIME_FOR_UPGRADE = 5;
	private int timeLeft = TIME_FOR_UPGRADE;
	private int score = 0;
	public TimeGame(Snake snake, int width, int height) {
		mySnake = snake;
		this.width = width;
		this.height = height;
		getChildren().add(mySnake);
	}
	
	@Override
	public String update() {
		mySnake.update(new Point(-1,-1));
		score++;
		timeLeft--;
		if(timeLeft == 0) {
			timeLeft = TIME_FOR_UPGRADE;
			return "UPGRADE";
		}
		return "GOING";
	}

	@Override
	public boolean checkDead() {
		return mySnake.outOfBounds(width, height)||mySnake.alertDead();
	}
	@Override
	public Snake[] getSnakes() {
		return new Snake[] {mySnake};
	}
	@Override
	public int getScore() {
		return score;
	}

}
