package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.game.Map;
import com.example.bomberman.game.entities.Bomberman;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import javafx.scene.shape.Rectangle;

public class AIMedium extends AI {

  Bomberman player;
  Enemy enemy;
  AI aiLow;
  int vision;

  public AIMedium(Bomberman bomber, Enemy e, int vision) {
    player = bomber;
    enemy = e;
    aiLow = new AILow();
    this.vision = vision;
  }

  @Override
  public int calculateDirection() {
    Rectangle playerCollision = player.getCollision();
    double enemyX = enemy.getCenter().getX();
    double enemyY = enemy.getCenter().getY();

    boolean stopUp = false;
    boolean stopDown = false;
    boolean stopLeft = false;
    boolean stopRight = false;

    for (int i = 1; i <= vision; i++) {
      int x = (int) (enemyX / (Sprite.DEFAULT_SIZE * Sprite.SCALED)) * (
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      int y = (int) (enemyY / (Sprite.DEFAULT_SIZE * Sprite.SCALED)) * (
              Sprite.DEFAULT_SIZE * Sprite.SCALED);

      Rectangle right = new Rectangle(
              x + i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              y,
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Rectangle left = new Rectangle(
              x + -i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              y,
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Rectangle down = new Rectangle(
              x,
              y + i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Rectangle up = new Rectangle(
              x,
              y + -i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);

      if (!stopRight) {
        if (Physic.checkCollision(right, playerCollision)) {
          return Enemy.RIGHT;
        }
        stopRight = isStop(right);
      }

      if (!stopLeft) {
        if (Physic.checkCollision(left, playerCollision)) {
          return Enemy.LEFT;
        }
        stopLeft = isStop(left);
      }

      if (!stopDown) {
        if (Physic.checkCollision(down, playerCollision)) {
          return Enemy.DOWN;
        }
        stopDown = isStop(down);
      }

      if (!stopUp) {
        if (Physic.checkCollision(up, playerCollision)) {
          return Enemy.UP;
        }
        stopUp = isStop(up);
      }

    }
    return aiLow.calculateDirection();
  }

  private boolean isStop(Rectangle direction) {
    for (Entity tile : Map.tiles) {
      if (Physic.checkCollision(tile.getCollision(), direction)
              && !tile.canBePassedThrough()) {
        return true;
      }
    }
    for (Entity bomb : Map.bombs) {
      if (Physic.checkCollision(bomb.getCollision(), direction)
              && !bomb.canBePassedThrough()) {
        return true;
      }
    }
    return false;
  }
}
