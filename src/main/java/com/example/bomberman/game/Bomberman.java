package com.example.bomberman.game;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Input;
import com.example.bomberman.gameEngine.Sprite;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Bomberman extends Entity {

  public final double DEFAULT_SPEED = 2.5;
  public final int DEFAULT_BOMB_AMOUNT = 1;
  public final int DEFAULT_HEALTH = 1;
  public final int DEFAULT_FLAME_SIZE = 1;

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

  public Bomberman(double x, double y, Image texture) {
    super(x, y, texture);
    activeAnimation = new Animation();
    bomberUp = new Animation(Sprite.bomber_up, Sprite.bomber_up.size(), 6);
    bomberDown = new Animation(Sprite.bomber_down, Sprite.bomber_down.size(), 6);
    bomberRight = new Animation(Sprite.bomber_right, Sprite.bomber_right.size(), 6);
    bomberLeft = new Animation(Sprite.bomber_left, Sprite.bomber_left.size(), 6);
    bomberDead = new Animation(Sprite.bomber_dead, Sprite.bomber_dead.size(), 6);
  }

  public Bomberman(Point2D position, Image texture) {
    this(position.getX(), position.getY(), texture);
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
      availableBombs--;
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

  public void movementController() {
    position = position.add(xVel, yVel);
  }

  public void animationController() {
    if (xVel > 0) {
      isFacingRight = true;
      activeAnimation = bomberRight;
      activeAnimation.setPaused(false);
    } else if (xVel < 0) {
      isFacingRight = false;
      activeAnimation = bomberLeft;
      activeAnimation.setPaused(false);
    } else {
      activeAnimation.setPaused(true);
    }
  }

  @Override
  public void update() {
    handleInput();
    movementController();
    animationController();
    if (activeAnimation != null) {
      activeAnimation.update();
    }
  }
}
