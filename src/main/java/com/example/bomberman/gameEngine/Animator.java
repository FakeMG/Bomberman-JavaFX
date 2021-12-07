package com.example.bomberman.gameEngine;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

public class Animator {

  private Animation currentAnimation;
  private boolean isPaused = true;
  private int frameCounter;
  private double timeCounter;

  public Animator(Animation currentAnimation) {
    this.currentAnimation = currentAnimation;
  }

  public void update(double deltaTime) {
    try {
      if (!isPaused) {
        timeCounter += deltaTime;
        if (currentAnimation.isLooping()) {
          loopTheAnimation();
        } else {
          playTheAnimationOnce();
        }
      } else {
        frameCounter = 0;
      }
    } catch (NullPointerException ex) {
      Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, "ANIMATION IS NULL!", ex);
    }

  }

  private void loopTheAnimation() {
    if (timeCounter >= currentAnimation.getDelayAt(frameCounter)) {
      frameCounter++;
      timeCounter = 0;
    }
    //ko dùng else if ở đoạn này được vì frameCounter sẽ vượt quá range của sprites
    if (frameCounter >= currentAnimation.getTotalFrames()) {
      frameCounter = 0;
    }
  }

  private void playTheAnimationOnce() {
    if (frameCounter < currentAnimation.getTotalFrames()
            && timeCounter >= currentAnimation.getDelayAt(frameCounter)) {
      frameCounter++;
      timeCounter = 0;
    }
  }

  public Image getCurrentFrame() {
    if (frameCounter < currentAnimation.getTotalFrames()) {
      return currentAnimation.getSprites().get(frameCounter).getTexture();
    }
    return null;
  }

  public int getFrameCounter() {
    return frameCounter;
  }

  public void setFrameCounter(int frameCounter) {
    this.frameCounter = frameCounter;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }

  public boolean isEnded() {
    return !currentAnimation.isLooping() && frameCounter == currentAnimation.getTotalFrames();
  }

  public void switchAnimation(Animation animation) {
    currentAnimation = animation;
    isPaused = false;
  }
}
