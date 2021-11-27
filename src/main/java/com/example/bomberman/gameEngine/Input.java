package com.example.bomberman.gameEngine;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Input {

  private static Scene scene;
  private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();
  private static final Set<KeyCode> keysLast = new HashSet<>(); //trạng thái ngay trước đó của các nút

  private Input() {
  }

  public static Input getInstance() {
    return new Input();
  }

  /**
   * hàm update này phải ở dưới hàm update của game.
   */
  public static void update() {
    keysLast.clear();
    keysLast.addAll(keysCurrentlyDown);
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
    return !keysLast.contains(keyCode) && keysCurrentlyDown.contains(keyCode);
  }

  public static boolean isUp(KeyCode keyCode) {
    return keysLast.contains(keyCode) && !keysCurrentlyDown.contains(keyCode);
  }

  @Override
  public String toString() {
    StringBuilder keysDown = new StringBuilder("KeyPolling on scene (").append(scene).append(")");
    for (KeyCode code : keysCurrentlyDown) {
      keysDown.append(code.getName()).append(" ");
    }
    return keysDown.toString();
  }

  public static void main(String[] args) {
    System.out.println(KeyCode.A.getCode());
  }
}
