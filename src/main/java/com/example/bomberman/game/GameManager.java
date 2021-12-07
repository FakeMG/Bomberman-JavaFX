package com.example.bomberman.game;

import com.example.bomberman.game.entities.Bomb;
import com.example.bomberman.game.entities.Flame;
import com.example.bomberman.gameEngine.AbstractGame;
import com.example.bomberman.gameEngine.Entity;
import com.example.bomberman.gameEngine.GameContainer;
import com.example.bomberman.game.entities.tile.Tile;

public class GameManager extends AbstractGame {

  private Map map1;

  public GameManager() {
    map1 = new Map("src/main/resources/com/example/bomberman/gameEngine/levels/Level1.txt");
    map1.readMap();
  }

  @Override
  public void update(double deltaTime) {
    for (Entity entity: Map.players) {
      entity.update(deltaTime);
    }

    for (Entity entity: Map.mobs) {
      entity.update(deltaTime);
    }

    for (Tile tile: Map.tiles) {
      tile.update(deltaTime);
    }

    //TODO: possible lag
    for (Bomb bomb : Map.bombs) {
      bomb.update(deltaTime);
    }
    Map.bombs.removeIf(Bomb::isDead);

    //TODO: possible lag
    for (Flame flame : Map.flames) {
      flame.update(deltaTime);
    }
    Map.flames.removeIf(Flame::isDead);
  }

  public static void main(String[] args) {
    GameContainer.go();
  }
}
