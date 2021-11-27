package com.example.bomberman.gameEngine;

import javafx.scene.shape.Rectangle;

public class Physic {

  public static boolean checkCollision(Rectangle a, Rectangle b) {
    double leftA, leftB;
    double rightA, rightB;
    double topA, topB;
    double bottomA, bottomB;

    leftA = a.getX();
    rightA = a.getX() + a.getWidth();
    topA = a.getY();
    bottomA = a.getY() + a.getHeight();

    leftB = b.getX();
    rightB = b.getX() + b.getWidth();
    topB = b.getY();
    bottomB = b.getY() + b.getHeight();

    if (bottomA <= topB) {
      return false;
    }

    if (topA >= bottomB) {
      return false;
    }

    if (rightA <= leftB) {
      return false;
    }

    if (leftA >= rightB) {
      return false;
    }

    return true;
  }

}
