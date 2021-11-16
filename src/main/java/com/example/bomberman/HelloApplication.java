package com.example.bomberman;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {

  @Override
  public void start(Stage theStage) throws IOException {
    theStage.setTitle("Timeline Example");

    Group root = new Group();
    Scene theScene = new Scene(root);
    theStage.setScene(theScene);

    Canvas canvas = new Canvas(512, 512);
    root.getChildren().add(canvas);

    GraphicsContext gc = canvas.getGraphicsContext2D();

    Image earth = new Image(
            "D:\\Projects\\Bomberman\\src\\main\\resources\\com\\example\\bomberman\\earth.png");
    Image sun = new Image(
            "D:\\Projects\\Bomberman\\src\\main\\resources\\com\\example\\bomberman\\sun.png");
    Image space = new Image(
            "D:\\Projects\\Bomberman\\src\\main\\resources\\com\\example\\bomberman\\space.png");

    final long startNanoTime = System.nanoTime();

    new AnimationTimer() {
      public void handle(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;

        double x = 232 + 128 * Math.cos(t);
        double y = 232 + 128 * Math.sin(t);

        // background image clears canvas
        //gc.drawImage( space, 0, 0 );
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(earth, x, y);
        gc.drawImage(sun, 196, 196);
      }
    }.start();

    theStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}