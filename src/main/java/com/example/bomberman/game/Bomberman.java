package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Input;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.gameEngine.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Bomberman extends Entity {

  public final double DEFAULT_SPEED = 150;
  public final int DEFAULT_BOMB_AMOUNT = 1;
  public final int DEFAULT_HEALTH = 1;
  public final int DEFAULT_FLAME_SIZE = 1;
  public final double DELAY = 0.1; //seconds

  private Animation bomberUp;
  private Animation bomberDown;
  private Animation bomberRight;
  private Animation bomberLeft;
  private Animation bomberDead;
  private double xVel;
  private double yVel;
  private double currentFlameSize = DEFAULT_FLAME_SIZE;
  private double currentSpeed = DEFAULT_SPEED;
  private int availableBombs = DEFAULT_BOMB_AMOUNT;
  private int currentHealth = DEFAULT_HEALTH;
  private boolean isFacingRight = true;
  private boolean isFacingDown = true;
  private List<Bomb> bombsList = new ArrayList<>();

  public Bomberman(double x, double y, Sprite sprite) {
    super(x, y, sprite);
    bomberUp = new Animation(Sprite.bomber_up, Sprite.bomber_up.size(), DELAY, true);
    bomberDown = new Animation(Sprite.bomber_down, Sprite.bomber_down.size(), DELAY, true);
    bomberRight = new Animation(Sprite.bomber_right, Sprite.bomber_right.size(), DELAY, true);
    bomberLeft = new Animation(Sprite.bomber_left, Sprite.bomber_left.size(), DELAY, true);
    bomberDead = new Animation(Sprite.bomber_dead, Sprite.bomber_dead.size(), DELAY, false);
    activeAnimation = bomberDown;
  }

  public Bomberman(Point2D position, Sprite sprite) {
    this(position.getX(), position.getY(), sprite);
  }

  public void handleInput() {
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
      //TODO: drop bomb
      if (availableBombs > 0) {
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

  public void movementController(double deltaTime) {
    //Cần phải update tọa độ x y riêng để check collision đc mượt hơn
    position = position.add(xVel * deltaTime, 0);
    collision.setX(position.getX());
    for (Tile tile : Map.getTiles()) {
      if (tile.getTileType() != ' ') {
        //check collision
        if (Physic.checkCollision(tile.getCollision(), collision)) {
          position = position.add(-xVel * deltaTime, 0);
          collision.setX(position.getX());

          //Cái này giúp nhân vật tự di chuyển vào khe trống giữa 2 tile
          //Giúp di chuyển mượt hơn
          if (getCenter().getY()
                  <= tile.getCenter().getY() - tile.getSprite().getRealWidth() / 2) {
            position = position.add(0, -2 * deltaTime);
          } else if (getCenter().getY()
                  >= tile.getCenter().getY() + tile.getSprite().getRealWidth() / 2) {
            position = position.add(0, 2 * deltaTime);
          }
        }
      }
    }

    position = position.add(0, yVel * deltaTime);
    collision.setY(position.getY());
    for (Tile tile : Map.getTiles()) {
      if (tile.getTileType() != ' ') {
        //check collision
        if (Physic.checkCollision(tile.getCollision(), collision)) {
          position = position.add(0, -yVel * deltaTime);
          collision.setY(position.getY());

          //Cái này giúp nhân vật tự di chuyển vào khe trống giữa 2 tile
          //Giúp di chuyển mượt hơn
          if (getCenter().getX()
                  <= tile.getCenter().getX() - tile.getSprite().getRealHeight() / 2) {
            position = position.add(-2 * deltaTime, 0);
          } else if (getCenter().getX()
                  >= tile.getCenter().getX() + tile.getSprite().getRealHeight() / 2) {
            position = position.add(2 * deltaTime, 0);
          }
        }
      }
    }
  }

  public void animationController() {
    if (xVel > 0) {
      isFacingRight = true;
      switchAnimation(bomberRight);
    }
    if (xVel < 0) {
      isFacingRight = false;
      switchAnimation(bomberLeft);
    }

    if (yVel > 0) {
      isFacingDown = true;
      switchAnimation(bomberDown);
    }
    if (yVel < 0) {
      isFacingDown = false;
      switchAnimation(bomberUp);
    }

    if (xVel + yVel == 0) {
      activeAnimation.setPaused(true);
    }
  }

  private void switchAnimation(Animation animation) {
    activeAnimation = animation;
    activeAnimation.setPaused(false);
  }

  @Override
  public void update(double deltaTime) {
    handleInput();
    movementController(deltaTime);
    animationController();
    if (activeAnimation != null) {
      activeAnimation.run(deltaTime);
    }
  }
}
