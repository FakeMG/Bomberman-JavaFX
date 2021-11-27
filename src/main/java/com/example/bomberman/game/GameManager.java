package com.example.bomberman.game;

import com.example.bomberman.gameEngine.AbstractGame;
import com.example.bomberman.gameEngine.GameContainer;
import com.example.bomberman.gameEngine.Renderer;
import com.example.bomberman.gameEngine.Sprite;

public class GameManager extends AbstractGame {

  private Map map1;
  private Bomberman player;

  public GameManager() {
    map1 = new Map("src/main/resources/com/example/bomberman/gameEngine/levels/Level1.txt");
    map1.readMap();
    player = new Bomberman(48, 48, Sprite.bomber_down.get(0).getTexture());
    Renderer.addEntity(player);
  }

  @Override
  public void update(float deltaTime) {
    player.update();
  }

  public static void main(String[] args) {
    GameContainer gc = new GameContainer();
    gc.go();
  }
}
