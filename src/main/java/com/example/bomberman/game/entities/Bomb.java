package com.example.bomberman.game.entities;

import com.example.bomberman.game.Map;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sound;
import com.example.bomberman.gameEngine.Sprite;
import javafx.scene.shape.Rectangle;
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
        Sound.playOnce(Sound.bomb_exploded);
      }
      animatorController(deltaTime);
      affectOtherEntities();
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

  private void affectOtherEntities() {
    for (Entity player : Map.players) {
      if (!Physic.checkCollision(player.getCollision(), collision)) {
        letPlayerThrough = false;
      }
    }
  }

  private void createFlame() {
    Map.flames.add(new Flame(position, Animation.explosion_centre));
    boolean stopUp = false;
    boolean stopDown = false;
    boolean stopLeft = false;
    boolean stopRight = false;

    for (int i = 1; i <= flameSize; i++) {
      Rectangle right = new Rectangle(
              (int) position.getX() + i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              (int) position.getY(),
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Rectangle left = new Rectangle(
              (int) position.getX() + -i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              (int) position.getY(),
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Rectangle down = new Rectangle(
              (int) position.getX(),
              (int) position.getY() + i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);
      Rectangle up = new Rectangle(
              (int) position.getX(),
              (int) position.getY() + -i * Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED,
              Sprite.DEFAULT_SIZE * Sprite.SCALED);

      if (!stopRight) {
        if (i == flameSize) {
          Map.flames.add(new Flame(right.getX(), right.getY(), Animation.explosion_right_end));
        } else {
          Map.flames.add(new Flame(right.getX(), right.getY(), Animation.explosion_horizontal));
        }
        stopRight = Map.flames.get(Map.flames.size() - 1).collideWithTile();
      }

      if (!stopLeft) {
        if (i == flameSize) {
          Map.flames.add(new Flame(left.getX(), left.getY(), Animation.explosion_left_end));
        } else {
          Map.flames.add(new Flame(left.getX(), left.getY(), Animation.explosion_horizontal));
        }
        stopLeft = Map.flames.get(Map.flames.size() - 1).collideWithTile();
      }

      if (!stopDown) {
        if (i == flameSize) {
          Map.flames.add(new Flame(down.getX(), down.getY(), Animation.explosion_down_end));
        } else {
          Map.flames.add(new Flame(down.getX(), down.getY(), Animation.explosion_vertical));
        }
        stopDown = Map.flames.get(Map.flames.size() - 1).collideWithTile();
      }

      if (!stopUp) {
        if (i == flameSize) {
          Map.flames.add(new Flame(up.getX(), up.getY(), Animation.explosion_up_end));
        } else {
          Map.flames.add(new Flame(up.getX(), up.getY(), Animation.explosion_vertical));
        }
      }
      stopUp = Map.flames.get(Map.flames.size() - 1).collideWithTile();
    }
  }

  public boolean letPlayerThrough() {
    return letPlayerThrough;
  }
}
