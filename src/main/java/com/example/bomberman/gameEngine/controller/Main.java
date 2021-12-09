package com.example.bomberman.gameEngine.controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("src/main/resources/interface/Menu.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 600, 400);
      stage.setTitle("BomberTime");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
