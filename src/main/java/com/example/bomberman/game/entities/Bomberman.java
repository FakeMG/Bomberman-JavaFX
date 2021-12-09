package com.example.bomberman.game.entities;

import com.example.bomberman.game.Map;
import com.example.bomberman.game.entities.tile.Tile;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Input;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sound;
import com.example.bomberman.gameEngine.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Bomberman extends Entity {

  public final double DEFAULT_SPEED = 150;
  public final int DEFAULT_BOMB_AMOUNT = 1;
  public final int DEFAULT_HEALTH = 1;
  public final int DEFAULT_FLAME_SIZE = 1;
  public final double SMOOTH_SPEED = 70;

  private Animator animator;

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

  /**
   * Bombs
   */
  private int maxBombs = DEFAULT_BOMB_AMOUNT;
  private int availableBombs = maxBombs;

  private int currentHealth = DEFAULT_HEALTH;
  private boolean isFacingRight = true;
  private boolean isFacingDown = true;

  public Bomberman(double x, double y, Animation animation) {
    super(x, y, animation.getSprites().get(0));
    animator = new Animator(animation);
  }

  public Bomberman(Point2D position, Animation animation) {
    this(position.getX(), position.getY(), animation);
  }

  @Override
  public void update(double deltaTime) {
    try {
      if (!isDying && !isDead) {
        handleInput();
        movementController(deltaTime);
        soundController();
      } else {
        xVel = yVel = 0;
      }
      animatorController(deltaTime);
      availableBombs = maxBombs - Map.bombs.size();
    } catch (Exception ex) {
      Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, "CONTROLLER IS NULL!", ex);
      System.exit(-1);
    }
  }

  private void animatorController(double deltaTime) {
    if (xVel > 0 && yVel == 0) {
      isFacingRight = true;
      animator.switchAnimation(Animation.bomberRight);
    }
    if (xVel < 0 && yVel == 0) {
      isFacingRight = false;
      animator.switchAnimation(Animation.bomberLeft);
    }

    if (yVel > 0) {
      isFacingDown = true;
      animator.switchAnimation(Animation.bomberDown);
    }
    if (yVel < 0) {
      isFacingDown = false;
      animator.switchAnimation(Animation.bomberUp);
    }
    if (isDying) {
      animator.switchAnimation(Animation.bomberDead);
    }

    if (xVel == 0 && yVel == 0 && !isDead && !isDying) {
      animator.setPaused(true);
    }

    animator.update(deltaTime);
  }

  private void soundController() {
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
        Sound.playOnce(Sound.bomb_placed);
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
      updateCollisionX(deltaTime, tile);
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
      updateCollisionY(deltaTime, tile);
    }
    for (Bomb bomb : Map.bombs) {
      if (!bomb.letPlayerThrough()) {
        updateCollisionY(deltaTime, bomb);
      }
    }

    if (xVel == 0 && yVel == 0) {
      currentSpeed = maxSpeed;
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
      }

    }
  }

  @Override
  public Image getTexture() {
    return animator.getCurrentFrame();
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public void setMaxSpeed(double maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  public int getMaxFlameSize() {
    return maxFlameSize;
  }

  public void setMaxFlameSize(int maxFlameSize) {
    this.maxFlameSize = maxFlameSize;
  }

  public int getMaxBombs() {
    return maxBombs;
  }

  public void setMaxBombs(int maxBombs) {
    this.maxBombs = maxBombs;
  }
}
