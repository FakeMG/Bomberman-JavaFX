package com.example.bomberman.game.entities.enemy;

public class AILow extends AI {

  /**
   * random movement.
   *
   * @return direction.
   */
  @Override
  public int calculateDirection() {
    return (random.nextInt(4) + 1) * 2;
  }
}
