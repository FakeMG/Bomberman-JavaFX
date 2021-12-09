package com.example.bomberman.gameEngine;

import com.example.bomberman.game.GameManager;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  private Renderer renderer;
  private Canvas canvas;
  private AbstractGame game;

  public void switchToScene1(ActionEvent event) throws IOException {
    canvas = new Canvas(GameContainer.WIDTH, GameContainer.HEIGHT);
    renderer = new Renderer(canvas);
    game = new GameManager();

    Group root = new Group();
    Scene theScene = new Scene(root);
    root.getChildren().add(canvas);
    Input.pollScene(theScene);

    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    stage.setTitle("Bomberman");
    stage.setScene(theScene);

//    main game loop
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
  }

  public void exit(ActionEvent event) {
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    stage.close();
  }
}
