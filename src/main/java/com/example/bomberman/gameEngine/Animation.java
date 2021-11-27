package com.example.bomberman.gameEngine;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Animation {

  private int totalFrames;
  private ArrayList<Sprite> sprites;
  private boolean isPaused = true;
  private int frameCounter;
  private int delayPerFrame = 1;

  public Animation() {
    sprites = new ArrayList<>();
  }

  public Animation(ArrayList<Sprite> sprites, int totalFrames, int delayPerFrame) {
    this.sprites = sprites;
    this.totalFrames = totalFrames;
    this.delayPerFrame = delayPerFrame;
  }

  public void update() {
    if (!isPaused) {
      frameCounter++;
      if (frameCounter / delayPerFrame >= totalFrames - 1) {
        frameCounter = 0;
      }
    } else {
      frameCounter = 1;
    }
  }

  public ArrayList<Sprite> getSprites() {
    return sprites;
  }

  public void setSprites(ArrayList<Sprite> sprites) {
    this.sprites = sprites;
  }

  public Image getCurrentFrame() {
    if (frameCounter / delayPerFrame < sprites.size()) {
      return sprites.get(frameCounter / delayPerFrame).getTexture();
    }
    return null;
  }

  public int getFrameCounter() {
    return frameCounter;
  }

  public void setFrameCounter(int frameCounter) {
    this.frameCounter = frameCounter;
  }

  public int getDelayPerFrame() {
    return delayPerFrame;
  }

  public void setDelayPerFrame(int delayPerFrame) {
    this.delayPerFrame = delayPerFrame;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }
}
