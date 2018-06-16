package backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PoisonGame extends Game {
	private Snake mySnake;
	private int width;
	private int height;
	public static final int DEFAULT_SIZE = 50;
	public static final int TIME_FOR_SPAWN = 5;
	private int timeLeft = TIME_FOR_SPAWN;
	private List<ImageView> poisonList;
	private int score = 0;
	public PoisonGame(Snake snake, int width, int height) {
		mySnake = snake;
		this.width = width;
		this.height = height;
		poisonList = new ArrayList<ImageView>();
		getChildren().add(mySnake);
	}
	
	@Override
	public String update() {
		mySnake.update(new Point(-1,-1));

		timeLeft--;
		if(timeLeft == 0) {
			score++;
			timeLeft = TIME_FOR_SPAWN;
			ImageView newPoison = generatePoison();
			poisonList.add(newPoison);
			getChildren().add(newPoison);
		}
		return "GOING";
	}

	@Override
	public boolean checkDead() {
		for(ImageView poison: poisonList) {
			if(mySnake.checkIntersection(new Point((int)poison.getX(), (int)poison.getY()))) {
				return true;
			}
		}
		return mySnake.outOfBounds(width, height)||mySnake.alertDead();
	}
	
	private ImageView generatePoison() {
		ImageView poison = new ImageView();
		poison.setImage(new Image("images/rcd.jpeg"));
		poison.setX((int)(Math.random() * width) * DEFAULT_SIZE);
		poison.setY((int)(Math.random() * height) * DEFAULT_SIZE);
		while(mySnake.checkIntersection(new Point((int)(poison.getX()), (int)(poison.getY())))) {
			poison.setX((int)(Math.random() * width) * DEFAULT_SIZE);
			poison.setY((int)(Math.random() * height) * DEFAULT_SIZE);
		}
		poison.setFitHeight(DEFAULT_SIZE);
		poison.setFitWidth(DEFAULT_SIZE);
		return poison;
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
