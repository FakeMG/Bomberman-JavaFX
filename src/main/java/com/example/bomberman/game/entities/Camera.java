package com.example.bomberman.game.entities;

import com.example.bomberman.game.Map;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.GameContainer;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;

public class Camera extends Entity {

  private Bomberman player;

  public Camera(double x, double y, Bomberman player) {
    super(x, y, null);
    this.player = player;
  }

  public Camera(Point2D position, Bomberman player) {
    this(position.getX(), position.getY(), player);
  }

  @Override
  public void update(double deltaTime) {
    followPlayer();
  }

  public void followPlayer() {
    if (player != null) {
      if (player.getCenter().getX() - position.getX() >= GameContainer.WIDTH * 0.5) {
        position = new Point2D(player.getCenter().getX() - GameContainer.WIDTH * 0.5,
                position.getY());
      }
      if (player.getCenter().getX() - position.getX() < GameContainer.WIDTH * 0.5) {
        position = new Point2D(player.getCenter().getX() - GameContainer.WIDTH * 0.5,
                position.getY());
      }

      //Giới hạn x
      //y ko thay đổi nên ko có giới hạn
      if (position.getX() < 0) {
        position = position.add(-position.getX(), 0);
      }
      if (position.getX() + GameContainer.WIDTH >= 31 * Sprite.DEFAULT_SIZE * Sprite.SCALED) {
        position = new Point2D(31 * Sprite.DEFAULT_SIZE * Sprite.SCALED - GameContainer.WIDTH, position.getY());
      }
    }
  }
}
