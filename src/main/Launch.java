package main;

import backend.TimeGame;
import backend.UnchangeableSnake;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Launch extends Application{
	
	private int frames = 2;
    public static final int INITIAL_DELAY = 500;
    private final Paint BACKGROUND = Color.BEIGE;
    private static final int SIZE = 400;
    public static final int INITIAL_WIDTH = SIZE/50;
	public static final int INITIAL_HEIGHT= SIZE/50;
	public static final int STARTING_SIZE = 4;
	private Stage myStage;
    private Scene myScene;
    private Group myGroup;
    private Engine myEngine;
    private Timeline myTimeline = new Timeline();
    private KeyFrame myKeyFrame;
    public static void main(String[] args) {
		launch(args); 
	}
	@Override
	public void start(Stage stage) {
		myStage = stage;
		initialize(myStage);
		
	}
	
	/**
	 * main game loop
	 */
	private void step() {
		String status = myEngine.update();
		if(status.equals("DEAD")) {
			myEngine.endScreen(myGroup, myScene);
		}
		if(status.equals("RESTART")){
			frames = 2;
			myStage.close();
			initialize(myStage);
		}
		if(status.equals("UPGRADE")) {
			System.out.println("hi");
			frames++;
			updateTime();
		}
	}
	
	private void initialize(Stage stage) {
		myGroup = new Group();
		myScene = new Scene(myGroup, SIZE, SIZE, BACKGROUND);
		myEngine = new Engine(new TimeGame(new UnchangeableSnake(STARTING_SIZE), INITIAL_WIDTH, INITIAL_HEIGHT));
		myGroup.getChildren().add(myEngine);
        stage.setScene(myScene);
        stage.show();
        myScene.setOnKeyPressed(e -> myEngine.handleKeyPress(e.getCode()));
        setUpTime();
	}
	
	private void setUpTime() {
		myKeyFrame = new KeyFrame(Duration.millis(INITIAL_DELAY), e -> step());
        if(myTimeline.getStatus() == Status.RUNNING) {
        		myTimeline.stop();
        }
        myTimeline = new Timeline();
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(myKeyFrame);
        myTimeline.play();
	}
	
	private void updateTime() {
		myTimeline.stop();
		myTimeline.getKeyFrames().remove(myKeyFrame);
		myKeyFrame = new KeyFrame(Duration.millis(1000/frames), e -> step());
		myTimeline.getKeyFrames().add(myKeyFrame);
		myTimeline.play();
		
	}
}
