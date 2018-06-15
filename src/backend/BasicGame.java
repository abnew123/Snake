package backend;

import java.awt.Point;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicGame extends Game{
	
	private Snake mySnake;
	private ImageView food = new ImageView();
	private int width;
	private int height;
	public static final int DEFAULT_SIZE = 50;
	private int score = 0;
	public BasicGame(Snake snake, int width, int height) {
		food.setX(((int)((Math.random() * width)) * DEFAULT_SIZE));
		food.setY(((int)((Math.random() * height)) * DEFAULT_SIZE));
		mySnake = snake;
		this.width = width;
		this.height = height;
		food.setImage(new Image("images/rcd.jpeg"));
		food.setFitHeight(DEFAULT_SIZE);
		food.setFitWidth(DEFAULT_SIZE);
		getChildren().add(mySnake);
		getChildren().add(food);
	}
	@Override
	public String update() {
		boolean eaten = mySnake.update(new Point((int)(food.getX()), (int)(food.getY())));
		if(eaten) {
			score++;
			generateFood();
		}
		if(mySnake.upgrade()) {
			return "UPGRADE";
		}
		return "GOING";
	}
	
	private void generateFood() {
		while(mySnake.checkIntersection(new Point((int)(food.getX()), (int)(food.getY())))) {
			food.setX((int)(Math.random() * width) * DEFAULT_SIZE);
			food.setY((int)(Math.random() * height) * DEFAULT_SIZE);
		}
		
	
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
