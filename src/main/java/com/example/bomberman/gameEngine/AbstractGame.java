package com.example.bomberman.gameEngine;

public abstract class AbstractGame {

  //TODO:
  public abstract void update(GameContainer gc, float deltaTime);

  public abstract void render(GameContainer gc, Renderer renderer);
}
