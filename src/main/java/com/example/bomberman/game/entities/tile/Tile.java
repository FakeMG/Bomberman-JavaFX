package com.example.bomberman.game.entities.tile;

import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;

public class Tile extends Entity {

  private char tileType;
  /*
  #: wall
  *: brick
  &: layered tile
   */

  public Tile(double x, double y, Sprite sprite, char tileType) {
    super(x, y, sprite);
    this.tileType = tileType;
    if (tileType == '#' || tileType == '*') {
      canBePassedThrough = false;
    }
  }

  public Tile(Point2D position, Sprite sprite, char tileType) {
    this(position.getX(), position.getY(), sprite, tileType);
  }

  public char getTileType() {
    return tileType;
  }

  public void setTileType(char tileType) {
    this.tileType = tileType;
  }

  @Override
  public void update(double deltaTime) {
  }
}
