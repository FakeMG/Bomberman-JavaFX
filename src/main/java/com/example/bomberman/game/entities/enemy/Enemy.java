package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.game.Map;
import com.example.bomberman.game.entities.Bomb;
import com.example.bomberman.game.entities.tile.Tile;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;

public abstract class Enemy extends Entity {

  public final double DEFAULT_SPEED = 60;
  public final double SMOOTH_SPEED = 70;
  public final double SHOCK_TIME = 1.5;
  public final double MAX_DISTANCE = Sprite.DEFAULT_SIZE * Sprite.SCALED * 2;
  protected Animator animator;
  protected AI ai;

  /**
   * Velocity
   */
  protected double xVel;
  protected double yVel;
  protected double currentSpeed = DEFAULT_SPEED;
  protected double distanceCovered;
  protected int direction;

  protected double timeCounter;
  /*
  8: up
  2: down
  4: left
  6: right
   */

  public Enemy(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public Enemy(Point2D position, Sprite sprite) {
    this(position.getX(), position.getY(), sprite);
  }

  @Override
  public void update(double deltaTime) {
    if (!isDying && !isDead) {
      updateVelocity(deltaTime);
      movementController(deltaTime);
      affectOtherEntities();
    } else {
      xVel = yVel = 0;
    }
  }

  protected abstract void affectOtherEntities();

  private void updateVelocity(double deltaTime) {
    if (distanceCovered >= MAX_DISTANCE) {
      distanceCovered = 0;
      xVel = yVel = 0;
      direction = ai.calculateDirection();
    }

    switch (direction) {
      case 2 -> yVel = currentSpeed;
      case 8 -> yVel = -currentSpeed;
      case 4 -> xVel = -currentSpeed;
      case 6 -> xVel = currentSpeed;
    }

    distanceCovered += Math.abs(xVel * deltaTime) + Math.abs(yVel * deltaTime);
  }

  private void movementController(double deltaTime) {
    //TODO: possible lag SEVERE
    //Cần phải update tọa độ x y riêng để check collision đc mượt hơn
    //collision X
    position = position.add(xVel * deltaTime, 0);
    collision.setX(position.getX());
    for (Tile tile : Map.tiles) {
      updateCollisionX(deltaTime, tile);
    }
    for (Bomb bomb : Map.bombs) {
      updateCollisionX(deltaTime, bomb);
    }

    //collision Y
    position = position.add(0, yVel * deltaTime);
    collision.setY(position.getY());
    for (Tile tile : Map.tiles) {
      updateCollisionY(deltaTime, tile);
    }
    for (Bomb bomb : Map.bombs) {
      updateCollisionY(deltaTime, bomb);
    }
  }

  private void updateCollisionX(double deltaTime, Entity entity) {
    if (Physic.checkCollision(collision, entity.getCollision()) && !entity.canBePassedThrough()) {
      position = position.add(-xVel * deltaTime, 0);
      collision.setX(position.getX());

      //Cái này giúp nhân vật tự di chuyển vào khe trống giữa 2 tile
      //Giúp di chuyển mượt hơn
      if (getCenter().getY()
              <= entity.getCenter().getY() - entity.getSprite().getRealWidth() / 2) {
        position = position.add(0, -SMOOTH_SPEED * deltaTime);
      } else if (getCenter().getY()
              >= entity.getCenter().getY() + entity.getSprite().getRealWidth() / 2) {
        position = position.add(0, SMOOTH_SPEED * deltaTime);
      } else {
        distanceCovered = MAX_DISTANCE;
      }
    }
  }

  private void updateCollisionY(double deltaTime, Entity entity) {
    if (Physic.checkCollision(collision, entity.getCollision()) && !entity.canBePassedThrough()) {
      position = position.add(0, -yVel * deltaTime);
      collision.setY(position.getY());

      //Cái này giúp nhân vật tự di chuyển vào khe trống giữa 2 tile
      //Giúp di chuyển mượt hơn
      if (getCenter().getX()
              <= entity.getCenter().getX() - entity.getSprite().getRealHeight() / 2) {
        position = position.add(-SMOOTH_SPEED * deltaTime, 0);
      } else if (getCenter().getX()
              >= entity.getCenter().getX() + entity.getSprite().getRealHeight() / 2) {
        position = position.add(SMOOTH_SPEED * deltaTime, 0);
      } else {
        distanceCovered = MAX_DISTANCE;
      }
    }
  }
}
