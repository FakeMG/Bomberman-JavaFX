package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Kondoria extends Enemy {

  public final double KONDORIA_SPEED = 60;
  public final double KONDORIA_MAX_DISTANCE = Sprite.DEFAULT_SIZE * Sprite.SCALED;

  public Kondoria(double x, double y, Animation animation) {
    super(x, y, animation.getSprites().get(0));
    animator = new Animator(animation);

    ai = new AIHigh(this);
    direction = Enemy.LEFT;
    currentMaxDistance = KONDORIA_MAX_DISTANCE;
    currentSpeed = KONDORIA_SPEED;
  }

  public Kondoria(Point2D position, Animation animation) {
    this(position.getX(), position.getY(), animation);
  }

  @Override
  public void update(double deltaTime) {
    super.update(deltaTime);
    ai.update();
    animatorController(deltaTime);
    if (animator.isEnded()) {
      isDead = true;
    }
  }

  private void animatorController(double deltaTime) {
    if (xVel > 0) {
      animator.switchAnimation(Animation.kondoria_right);
    }

    if (xVel < 0) {
      animator.switchAnimation(Animation.kondoria_left);
    }

    if (isDying) {
      timeCounter += deltaTime;
      if (timeCounter < SHOCK_TIME) {
        animator.switchAnimation(Animation.kondoria_dead);
      } else {
        animator.switchAnimation(Animation.mob_dead);
      }
    }

    animator.update(deltaTime);
  }

  @Override
  public Image getTexture() {
    return animator.getCurrentFrame();
  }
}
