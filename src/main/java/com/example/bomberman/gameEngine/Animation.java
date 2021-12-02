package com.example.bomberman.gameEngine;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Animation {

  private int totalFrames;
  private ArrayList<Sprite> sprites;
  private boolean isLooping = false;
  private boolean isPaused = true;
  private int frameCounter;
  private double delayPerFrame = 0.1; //seconds
  private double timeCounter;

  public Animation(ArrayList<Sprite> sprites, int totalFrames, double delayPerFrame,
          boolean isLooping) {
    this.sprites = sprites;
    this.totalFrames = totalFrames;
    this.delayPerFrame = delayPerFrame;
    this.isLooping = isLooping;
  }

  public void run(double deltaTime) {
    if (!isPaused) {
      if (isLooping) {
        timeCounter += deltaTime;
        loopTheAnimation();
      } else {
        runTheAnimationOnce();
      }
    } else {
      frameCounter = 0;
    }
  }

  private void loopTheAnimation() {
    if (timeCounter >= delayPerFrame) {
      frameCounter++;
      timeCounter = 0;
    }
    //ko dùng else if ở đoạn này được vì frameCounter sẽ vượt quá range của sprites
    if (frameCounter >= totalFrames) {
      frameCounter = 0;
    }
  }

  private void runTheAnimationOnce() {
    if (frameCounter < totalFrames - 1) {
      frameCounter++;
    }
  }

  public ArrayList<Sprite> getSprites() {
    return sprites;
  }

  public void setSprites(ArrayList<Sprite> sprites) {
    this.sprites = sprites;
  }

  public Image getCurrentFrame() {
    if (frameCounter < sprites.size()) {
      return sprites.get(frameCounter).getTexture();
    }
    return null;
  }

  public int getFrameCounter() {
    return frameCounter;
  }

  public void setFrameCounter(int frameCounter) {
    this.frameCounter = frameCounter;
  }

  public double getDelayPerFrame() {
    return delayPerFrame;
  }

  public void setDelayPerFrame(double delayPerFrame) {
    this.delayPerFrame = delayPerFrame;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }

  public boolean isLooping() {
    return isLooping;
  }

  public void setLooping(boolean looping) {
    this.isLooping = looping;
  }

  public boolean isEnded() {
    return !isLooping && frameCounter == totalFrames - 1;
  }
}
