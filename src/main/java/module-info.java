module com.example.bomberman {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires java.logging;

  opens com.example.bomberman.gameEngine to javafx.fxml;
  exports com.example.bomberman.gameEngine;
}