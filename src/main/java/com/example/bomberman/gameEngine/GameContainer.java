package com.example.bomberman.gameEngine;

import com.example.bomberman.game.GameManager;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GameContainer extends Application {

  public static final int WIDTH = 768;
  public static final int HEIGHT = 624;

  /**
   * Bắt buộc phải để public cho constructor ko có tham số
   */
  public GameContainer() {
  }

  @Override
  public void start(Stage stage) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(GameContainer.class.getResource("Menu.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
      stage.show();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void go() {
    launch();
  }
}
