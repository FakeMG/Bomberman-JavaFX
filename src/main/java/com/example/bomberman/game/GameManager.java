package com.example.bomberman.game;

import com.example.bomberman.gameEngine.AbstractGame;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.GameContainer;
import com.example.bomberman.gameEngine.Renderer;
import com.example.bomberman.gameEngine.Sprite;
import com.example.bomberman.gameEngine.Tile;

public class GameManager extends AbstractGame {

  private Map map1;

  public GameManager() {
    map1 = new Map("src/main/resources/com/example/bomberman/gameEngine/levels/Level1.txt");
    map1.readMap();
  }

  @Override
  public void update(double deltaTime) {
    for (Entity entity: Map.getMobs()) {
      entity.update(deltaTime);
    }
    for (Tile tile: Map.getTiles()) {
      tile.update(deltaTime);
    }
  }

  public static void main(String[] args) {
    GameContainer.go();
  }
}
