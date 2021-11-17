package com.example.bomberman.gameEngine;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Input {

  private static Scene scene;
  private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();

  private Input() {
  }

  public static Input getInstance() {
    return new Input();
  }

  public static void pollScene(Scene scene) {
    clearKeys();
    removeCurrentKeyHandlers();
    setScene(scene);
  }

  private static void clearKeys() {
    keysCurrentlyDown.clear();
  }

  private static void removeCurrentKeyHandlers() {
    if (scene != null) {
      Input.scene.setOnKeyPressed(null);
      Input.scene.setOnKeyReleased(null);
    }
  }

  private static void setScene(Scene scene) {
    Input.scene = scene;
    Input.scene.setOnKeyPressed((keyEvent -> {
      keysCurrentlyDown.add(keyEvent.getCode());
    }));
    Input.scene.setOnKeyReleased((keyEvent -> {
      keysCurrentlyDown.remove(keyEvent.getCode());
    }));
  }

  public static boolean isDown(KeyCode keyCode) {
    return keysCurrentlyDown.contains(keyCode);
  }

  @Override
  public String toString() {
    StringBuilder keysDown = new StringBuilder("KeyPolling on scene (").append(scene).append(")");
    for (KeyCode code : keysCurrentlyDown) {
      keysDown.append(code.getName()).append(" ");
    }
    return keysDown.toString();
  }
}
