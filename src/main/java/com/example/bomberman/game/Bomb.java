package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Entity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Bomb extends Entity {

  public Bomb(double x, double y, Image texture) {
    super(x, y, texture);
  }

  public Bomb(Point2D position, Image texture) {
    super(position, texture);
  }

  @Override
  public void update() {

  }
}
