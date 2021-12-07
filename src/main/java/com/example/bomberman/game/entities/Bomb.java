package com.example.bomberman.game.entities;

import com.example.bomberman.game.Map;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Bomb extends Entity {

  private Animator animator;
  private final float COUNT_DOWN_TIME = 2; //seconds
  private float counter;
  private int flameSize;
  private boolean letPlayerThrough = true;

  public Bomb(double x, double y, Animation animation, int flameSize) {
    super(x, y, animation.getSprites().get(0));
    this.flameSize = flameSize;
    animator = new Animator(animation);
    animator.setPaused(false);
    canBePassedThrough = false;
  }

  public Bomb(Point2D position, Animation animation, int flameSize) {
    this(position.getX(), position.getY(), animation, flameSize);
  }

  @Override
  public void update(double deltaTime) {
    try {
      counter += deltaTime;
      countDown();
      if (isDead) {
        createFlame();
      }
      animatorController(deltaTime);
      updateCollision();
    } catch (Exception ex) {
      Logger.getLogger(Bomberman.class.getName()).log(Level.SEVERE, "ANIMATOR IS NULL", ex);
      System.exit(-1);
    }
  }

  private void animatorController(double deltaTime) {
    animator.update(deltaTime);
  }

  @Override
  public Image getTexture() {
    return animator.getCurrentFrame();
  }

  private void countDown() {
    if (counter >= COUNT_DOWN_TIME) {
      isDead = true;
    }
  }

  private void updateCollision() {
    for (Entity player : Map.players) {
      if (!Physic.checkCollision(player.getCollision(), collision)) {
        letPlayerThrough = false;
      }
    }
  }

  private void createFlame() {
    Map.flames.add(new Flame(position, Animation.explosion_centre));

    for (int i = 1; i <= flameSize; i++) {
      Point2D newPositionRight = new Point2D(
              position.getX() + i * Sprite.DEFAULT_SIZE * Sprite.SCALED, position.getY());
      Point2D newPositionLeft = new Point2D(
              position.getX() + -i * Sprite.DEFAULT_SIZE * Sprite.SCALED, position.getY());

      Point2D newPositionDown = new Point2D(
              position.getX(), position.getY() + i * Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Point2D newPositionUp = new Point2D(
              position.getX(), position.getY() + -i * Sprite.DEFAULT_SIZE * Sprite.SCALED);

      if (i == flameSize) {
        Map.flames.add(new Flame(newPositionRight, Animation.explosion_right_end));
        Map.flames.add(new Flame(newPositionLeft, Animation.explosion_left_end));
        Map.flames.add(new Flame(newPositionDown, Animation.explosion_down_end));
        Map.flames.add(new Flame(newPositionUp, Animation.explosion_up_end));
      } else {
        Map.flames.add(new Flame(newPositionRight, Animation.explosion_horizontal));
        Map.flames.add(new Flame(newPositionLeft, Animation.explosion_horizontal));
        Map.flames.add(new Flame(newPositionDown, Animation.explosion_vertical));
        Map.flames.add(new Flame(newPositionUp, Animation.explosion_vertical));
      }
    }
  }

  public boolean letPlayerThrough() {
    return letPlayerThrough;
  }
}
