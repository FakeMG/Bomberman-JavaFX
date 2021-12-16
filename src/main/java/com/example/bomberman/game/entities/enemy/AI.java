package com.example.bomberman.game.entities.enemy;

import java.util.Random;

public abstract class AI {

  protected Random random = new Random();
  public abstract int calculateDirection();
  public void update() {
  }
}
