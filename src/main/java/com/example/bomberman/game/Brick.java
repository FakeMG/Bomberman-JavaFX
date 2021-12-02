package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.gameEngine.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Brick extends Tile {

  private char item = '0';
  private boolean isBroken = false;
  /*
  0 - ko có gì
  x - Portal
  b - Bomb Item
  f - Flame Item
  s - Speed Item
   */

  public Brick(double x, double y, Sprite sprite) {
    super(x, y, sprite, '*');
  }

  public Brick(double x, double y, Sprite sprite, char item) {
    super(x, y, sprite, '*');
    this.item = item;
  }

  public Brick(Point2D position, Sprite sprite, char item) {
    super(position, sprite, '*');
    this.item = item;
  }

  public boolean hasItemUnder(char item) {
    return this.item == item;
  }

  public void playerEnter(Bomberman player) {
    if (isBroken) {
      if (Physic.checkCollision(player.getCollision(), this.getCollision())) {
        switch (item) {
          case 'x':
            //TODO:
            break;
          case 'b':
            //TODO:
            break;
          case 'f':
            //TODO:
            break;
          case 's':
            break;
          default:
            break;
        }
      }
    }
  }

  public char getItem() {
    return item;
  }

  public void setItem(char item) {
    this.item = item;
  }

  public boolean isBroken() {
    return isBroken;
  }

  public void setBroken(boolean broken) {
    isBroken = broken;
  }

  @Override
  public void update(double deltaTime) {

  }

  public static void main(String[] args) {

    List<Tile> tiles = new ArrayList<>();
    Brick brick = new Brick(0, 0, null, 'x');
    tiles.add(brick);
    System.out.println(((Brick) tiles.get(0)).hasItemUnder('x'));
  }
}
