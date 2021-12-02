package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Bomb extends Entity {
  private int countDownTime = 5; //dựa vào frame
  private int counter;

  public Bomb(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public Bomb(Point2D position, Sprite sprite) {
    super(position, sprite);
  }

  @Override
  public void update(double deltaTime) {

  }
}
