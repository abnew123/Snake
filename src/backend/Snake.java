package backend;

import java.awt.Point;

import javafx.scene.Group;

public abstract class Snake extends Group{
	public abstract void setDirection(int increment);
	public abstract boolean update(Point foodLocation);
	public abstract boolean checkIntersection(Point location);
	public abstract boolean outOfBounds(int width, int height);
	public abstract boolean alertDead();
	public abstract boolean upgrade();
}
