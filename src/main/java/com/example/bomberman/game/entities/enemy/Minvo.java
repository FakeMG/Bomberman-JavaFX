package com.example.bomberman.game.entities.enemy;

import com.example.bomberman.game.Map;
import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.Physic;
import com.example.bomberman.gameEngine.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Minvo extends Enemy {

  public final int MINVO_VISION = 10;
  public final double MINVO_SPEED = 120;
  public final double MINVO_MAX_DISTANCE = Sprite.DEFAULT_SIZE * Sprite.SCALED;

  public Minvo(double x, double y, Animation animation) {
    super(x, y, animation.getSprites().get(0));
    animator = new Animator(animation);

    ai = new AIMedium(Map.players.get(0), this, MINVO_VISION);
    direction = ai.calculateDirection();
    currentMaxDistance = MINVO_MAX_DISTANCE;
    currentSpeed = MINVO_SPEED;
  }

  public Minvo(Point2D position, Animation animation) {
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
      animator.switchAnimation(Animation.minvo_right);
    }

    if (xVel < 0) {
      animator.switchAnimation(Animation.minvo_left);
    }

    if (isDying) {
      timeCounter += deltaTime;
      if (timeCounter < SHOCK_TIME) {
        animator.switchAnimation(Animation.minvo_dead);
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
