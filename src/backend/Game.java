package backend;

import javafx.scene.Group;

public abstract class Game extends Group {
	public abstract String update();
	public abstract boolean checkDead();
	public abstract Snake[] getSnakes();
	public abstract int getScore();
}
