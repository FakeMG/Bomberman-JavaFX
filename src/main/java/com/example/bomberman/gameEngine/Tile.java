package com.example.bomberman.gameEngine;

import javafx.geometry.Point2D;

public class Tile extends Entity {

  private char tileType;

  public Tile(double x, double y, Sprite sprite, char tileType) {
    super(x, y, sprite);
    this.tileType = tileType;
  }

  public Tile(Point2D position, Sprite sprite, char tileType) {
    super(position, sprite);
    this.tileType = tileType;
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
