package com.example.bomberman.gameEngine;

import com.example.bomberman.game.Map;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

    Input.pollScene(theScene);

    Map mm = new Map("src/main/resources/com/example/bomberman/gameEngine/levels/Level1.txt");
    mm.readMap();

    //main game loop
    new AnimationTimer() {
      @Override
      public void handle(long currentNanoTime) {
        //TODO: main game loop
        renderer.clear();
        renderer.render(mm.getTiles());
      }
    }.start();

    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
