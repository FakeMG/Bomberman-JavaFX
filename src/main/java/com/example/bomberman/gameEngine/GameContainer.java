package com.example.bomberman.gameEngine;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GameContainer extends Application {

  private Renderer renderer;
  private Canvas canvas;

  public GameContainer() {
    canvas = new Canvas(1280, 720);
    renderer = new Renderer(canvas);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Group root = new Group();
    Scene theScene = new Scene(root);

    stage.setTitle("Bomberman");
    stage.setScene(theScene);
    root.getChildren().add(canvas);

    //main game loop
    new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        //TODO: main game loop
        renderer.render();
      }
    }.start();

    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
