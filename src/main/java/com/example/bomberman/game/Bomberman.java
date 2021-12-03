package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Input;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.gameEngine.Tile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Bomberman extends Entity {

  public final double DEFAULT_SPEED = 150;
  public final int DEFAULT_BOMB_AMOUNT = 100;
  public final int DEFAULT_HEALTH = 1;
  public final int DEFAULT_FLAME_SIZE = 1;
  public final double SMOOTH_SPEED = 70;

  /**
   * Velocity
   */
  private double xVel;
  private double yVel;
  private double maxSpeed = DEFAULT_SPEED;
  private double currentSpeed = maxSpeed;

  /**
   * Flame
   */
  private int maxFlameSize = DEFAULT_FLAME_SIZE;
  private int currentFlameSize = maxFlameSize;

  /**
   * Bombs
   */
  private int maxBombs = DEFAULT_BOMB_AMOUNT;
  private int availableBombs = maxBombs;

  private int currentHealth = DEFAULT_HEALTH;
  private boolean isFacingRight = true;
  private boolean isFacingDown = true;

  public Bomberman(double x, double y, Sprite sprite) {
    super(x, y, sprite);
  }

  public Bomberman(Point2D position, Sprite sprite) {
    this(position.getX(), position.getY(), sprite);
  }

  public Bomberman(double x, double y, Animation animation) {
    super(x, y, animation);
  }

  public Bomberman(Point2D position, Animation animation) {
    super(position, animation);
  }

  @Override
  public void update(double deltaTime) {
    try {
      if (!isDead) {
        handleInput();
        movementController(deltaTime);
      }
      animator();
      animationController.play(deltaTime);
      availableBombs = maxBombs - Map.bombs.size();
    } catch (Exception ex) {
      Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, "CONTROLLER IS NULL!", ex);
      System.exit(-1);
    }
  }

  private void handleInput() {
    if (Input.isDown(KeyCode.W)) {
      yVel -= currentSpeed;
    }
    if (Input.isDown(KeyCode.S)) {
      yVel += currentSpeed;
    }
    if (Input.isDown(KeyCode.A)) {
      xVel -= currentSpeed;
    }
    if (Input.isDown(KeyCode.D)) {
      xVel += currentSpeed;
    }
    if (Input.isDown(KeyCode.SPACE)) {
      if (availableBombs > 0) {
        int x = (int) (getCenter().getX() / (Sprite.DEFAULT_SIZE * Sprite.SCALED)) * (
                Sprite.DEFAULT_SIZE * Sprite.SCALED);
        int y = (int) (getCenter().getY() / (Sprite.DEFAULT_SIZE * Sprite.SCALED)) * (
                Sprite.DEFAULT_SIZE * Sprite.SCALED);
        Map.bombs.add(new Bomb(x, y, Animation.bomb, maxFlameSize));
        availableBombs--;
      }
    }

    if (Input.isUp(KeyCode.W)) {
      yVel += currentSpeed;
    }
    if (Input.isUp(KeyCode.S)) {
      yVel -= currentSpeed;
    }
    if (Input.isUp(KeyCode.A)) {
      xVel += currentSpeed;
    }
    if (Input.isUp(KeyCode.D)) {
      xVel -= currentSpeed;
    }
  }

  private void movementController(double deltaTime) {
    //TODO: possible lag SEVERE
    //Cần phải update tọa độ x y riêng để check collision đc mượt hơn
    //collision X
    position = position.add(xVel * deltaTime, 0);
    collision.setX(position.getX());
    for (Tile tile : Map.tiles) {
      if (tile.getTileType() != ' ') {
        updateCollisionX(deltaTime, tile);
      }
    }
    for (Bomb bomb : Map.bombs) {
      if (!bomb.letPlayerThrough()) {
        updateCollisionX(deltaTime, bomb);
      }
    }

    //collision Y
    position = position.add(0, yVel * deltaTime);
    collision.setY(position.getY());
    for (Tile tile : Map.tiles) {
      if (tile.getTileType() != ' ') {
        updateCollisionY(deltaTime, tile);
      }
    }
    for (Bomb bomb : Map.bombs) {
      if (!bomb.letPlayerThrough()) {
        updateCollisionY(deltaTime, bomb);
      }
    }
  }

  private void updateCollisionX(double deltaTime, Entity entity) {
    if (Physic.checkCollision(collision, entity.getCollision())) {
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
      }
    }
  }

  private void updateCollisionY(double deltaTime, Entity entity) {
    if (Physic.checkCollision(collision, entity.getCollision())) {
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
      }

    }
  }

  private void animator() {
    if (xVel > 0) {
      isFacingRight = true;
      animationController.switchAnimation(Animation.bomberRight);
    }
    if (xVel < 0) {
      isFacingRight = false;
      animationController.switchAnimation(Animation.bomberLeft);
    }

    if (yVel > 0) {
      isFacingDown = true;
      animationController.switchAnimation(Animation.bomberDown);
    }
    if (yVel < 0) {
      isFacingDown = false;
      animationController.switchAnimation(Animation.bomberUp);
    }
    if (isDead()) {
      animationController.switchAnimation(Animation.bomberDead);
    }

    if (xVel + yVel == 0 && !isDead) {
      animationController.setPaused(true);
    }
  }
}
