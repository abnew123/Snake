package backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicSnake extends Snake {
	private ImageView image = new ImageView();
	private List<Point> bodyParts;
	private boolean eaten;
	private int direction;
	public static final int AMOUNT_NEEDED_FOR_UPGRADE = 1;
	private int toNextUpgrade = AMOUNT_NEEDED_FOR_UPGRADE;
	public static final int DEFAULT_SIZE = 50;
	public BasicSnake(int size) {
		bodyParts = new ArrayList<Point>();
		image.setImage(new Image("images/brick2.gif"));
		eaten = false;
		generate(size);
		direction = 0;
		setUpGroup();
		
	}
	public boolean update(Point foodLocation) {
		eaten = checkIntersection(foodLocation);
		if(eaten) {
			eaten = false;
			toNextUpgrade--;
			bodyParts.add(0, bodyParts.get(0));
			updatePoints();
			updateGroup();
			return true;
		}
		updatePoints();
		updateGroup();
		return false;
	}
	
	//generates the initial snake
	private void generate(int size) {
		for(int i = 0; i < size; i++) {
			bodyParts.add(new Point(i * DEFAULT_SIZE,0));
		}
	}
	
	public boolean checkIntersection(Point foodLocation) {
		for(Point part: bodyParts) {
			if(part.equals(foodLocation))
				return true;
		}
		return false;
	}
	@Override
	public void setDirection(int newDirection) {
		direction = newDirection;
	}
	private void updatePoints() {
		for(int i = 0; i < bodyParts.size() - 1; i++) {
			bodyParts.set(i, bodyParts.get(i + 1));
		}
		bodyParts.set(bodyParts.size() - 1, determinePoint(bodyParts.get(bodyParts.size() - 1)));
	}
	
	private Point determinePoint(Point initial) {
		Point point = new Point(initial);
		switch(direction) {
		case(0):
			point.translate(DEFAULT_SIZE, 0);
			break;
		case(1):
			point.translate(0,DEFAULT_SIZE);
			break;
		case(2):
			point.translate(-1*DEFAULT_SIZE, 0);
			break;
		case(3):
			point.translate(0, -1*DEFAULT_SIZE);
			break;
		default:
			System.out.print("wtf");
		}
		return point;
	}
	private void updateGroup() {
		getChildren().clear();
		setUpGroup();
	}
	private void setUpGroup() {
		for(int i = 0; i < bodyParts.size(); i++) {
			ImageView node = new ImageView();
			node.setImage(image.getImage());
			node.setX(bodyParts.get(i).getX());
			node.setY(bodyParts.get(i).getY());
			node.setFitHeight(DEFAULT_SIZE);
			node.setFitWidth(DEFAULT_SIZE);
			getChildren().add(node);
		}
	}
	
	public boolean outOfBounds(int width, int height) {
		Point head = bodyParts.get(bodyParts.size() - 1);
		return (head.getX() < 0 || head.getY() < 0|| head.getX() >= width * DEFAULT_SIZE || head.getY() >= height*DEFAULT_SIZE);
	}
	
	public boolean alertDead() {
		boolean dead = false;
		for(int i = 0; i < bodyParts.size(); i++) {
			Point point = bodyParts.get(i);
			bodyParts.remove(i);
			dead = dead || checkIntersection(point);
			bodyParts.add(i,point);
		}
		return dead;
	}
	
	public boolean upgrade() {
		if(toNextUpgrade == 0) {
			toNextUpgrade = AMOUNT_NEEDED_FOR_UPGRADE;
			return true;
		}
		return false;
	}
	@Override
	public int getSize() {
		return bodyParts.size();
	}
}
