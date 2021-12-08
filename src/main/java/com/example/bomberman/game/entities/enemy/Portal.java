package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;

public class Portal extends Entity {

  public Portal(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public Portal(Point2D position, Sprite sprite) {
    super(position, sprite);
  }

  @Override
  public void update(double deltaTime) {

  }
}