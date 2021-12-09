module com.example.bomberman {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires java.logging;
  requires javafx.media;

  opens com.example.bomberman.gameEngine to javafx.fxml;
  exports com.example.bomberman.gameEngine;
  exports com.example.bomberman.game.entities;
  opens com.example.bomberman.game.entities to javafx.fxml;
  exports com.example.bomberman.game.entities.tile;
  opens com.example.bomberman.game.entities.tile to javafx.fxml;
  exports com.example.bomberman.game.entities.item;
  opens com.example.bomberman.game.entities.item to javafx.fxml;
}