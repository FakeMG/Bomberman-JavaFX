package com.example.bomberman.gameEngine;

import com.example.bomberman.game.GameManager;
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
    canvas = new Canvas(768, 768);
    renderer = new Renderer(canvas);
    game = new GameManager();
  }

  @Override
  public void start(Stage stage) {
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
        //Timer
        double startTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;

        @Override
        public void handle(long currentNanoTime) {
          try {
            //calculate time
            passedTime = currentNanoTime / 1000000000.0 - startTime;
            startTime = currentNanoTime / 1000000000.0;

            //TODO: main game loop
            game.update(passedTime);
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

  public static void go() {
    launch();
  }
}
