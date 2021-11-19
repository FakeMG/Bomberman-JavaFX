package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Entity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Tile extends Entity {
  private char tileType;

  public Tile(double x, double y, Image texture) {
    super(x, y, texture);
  }

  public Tile(Point2D position, Image texture) {
    super(position, texture);
  }

  public char getTileType() {
    return tileType;
  }

  public void setTileType(char tileType) {
    this.tileType = tileType;
  }

  @Override
  public void update() {

  }
}
