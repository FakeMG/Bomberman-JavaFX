package com.example.bomberman.game.entities.enemy;

import java.util.Random;

public abstract class AI {

  protected Random random = new Random();

  /**
   * Thuật toán tìm đường đi
   * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 2/4/6/8 ở numpad
   */
  public abstract int calculateDirection();
}
