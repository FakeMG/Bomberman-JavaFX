package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.gameEngine.Tile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;

public class Flame extends Entity {

  private boolean isOff = false;

  public Flame(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public Flame(Point2D position, Sprite sprite) {
    super(position, sprite);
  }

  public Flame(double x, double y, Animation animation) {
    super(x, y, animation);
  }

  public Flame(Point2D position, Animation animation) {
    super(position, animation);
  }

  @Override
  public void update(double deltaTime) {
    try {
      if (animationController.isEnded()) {
        isOff = true;
      }
      animationController.play(deltaTime);
      updateCollision();
    } catch (Exception ex) {
      Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, "CONTROLLER IS NULL", ex);
      System.exit(-1);
    }
  }

  private void updateCollision() {
    //TODO: possible lag SEVERE
    for (Tile tile : Map.tiles) {
      if (tile.getTileType() == '*' || tile.getTileType() == '#') {
        if (Physic.checkCollision(tile.getCollision(), collision)) {
          isOff = true;
          if (tile.getTileType() == '*') {
            //TODO:
          }
        }
      }
    }

    for (Entity entity: Map.mobs) {
      if (Physic.checkCollision(entity.getCollision(), collision)) {
        entity.setDead(true);
      }
    }

    for (Entity player: Map.players) {
      if (Physic.checkCollision(player.getCollision(), collision)) {
        player.setDead(true);
      }
    }

    for (Bomb bomb : Map.bombs) {
      if (Physic.checkCollision(bomb.getCollision(), collision)) {
        bomb.setDead(true);
      }
    }
  }

  public boolean isOff() {
    return isOff;
  }
}
