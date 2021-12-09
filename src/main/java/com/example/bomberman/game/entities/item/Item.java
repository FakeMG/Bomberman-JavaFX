package com.example.bomberman.game.entities.item;

import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Sound;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;

public abstract class Item extends Entity {

  public Item(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public Item(Point2D position, Sprite sprite) {
    this(position.getX(), position.getY(), sprite);
  }

  public abstract void affect();
}
