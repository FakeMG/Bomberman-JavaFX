package com.example.bomberman.gameEngine;

import java.util.ArrayList;
import java.util.Arrays;

public class Animation {

  public static final float PLAYER_DELAY = 0.1f; //seconds
  public static final float PLAYER_DEAD_DELAY = 0.3f; //seconds
  public static final float BOMB_DELAY = 0.2f; //seconds
  public static final float FLAME_DELAY = 0.1f; //seconds
  public static final float BRICK_DELAY = 0.1f; //seconds

  public static Animation bomberUp = new Animation(Sprite.bomber_up, PLAYER_DELAY, true);
  public static Animation bomberDown = new Animation(Sprite.bomber_down, PLAYER_DELAY, true);
  public static Animation bomberRight = new Animation(Sprite.bomber_right, PLAYER_DELAY, true);
  public static Animation bomberLeft = new Animation(Sprite.bomber_left, PLAYER_DELAY, true);
  public static Animation bomberDead = new Animation(Sprite.bomber_dead, PLAYER_DEAD_DELAY, false);

  public static Animation bomb = new Animation(Sprite.bomb, BOMB_DELAY, true);

  public static Animation explosion_centre = new Animation(Sprite.explosion_centre, FLAME_DELAY,
          false);
  public static Animation explosion_vertical = new Animation(Sprite.explosion_vertical,
          FLAME_DELAY, false);
  public static Animation explosion_horizontal = new Animation(Sprite.explosion_horizontal,
          FLAME_DELAY, false);
  public static Animation explosion_left_end = new Animation(Sprite.explosion_left_end,
          FLAME_DELAY, false);
  public static Animation explosion_right_end = new Animation(Sprite.explosion_right_end,
          FLAME_DELAY, false);
  public static Animation explosion_up_end = new Animation(Sprite.explosion_up_end,
          FLAME_DELAY, false);
  public static Animation explosion_down_end = new Animation(Sprite.explosion_down_end, FLAME_DELAY,
          false);

  public static Animation brick_broken = new Animation(Sprite.brick_broken, BRICK_DELAY, false);

  public static Animation ballom_left = new Animation(Sprite.ballom_left, PLAYER_DELAY, true);
  public static Animation ballom_right = new Animation(Sprite.ballom_right, PLAYER_DELAY, true);
  public static Animation ballom_dead = new Animation(Sprite.balloom_dead, PLAYER_DELAY, true);

  public static Animation oneal_left = new Animation(Sprite.oneal_left, PLAYER_DELAY, true);
  public static Animation oneal_right = new Animation(Sprite.oneal_right, PLAYER_DELAY, true);
  public static Animation oneal_dead = new Animation(Sprite.oneal_dead, PLAYER_DELAY, true);

  public static Animation mob_dead = new Animation(Sprite.mob_dead, PLAYER_DELAY, false);

  private int totalFrames;
  private ArrayList<Sprite> sprites;
  private boolean isLooping = false;
  private float[] delayPerFrame;

  public Animation(ArrayList<Sprite> sprites, float delayValue, boolean isLooping) {
    this.sprites = sprites;
    this.totalFrames = sprites.size();
    this.delayPerFrame = new float[sprites.size()];
    Arrays.fill(this.delayPerFrame, delayValue);
    this.isLooping = isLooping;
  }

  public ArrayList<Sprite> getSprites() {
    return sprites;
  }

  public int getTotalFrames() {
    return totalFrames;
  }

  public float getDelayAt(int index) {
    return delayPerFrame[index];
  }

  public void setDelayAt(int index, float value) {
    delayPerFrame[index] = value;
  }

  public boolean isLooping() {
    return isLooping;
  }

  public void setLooping(boolean looping) {
    this.isLooping = looping;
  }
}
