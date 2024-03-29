package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Ballom extends Enemy {

  public Ballom(double x, double y, Animation animation) {
    super(x, y, animation.getSprites().get(0));
    animator = new Animator(animation);
    ai = new AILow();
    direction = ai.calculateDirection();
  }

  public Ballom(Point2D position, Animation animation) {
    this(position.getX(), position.getY(), animation);
  }

  @Override
  public void update(double deltaTime) {
    super.update(deltaTime);
    animatorController(deltaTime);
    if (animator.isEnded()) {
      isDead = true;
    }
  }

  private void animatorController(double deltaTime) {
    if (xVel > 0) {
      animator.switchAnimation(Animation.ballom_right);
    }

    if (xVel < 0) {
      animator.switchAnimation(Animation.ballom_left);
    }

    if (isDying) {
      timeCounter += deltaTime;
      if (timeCounter < SHOCK_TIME) {
        animator.switchAnimation(Animation.ballom_dead);
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
