package com.example.bomberman.gameEngine;

import com.example.bomberman.game.GameManager;
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
  private AbstractGame game;

  /**
   * Bắt buộc phải để public cho constructor ko có tham số
   */
  public GameContainer() {
    canvas = new Canvas(1280, 720);
    renderer = new Renderer(canvas);
    game = new GameManager();
  }

  @Override
  public void start(Stage stage) throws IOException {
    try {
      Group root = new Group();
      Scene theScene = new Scene(root);
      Input.pollScene(theScene);

      stage.setTitle("Bomberman");
      stage.setScene(theScene);
      root.getChildren().add(canvas);

      Input.pollScene(theScene);

      //main game loop
      new AnimationTimer() {
        @Override
        public void handle(long currentNanoTime) {
          try {
            //TODO: main game loop
            game.update(currentNanoTime);
            Input.update();
            renderer.clear();
            renderer.render();
          } catch (NullPointerException e) {
            e.printStackTrace();
            System.exit(-1);
          }
        }
      }.start();

      stage.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void go() {
    launch();
  }
}
