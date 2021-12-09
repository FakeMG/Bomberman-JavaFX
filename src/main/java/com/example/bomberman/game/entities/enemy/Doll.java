package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.game.Map;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Doll extends Enemy {

  public final double DOLL_SPEED = 90;
  public final double DOLL_MAX_DISTANCE = Sprite.DEFAULT_SIZE * Sprite.SCALED * 3;

  public Doll(double x, double y, Animation animation) {
    super(x, y, animation.getSprites().get(0));
    animator = new Animator(animation);
    ai = new AILow();
    direction = ai.calculateDirection();
    currentMaxDistance = DOLL_SPEED;
    currentSpeed = DOLL_MAX_DISTANCE;
  }

  public Doll(Point2D position, Animation animation) {
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
      animator.switchAnimation(Animation.doll_right);
    }

    if (xVel < 0) {
      animator.switchAnimation(Animation.doll_left);
    }

    if (isDying) {
      timeCounter += deltaTime;
      if (timeCounter < SHOCK_TIME) {
        animator.switchAnimation(Animation.doll_dead);
      } else {
        animator.switchAnimation(Animation.mob_dead);
      }
    }

    animator.update(deltaTime);
  }

  protected void affectOtherEntities() {
    for (Entity player : Map.players) {
      if (Physic.checkCollision(player.getCollision(), collision)) {
        player.setDying(true);
      }
    }
  }

  @Override
  public Image getTexture() {
    return animator.getCurrentFrame();
  }
}
