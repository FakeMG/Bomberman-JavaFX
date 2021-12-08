package com.example.bomberman.game.entities.tile;

import com.example.bomberman.gameEngine.Animation;
import com.example.bomberman.gameEngine.Animator;
import com.example.bomberman.gameEngine.Sprite;
import javafx.scene.image.Image;

public class Brick extends Tile {

  private Animator animator;

  public Brick(double x, double y, Sprite sprite, Animation animation) {
    super(x, y, sprite, '*');
    animator = new Animator(animation);
    animator.setPaused(true);
    canBePassedThrough = false;
  }

  @Override
  public void update(double deltaTime) {
    animatorController(deltaTime);

    if (animator.isEnded()) {
      isDead = true;
    }
  }

  private void animatorController(double deltaTime) {
    if (isDying) {
      animator.switchAnimation(Animation.brick_broken);
    }

    animator.update(deltaTime);
  }

  @Override
  public Image getTexture() {
    if (animator.isPaused()) {
      return sprite.getTexture();
    }
    return animator.getCurrentFrame();
  }
}
